package com.uber.captain.Controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uber.captain.Model.Captain;
import com.uber.captain.Model.CaptainLocationRequest;
import com.uber.captain.Service.CaptainService;
import com.uber.captain.Service.CaptainLocationService;

@RequestMapping("/api/v1/captains")
@RestController
public class CaptainController {

	private final CaptainService captainService;
	private final CaptainLocationService captainLocationService;
    private static final Logger log = LoggerFactory.getLogger(CaptainController.class);


	public CaptainController(CaptainService captainService, CaptainLocationService captainLocationService) {
		this.captainService = captainService;
		this.captainLocationService = captainLocationService;
	}

	@PostMapping("/create-captain")
	public ResponseEntity<Captain> createCaptain(@RequestBody Captain captain) {
		Captain savedCaptain = captainService.saveCaptainDetails(captain);
		log.info("Captain created with ID: {}", savedCaptain.getId());
		return ResponseEntity.ok(savedCaptain);
	}

	@GetMapping("/get-captain/{captainId}")
	public ResponseEntity<Captain> getCaptain(@PathVariable String captainId) {
		Captain captain = captainService.getCaptainDetails(captainId);
		log.info("Retrieved captain with ID: {}", captain.getId());
		return ResponseEntity.ok(captain);
	}

	@GetMapping("/getAll-captains")
	public ResponseEntity<List<Captain>> getAllCaptains() {
		List<Captain> captains = captainService.getAllCaptains();
		log.info("Retrieved all captains, count: {}", captains.size());
		return ResponseEntity.ok(captains);
	}

	@DeleteMapping("/delete-captain/{captainId}")
	public ResponseEntity<Void> deleteCaptain(@PathVariable String captainId) {
		log.info("Deleted captain with ID: {}", captainId);
		captainService.deleteCaptain(captainId);
		return ResponseEntity.accepted().build();
	}


	@PutMapping("/{captainId}/location")
	public ResponseEntity<Map<String, String>> updateCaptainLocation(
			@PathVariable String captainId,
			@RequestBody CaptainLocationRequest request) {
		Captain captain = captainService.getCaptainDetails(captainId);
		captainLocationService.updateCaptainLocation(
				captain.getId(),
				request.getLatitude(),
				request.getLongitude(),
				request.isAvailable());

		return ResponseEntity.ok(Map.of("message", "Location updated"));
	}
}
