package com.uber.trip_service.Model;

import java.math.BigDecimal;
import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TripQuote {

	@Id
	private String quoteId;
	private String riderId;
	private String riderPhone;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "latitude", column = @Column(name = "pickup_latitude")),
		@AttributeOverride(name = "longitude", column = @Column(name = "pickup_longitude"))
	})
	private GeoLocation pickupLocation;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "latitude", column = @Column(name = "drop_latitude")),
		@AttributeOverride(name = "longitude", column = @Column(name = "drop_longitude"))
	})
	private GeoLocation dropLocation;

	private Double distanceKm;
	private Integer estimatedDurationMinutes;
	@Column(precision = 10, scale = 2)
	private BigDecimal estimatedFare;
	private String currency;
	private Instant expiresAt;
	private boolean used;

	@CreationTimestamp
	private Instant createdAt;

	@UpdateTimestamp
	private Instant updatedAt;

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

	public String getRiderPhone() {
		return riderPhone;
	}

	public void setRiderPhone(String riderPhone) {
		this.riderPhone = riderPhone;
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

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}
}
