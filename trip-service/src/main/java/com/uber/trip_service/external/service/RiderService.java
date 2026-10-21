package com.uber.trip_service.external.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uber.trip_service.external.DTO.RiderDto;

@FeignClient(name = "rider", path = "/api/v1/riders")
public interface RiderService {

	@GetMapping("/by-phone")
	RiderDto getRiderByPhone(@RequestParam("phoneNumber") String phoneNumber);
    
}
