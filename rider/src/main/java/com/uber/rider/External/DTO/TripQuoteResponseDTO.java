package com.uber.rider.External.DTO;

import java.math.BigDecimal;
import java.time.Instant;

public class TripQuoteResponseDTO {

	private String quoteId;
	private String riderId;
	private GeoLocation pickupLocation;
	private GeoLocation dropLocation;
	private Double distanceKm;
	private Integer estimatedDurationMinutes;
	private BigDecimal estimatedFare;
	private String currency;
	private Instant expiresAt;

	public TripQuoteResponseDTO() {
	}

	public TripQuoteResponseDTO(
			String quoteId,
			String riderId,
			GeoLocation pickupLocation,
			GeoLocation dropLocation,
			Double distanceKm,
			Integer estimatedDurationMinutes,
			BigDecimal estimatedFare,
			String currency,
			Instant expiresAt) {
		this.quoteId = quoteId;
		this.riderId = riderId;
		this.pickupLocation = pickupLocation;
		this.dropLocation = dropLocation;
		this.distanceKm = distanceKm;
		this.estimatedDurationMinutes = estimatedDurationMinutes;
		this.estimatedFare = estimatedFare;
		this.currency = currency;
		this.expiresAt = expiresAt;
	}

	public String getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(String quoteId) {
		this.quoteId = quoteId;
	}

	public String getRiderId() {
		return riderId;
	}

	public void setRiderId(String riderId) {
		this.riderId = riderId;
	}

	public GeoLocation getPickupLocation() {
		return pickupLocation;
	}

	public void setPickupLocation(GeoLocation pickupLocation) {
		this.pickupLocation = pickupLocation;
	}

	public GeoLocation getDropLocation() {
		return dropLocation;
	}

	public void setDropLocation(GeoLocation dropLocation) {
		this.dropLocation = dropLocation;
	}

	public Double getDistanceKm() {
		return distanceKm;
	}

	public void setDistanceKm(Double distanceKm) {
		this.distanceKm = distanceKm;
	}

	public Integer getEstimatedDurationMinutes() {
		return estimatedDurationMinutes;
	}

	public void setEstimatedDurationMinutes(Integer estimatedDurationMinutes) {
		this.estimatedDurationMinutes = estimatedDurationMinutes;
	}

	public BigDecimal getEstimatedFare() {
		return estimatedFare;
	}

	public void setEstimatedFare(BigDecimal estimatedFare) {
		this.estimatedFare = estimatedFare;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Instant getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(Instant expiresAt) {
		this.expiresAt = expiresAt;
	}
}
