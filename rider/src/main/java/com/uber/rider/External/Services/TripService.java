
package com.uber.rider.External.Services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.uber.rider.External.DTO.TripDTO;
import com.uber.rider.External.DTO.TripQuoteRequestDTO;
import com.uber.rider.External.DTO.TripQuoteResponseDTO;


@FeignClient(name = "trip-service", path = "/api/v1/trips")
public interface TripService {

    @PostMapping("/quote")
    TripQuoteResponseDTO getTripQuote(@RequestBody TripQuoteRequestDTO tripQuoteRequestDTO);

    @PostMapping
    TripDTO createTrip(@RequestParam("quoteId") String quoteId);
    
}
