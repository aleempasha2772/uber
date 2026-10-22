package com.uber.rider.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.uber.rider.External.DTO.TripQuoteRequestDTO;
import com.uber.rider.External.DTO.TripQuoteResponseDTO;
import com.uber.rider.External.DTO.TripDTO;
import com.uber.rider.External.Services.TripService;
import com.uber.rider.Model.Rider;
import com.uber.rider.Repository.RiderRepository;

@Service
public class RiderService {
	
	
	private final RiderRepository riderRepository;
	private final TripService tripService;
	
	public RiderService(RiderRepository riderRepository, TripService tripService) {
		this.riderRepository = riderRepository;
		this.tripService = tripService;
	}
	
	@Transactional
	public Rider saveRiderDetails(Rider rider){
		Assert.notNull(rider, "Rider cannot be null");
		return riderRepository.save(rider);
	}
	
	public Rider getRiderDetails(String riderId) {
		Assert.notNull(riderId, "Rider ID cannot be null");
		return riderRepository.findById(riderId)
				.orElseThrow(() -> new RuntimeException("Rider not found"));
	}
	
	public List<Rider> getAllRiders() {
		return riderRepository.findAll();
	}
	
	public void deleteRider(String riderId) {
		Assert.notNull(riderId, "Rider ID cannot be null");
		riderRepository.deleteById(riderId);
	}
	
	@Transactional
	public Rider updateRiderDetails(String riderId, Rider updatedRider) {
	    Assert.notNull(riderId, "Rider ID cannot be null");
	    Assert.notNull(updatedRider, "Updated Rider cannot be null");

	    Rider existingRider = getRiderDetails(riderId);

	    if (updatedRider.getFirstName() != null) {
	        existingRider.setFirstName(updatedRider.getFirstName());
	    }
	    if (updatedRider.getLastName() != null) {
	        existingRider.setLastName(updatedRider.getLastName());
	    }
	    if (updatedRider.getEmail() != null) {
	        existingRider.setEmail(updatedRider.getEmail());
	    }
	    if (updatedRider.getPhone() != null) {
	        existingRider.setPhone(updatedRider.getPhone());
	    }
	    if (updatedRider.getPassword() != null) {
	        existingRider.setPassword(updatedRider.getPassword());
	    }
	    if (updatedRider.getRole() != null) {
	        existingRider.setRole(updatedRider.getRole());
	    }
	    if (updatedRider.getStatus() != null) {
	        existingRider.setStatus(updatedRider.getStatus());
	    }
	    if (updatedRider.getUpdatedAt() != null) {
	        existingRider.setUpdatedAt(updatedRider.getUpdatedAt());
	    }

	    return riderRepository.save(existingRider);
	}

	public Rider findRiderByPhone(String phoneNumber) {
		Assert.hasText(phoneNumber, "Phone number cannot be empty");
		return riderRepository.findByPhone(phoneNumber)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND, "Rider not found for phone number"));
	}

	public String getTripQuote(TripQuoteRequestDTO tripQuoteRequestDTO){
		TripQuoteResponseDTO tripQuoteResponseDTO = tripService.getTripQuote(tripQuoteRequestDTO);
		return tripQuoteResponseDTO.getQuoteId();
	}

	public TripDTO requestTrip(String quoteId) {
		Assert.hasText(quoteId, "Quote ID cannot be empty");
		return tripService.createTrip(quoteId);
	}
	

}
