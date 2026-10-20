package com.uber.captain.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.uber.captain.Model.Captain;
import com.uber.captain.Repository.CaptainRepository;

@Service
public class CaptainService {


	private final CaptainRepository captainRepository;

	public CaptainService(CaptainRepository captainRepository) {
		this.captainRepository = captainRepository;
	}

	@Transactional
	public Captain saveCaptainDetails(Captain captain){
		Assert.notNull(captain, "Captain cannot be null");
		return captainRepository.save(captain);
	}

	public Captain getCaptainDetails(String captainId) {
		Assert.notNull(captainId, "Captain ID cannot be null");
		return captainRepository.findById(captainId)
				.orElseThrow(() -> new RuntimeException("Captain not found"));
	}

	public List<Captain> getAllCaptains() {
		return captainRepository.findAll();
	}

	public void deleteCaptain(String captainId) {
		Assert.notNull(captainId, "Captain ID cannot be null");
		captainRepository.deleteById(captainId);
	}

	@Transactional
	public Captain updateCaptainDetails(String captainId, Captain updatedCaptain) {
	    Assert.notNull(captainId, "Captain ID cannot be null");
	    Assert.notNull(updatedCaptain, "Updated Captain cannot be null");

	    Captain existingCaptain = getCaptainDetails(captainId);

	    if (updatedCaptain.getFirstName() != null) {
	        existingCaptain.setFirstName(updatedCaptain.getFirstName());
	    }
	    if (updatedCaptain.getLastName() != null) {
	        existingCaptain.setLastName(updatedCaptain.getLastName());
	    }
	    if (updatedCaptain.getEmail() != null) {
	        existingCaptain.setEmail(updatedCaptain.getEmail());
	    }
	    if (updatedCaptain.getPhone() != null) {
	        existingCaptain.setPhone(updatedCaptain.getPhone());
	    }
	    if (updatedCaptain.getPassword() != null) {
	        existingCaptain.setPassword(updatedCaptain.getPassword());
	    }
	    if (updatedCaptain.getRole() != null) {
	        existingCaptain.setRole(updatedCaptain.getRole());
	    }
	    if (updatedCaptain.getStatus() != null) {
	        existingCaptain.setStatus(updatedCaptain.getStatus());
	    }
	    if (updatedCaptain.getLicenseNumber() != null) {
	        existingCaptain.setLicenseNumber(updatedCaptain.getLicenseNumber());
	    }
	    if (updatedCaptain.getVehicleType() != null) {
	        existingCaptain.setVehicleType(updatedCaptain.getVehicleType());
	    }
	    if (updatedCaptain.getVehiclePlate() != null) {
	        existingCaptain.setVehiclePlate(updatedCaptain.getVehiclePlate());
	    }
	    if (updatedCaptain.getRating() != null) {
	        existingCaptain.setRating(updatedCaptain.getRating());
	    }
	    if (updatedCaptain.getBackgroundCheckStatus() != null) {
	        existingCaptain.setBackgroundCheckStatus(updatedCaptain.getBackgroundCheckStatus());
	    }
	    if (updatedCaptain.getBankAccountNumber() != null) {
	        existingCaptain.setBankAccountNumber(updatedCaptain.getBankAccountNumber());
	    }
	    if (updatedCaptain.getUpdatedAt() != null) {
	        existingCaptain.setUpdatedAt(updatedCaptain.getUpdatedAt());
	    }

	    return captainRepository.save(existingCaptain);
	}


}
