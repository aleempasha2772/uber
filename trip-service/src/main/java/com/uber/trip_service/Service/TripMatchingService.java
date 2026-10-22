package com.uber.trip_service.Service;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uber.trip_service.Event.CaptainLocationEvent;
import com.uber.trip_service.Event.TripRequestedEvent;
import com.uber.trip_service.Model.Trip;
import com.uber.trip_service.Repository.TripServiceRepository;

@Service
public class TripMatchingService {

	private static final Logger log = LoggerFactory.getLogger(TripMatchingService.class);
	private static final Duration MAXIMUM_LOCATION_AGE = Duration.ofSeconds(60);

	private final TripServiceRepository tripRepository;
	private final CaptainLocationRegistry locationRegistry;

	public TripMatchingService(TripServiceRepository tripRepository,CaptainLocationRegistry locationRegistry) {
		this.tripRepository = tripRepository;
		this.locationRegistry = locationRegistry;
	}

	@Transactional
	public void match(TripRequestedEvent event) {
		Trip trip = tripRepository.findById(event.getTripId()).orElse(null);
		if (trip == null || !"REQUESTED".equals(trip.getStatus())) {
			return;
		}

		trip.setStatus("MATCHING");
		CaptainLocationEvent captain = locationRegistry.findNearestAvailable(
				event.getPickupLatitude(),
				event.getPickupLongitude(),
				MAXIMUM_LOCATION_AGE)
				.orElse(null);

		if (captain == null) {
			trip.setStatus("NO_CAPTAIN_FOUND");
			tripRepository.save(trip);
			log.info("No available captain found for trip {}", trip.getId());
			return;
		}

		trip.setCaptainId(captain.getCaptainId());
		tripRepository.save(trip);
		log.info("Selected captain {} for trip {}", captain.getCaptainId(), trip.getId());
	}
}
