package com.uber.trip_service.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.uber.trip_service.Event.CaptainLocationEvent;

@Service
public class CaptainLocationRegistry {

	private static final double EARTH_RADIUS_KM = 6371.0088;
	private final Map<String, CaptainLocationEvent> locations = new ConcurrentHashMap<>();

	public void update(CaptainLocationEvent location) {
		locations.compute(location.getCaptainId(), (captainId, current) -> {
			if (current == null || current.getTimestamp() == null
					|| !current.getTimestamp().isAfter(location.getTimestamp())) {
				return location;
			}
			return current;
		});
	}

	public Optional<CaptainLocationEvent> findNearestAvailable(
			double pickupLatitude,
			double pickupLongitude,
			Duration maximumAge) {
		Instant cutoff = Instant.now().minus(maximumAge);
		return locations.values().stream()
				.filter(CaptainLocationEvent::isAvailable)
				.filter(location -> location.getTimestamp() != null
						&& !location.getTimestamp().isBefore(cutoff))
				.min(Comparator.comparingDouble(location -> distanceKm(
						pickupLatitude,
						pickupLongitude,
						location.getLatitude(),
						location.getLongitude())));
	}

	private double distanceKm(
			double firstLatitude,
			double firstLongitude,
			double secondLatitude,
			double secondLongitude) {
		double latitudeDifference = Math.toRadians(secondLatitude - firstLatitude);
		double longitudeDifference = Math.toRadians(secondLongitude - firstLongitude);
		double firstLatitudeRadians = Math.toRadians(firstLatitude);
		double secondLatitudeRadians = Math.toRadians(secondLatitude);

		double haversine = Math.pow(Math.sin(latitudeDifference / 2), 2)
				+ Math.cos(firstLatitudeRadians)
				* Math.cos(secondLatitudeRadians)
				* Math.pow(Math.sin(longitudeDifference / 2), 2);
		return 2 * EARTH_RADIUS_KM * Math.asin(Math.sqrt(haversine));
	}
}
