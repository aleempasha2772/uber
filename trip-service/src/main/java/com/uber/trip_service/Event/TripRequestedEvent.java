package com.uber.trip_service.Event;

import java.math.BigDecimal;
import java.time.Instant;

public class TripRequestedEvent {

	private String eventId;
	private String tripId;
	private String quoteId;
	private String riderId;
	private String riderPhone;
	private Double pickupLatitude;
	private Double pickupLongitude;
	private Double dropLatitude;
	private Double dropLongitude;
	private BigDecimal estimatedFare;
	private String currency;
	private Instant requestedAt;

	public TripRequestedEvent() {
	}

	public String getEventId() { return eventId; }
	public void setEventId(String eventId) { this.eventId = eventId; }
	public String getTripId() { return tripId; }
	public void setTripId(String tripId) { this.tripId = tripId; }
	public String getQuoteId() { return quoteId; }
	public void setQuoteId(String quoteId) { this.quoteId = quoteId; }
	public String getRiderId() { return riderId; }
	public void setRiderId(String riderId) { this.riderId = riderId; }
	public String getRiderPhone() { return riderPhone; }
	public void setRiderPhone(String riderPhone) { this.riderPhone = riderPhone; }
	public Double getPickupLatitude() { return pickupLatitude; }
	public void setPickupLatitude(Double pickupLatitude) { this.pickupLatitude = pickupLatitude; }
	public Double getPickupLongitude() { return pickupLongitude; }
	public void setPickupLongitude(Double pickupLongitude) { this.pickupLongitude = pickupLongitude; }
	public Double getDropLatitude() { return dropLatitude; }
	public void setDropLatitude(Double dropLatitude) { this.dropLatitude = dropLatitude; }
	public Double getDropLongitude() { return dropLongitude; }
	public void setDropLongitude(Double dropLongitude) { this.dropLongitude = dropLongitude; }
	public BigDecimal getEstimatedFare() { return estimatedFare; }
	public void setEstimatedFare(BigDecimal estimatedFare) { this.estimatedFare = estimatedFare; }
	public String getCurrency() { return currency; }
	public void setCurrency(String currency) { this.currency = currency; }
	public Instant getRequestedAt() { return requestedAt; }
	public void setRequestedAt(Instant requestedAt) { this.requestedAt = requestedAt; }
}
