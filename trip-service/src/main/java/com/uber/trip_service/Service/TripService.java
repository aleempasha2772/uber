package com.uber.trip_service.Service;

import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import com.uber.trip_service.Model.Trip;
import com.uber.trip_service.Model.TripQuoteRequest;
import com.uber.trip_service.Model.TripQuoteResponse;			
import com.uber.trip_service.Repository.TripServiceRepository;
import com.uber.trip_service.Repository.TripQuoteRepository;
import com.uber.trip_service.Model.TripQuote;
import com.uber.trip_service.Model.GeoLocation;
import com.uber.trip_service.Event.TripRequestedEvent;
import com.uber.trip_service.external.DTO.RiderDto;
import com.uber.trip_service.external.service.RiderService;

import feign.FeignException;

@Service
public class TripService {
	private static final Logger log = LoggerFactory.getLogger(TripService.class);
	private static final double EARTH_RADIUS_KM = 6371.0088;
	private static final double AVERAGE_SPEED_KMH = 30.0;
	private static final BigDecimal BASE_FARE = new BigDecimal("2.50");
	private static final BigDecimal RATE_PER_KM = new BigDecimal("1.25");
	private static final BigDecimal RATE_PER_MINUTE = new BigDecimal("0.20");

	private final TripServiceRepository tripRepository;
	private final RiderService riderService;
	private final TripQuoteRepository tripQuoteRepository;
	private final ApplicationEventPublisher eventPublisher;

	public TripService(
			TripServiceRepository tripRepository,
			RiderService riderService,
			TripQuoteRepository tripQuoteRepository,
			ApplicationEventPublisher eventPublisher) {
		this.tripRepository = tripRepository;
		this.riderService = riderService;		
		this.tripQuoteRepository = tripQuoteRepository;
		this.eventPublisher = eventPublisher;
	}


	public TripQuoteResponse getTripQuote(TripQuoteRequest tripQuoteRequest) {
		validateQuoteRequest(tripQuoteRequest);

		RiderDto rider = getRiderByPhone(tripQuoteRequest.getPhoneNumber());
		if (rider == null || rider.getId() == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rider not found");
		}

		GeoLocation pickupLocation = tripQuoteRequest.getPickupLocation();
		GeoLocation dropLocation = tripQuoteRequest.getDropLocation();
		Double distanceKm = calculateDistance(pickupLocation, dropLocation);
		Integer estimatedDuration = Math.max(
				1,
				(int) Math.ceil((distanceKm / AVERAGE_SPEED_KMH) * 60));
		BigDecimal estimatedFare = calculateFare(distanceKm, estimatedDuration);
		String currency = "USD";
		Instant expiresAt = Instant.now().plus(5, ChronoUnit.MINUTES);
		String quoteId = UUID.randomUUID().toString();

		TripQuote tripQuote = new TripQuote();
		tripQuote.setQuoteId(quoteId);
		tripQuote.setRiderId(rider.getId());
		tripQuote.setRiderPhone(rider.getPhone());
		tripQuote.setPickupLocation(pickupLocation);
		tripQuote.setDropLocation(dropLocation);
		tripQuote.setDistanceKm(distanceKm);
		tripQuote.setEstimatedDurationMinutes(estimatedDuration);
		tripQuote.setEstimatedFare(estimatedFare);
		tripQuote.setCurrency(currency);
		tripQuote.setExpiresAt(expiresAt);
		tripQuote.setUsed(false);
		tripQuoteRepository.save(tripQuote);

		return new TripQuoteResponse(
            quoteId,
			rider.getId(),
            pickupLocation,
            dropLocation,
            distanceKm,
            estimatedDuration,
			estimatedFare,
            currency,
            expiresAt
    	);
	}

	@Transactional
	public Trip createTrip(String quoteId) {
		if (!StringUtils.hasText(quoteId)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quote ID cannot be empty");
		}
		TripQuote tripQuote = tripQuoteRepository.findById(quoteId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quote not found"));
		if (tripQuote.isUsed()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Quote already used");
		}
		if (tripQuote.getExpiresAt().isBefore(Instant.now())) {
			throw new ResponseStatusException(HttpStatus.GONE, "Quote expired");
		}
		Trip trip = new Trip();
		trip.setQuoteId(quoteId);
		trip.setRiderId(tripQuote.getRiderId());
		trip.setRiderPhone(tripQuote.getRiderPhone());
		trip.setPickupLocation(tripQuote.getPickupLocation().getLatitude() + "," + tripQuote.getPickupLocation().getLongitude());
		trip.setDropLocation(tripQuote.getDropLocation().getLatitude() + "," + tripQuote.getDropLocation().getLongitude());
		trip.setStatus("REQUESTED");
		trip.setPaymentMethod("CREDIT_CARD");
		trip.setPaymentStatus("PENDING");
		trip.setPaymentAmount(tripQuote.getEstimatedFare().toString());
		trip.setCurrency(tripQuote.getCurrency());

		Trip savedTrip = tripRepository.save(trip);
		tripQuote.setUsed(true);
		tripQuoteRepository.save(tripQuote);

		TripRequestedEvent event = new TripRequestedEvent();
		event.setEventId(UUID.randomUUID().toString());
		event.setTripId(savedTrip.getId());
		event.setQuoteId(tripQuote.getQuoteId());
		event.setRiderId(tripQuote.getRiderId());
		event.setRiderPhone(tripQuote.getRiderPhone());
		event.setPickupLatitude(tripQuote.getPickupLocation().getLatitude());
		event.setPickupLongitude(tripQuote.getPickupLocation().getLongitude());
		event.setDropLatitude(tripQuote.getDropLocation().getLatitude());
		event.setDropLongitude(tripQuote.getDropLocation().getLongitude());
		event.setEstimatedFare(tripQuote.getEstimatedFare());
		event.setCurrency(tripQuote.getCurrency());
		event.setRequestedAt(Instant.now());
		eventPublisher.publishEvent(event);

		return savedTrip;
	}

	public Trip getTrip(String tripId) {
		Assert.hasText(tripId, "Trip ID cannot be empty");
		return tripRepository.findById(tripId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip not found"));
	}

	public List<Trip> getAllTrips() {
		return tripRepository.findAll();
	}

	public List<Trip> getTripsByRider(String riderId) {
		Assert.hasText(riderId, "Rider ID cannot be empty");
		return tripRepository.findByRiderId(riderId);
	}

	@Transactional
	public Trip updateTripStatus(String tripId, String status) {
		Assert.hasText(status, "Status cannot be empty");
		Trip trip = getTrip(tripId);
		trip.setStatus(status);
		return tripRepository.save(trip);
	}

	@Transactional
	public void deleteTrip(String tripId) {
		tripRepository.delete(getTrip(tripId));
	}

	private Double calculateDistance(GeoLocation pickupLocation, GeoLocation dropLocation) {
		double pickupLatitude = Math.toRadians(pickupLocation.getLatitude());
		double dropLatitude = Math.toRadians(dropLocation.getLatitude());
		double latitudeDifference = dropLatitude - pickupLatitude;
		double longitudeDifference = Math.toRadians(
				dropLocation.getLongitude() - pickupLocation.getLongitude());

		double haversine = Math.pow(Math.sin(latitudeDifference / 2), 2)
				+ Math.cos(pickupLatitude)
				* Math.cos(dropLatitude)
				* Math.pow(Math.sin(longitudeDifference / 2), 2);
		double distance = 2 * EARTH_RADIUS_KM * Math.asin(Math.sqrt(haversine));
		return BigDecimal.valueOf(distance)
				.setScale(2, RoundingMode.HALF_UP)
				.doubleValue();
	}

	private BigDecimal calculateFare(Double distanceKm, Integer durationMinutes) {
		return BASE_FARE
				.add(RATE_PER_KM.multiply(BigDecimal.valueOf(distanceKm)))
				.add(RATE_PER_MINUTE.multiply(BigDecimal.valueOf(durationMinutes)))
				.setScale(2, RoundingMode.HALF_UP);
	}

	private void validateQuoteRequest(TripQuoteRequest request) {
		if (request == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quote request cannot be null");
		}
		if (!StringUtils.hasText(request.getPhoneNumber())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone number cannot be empty");
		}
		validateLocation(request.getPickupLocation(), "Pickup location");
		validateLocation(request.getDropLocation(), "Drop location");

		GeoLocation pickup = request.getPickupLocation();
		GeoLocation drop = request.getDropLocation();
		if (Double.compare(pickup.getLatitude(), drop.getLatitude()) == 0
				&& Double.compare(pickup.getLongitude(), drop.getLongitude()) == 0) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, "Pickup and drop locations cannot be the same");
		}
	}

	private void validateLocation(GeoLocation location, String fieldName) {
		if (location == null || location.getLatitude() == null || location.getLongitude() == null) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, fieldName + " must contain latitude and longitude");
		}
		if (location.getLatitude() < -90 || location.getLatitude() > 90) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, fieldName + " latitude must be between -90 and 90");
		}
		if (location.getLongitude() < -180 || location.getLongitude() > 180) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, fieldName + " longitude must be between -180 and 180");
		}
	}

	public RiderDto getRiderByPhone(String phone) {
		try {
			RiderDto rider = riderService.getRiderByPhone(phone);
			if (rider != null) {
				log.debug("Retrieved rider with ID: {}", rider.getId());
			}
			return rider;
		} catch (FeignException.NotFound exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rider not found", exception);
		} catch (FeignException exception) {
			throw new ResponseStatusException(
					HttpStatus.SERVICE_UNAVAILABLE, "Rider service is unavailable", exception);
		}
	}

	
}
