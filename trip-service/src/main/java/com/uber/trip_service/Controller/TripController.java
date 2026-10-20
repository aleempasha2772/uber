package com.uber.trip_service.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uber.trip_service.Model.Trip;
import com.uber.trip_service.Model.TripQuoteRequest;
import com.uber.trip_service.Model.TripQuoteResponse;
import com.uber.trip_service.Service.TripService;
import com.uber.trip_service.external.RiderDto;

@RestController
@RequestMapping("/api/v1/trips")
public class TripController {

	private final TripService tripService;

	public TripController(TripService tripService) {
		this.tripService = tripService;
	}

	@PostMapping("/quote")
	public ResponseEntity<TripQuoteResponse> getTripQuote(
			@RequestBody TripQuoteRequest tripQuoteRequest) {
		return ResponseEntity.ok(tripService.getTripQuote(tripQuoteRequest));
	}

	@PostMapping
	public ResponseEntity<Trip> createTrip(@RequestParam String quoteId) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(tripService.createTrip(quoteId));
	}

	@GetMapping("/{tripId}")
	public ResponseEntity<Trip> getTrip(@PathVariable String tripId) {
		return ResponseEntity.ok(tripService.getTrip(tripId));
	}

	@GetMapping
	public ResponseEntity<List<Trip>> getAllTrips() {
		return ResponseEntity.ok(tripService.getAllTrips());
	}

	@GetMapping("/rider/{riderId}")
	public ResponseEntity<List<Trip>> getTripsByRider(@PathVariable String riderId) {
		return ResponseEntity.ok(tripService.getTripsByRider(riderId));
	}

	@PatchMapping("/{tripId}/status")
	public ResponseEntity<Trip> updateTripStatus(
			@PathVariable String tripId,
			@RequestParam String status) {
		return ResponseEntity.ok(tripService.updateTripStatus(tripId, status));
	}

	@DeleteMapping("/{tripId}")
	public ResponseEntity<Void> deleteTrip(@PathVariable String tripId) {
		tripService.deleteTrip(tripId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/rider/by-phone")
	public ResponseEntity<RiderDto> getRiderByPhone(@RequestParam String phoneNumber) {
		return ResponseEntity.ok(tripService.getRiderByPhone(phoneNumber));
	}
}
