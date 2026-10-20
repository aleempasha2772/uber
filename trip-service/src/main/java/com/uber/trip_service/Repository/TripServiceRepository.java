package com.uber.trip_service.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uber.trip_service.Model.Trip;

@Repository
public interface TripServiceRepository extends JpaRepository<Trip, String> {
	List<Trip> findByRiderId(String riderId);
	Trip findByQuoteId(String quoteId);
}