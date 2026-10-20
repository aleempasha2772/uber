package com.uber.rider.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uber.rider.HelloWorld;
import com.uber.rider.Model.Rider;
import com.uber.rider.Service.RiderService;

@RequestMapping("/api/v1/riders")
@RestController
public class RiderController {

	private final RiderService riderService;
    private static final Logger log = LoggerFactory.getLogger(RiderController.class);

	
	public RiderController(RiderService riderService) {
		this.riderService = riderService;
	}
	
	@PostMapping("/create-rider")
	public ResponseEntity<Rider> createRider(@RequestBody Rider rider) {
		Rider savedRider = riderService.saveRiderDetails(rider);
		log.info("Rider created with ID: {}", savedRider.getId());
		return ResponseEntity.ok(savedRider);
	}
	
	@GetMapping("/get-rider/{riderId}")
	public ResponseEntity<Rider> getRider(@PathVariable String riderId) {
		Rider rider = riderService.getRiderDetails(riderId);
		log.info("Retrieved rider with ID: {}", rider.getId());
		return ResponseEntity.ok(rider);
	}
	
	@GetMapping("/getAll-riders")
	public ResponseEntity<List<Rider>> getAllRiders() {
		List<Rider> riders = riderService.getAllRiders();
		log.info("Retrieved all riders, count: {}", riders.size());
		return ResponseEntity.ok(riders);
	}

	@GetMapping("/by-phone")
	public ResponseEntity<Rider> getRiderByPhone(@RequestParam String phoneNumber) {
		Rider rider = riderService.findRiderByPhone(phoneNumber);
		log.info("Retrieved rider with phone number: {}", phoneNumber);
		return ResponseEntity.ok(rider);
	}
	
	@DeleteMapping("/delete-rider/{riderId}")
	public ResponseEntity<Void> deleteRider(@PathVariable String riderId) {
		log.info("Deleted rider with ID: {}", riderId);
		riderService.deleteRider(riderId);
		return ResponseEntity.accepted().build();
	}
}
