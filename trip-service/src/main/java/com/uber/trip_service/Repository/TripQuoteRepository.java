package com.uber.trip_service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uber.trip_service.Model.TripQuote;

@Repository
public interface TripQuoteRepository extends JpaRepository<TripQuote, String> {
    
}
