package com.uber.rider.External.DTO;

public class TripDTO {

	private String id;
	private String quoteId;
	private String riderId;
	private String riderPhone;
	private String captainId;
	private String pickupLocation;
	private String dropLocation;
	private String status;
	private String paymentMethod;
	private String paymentStatus;
	private String paymentAmount;
	private String currency;
	private String createdAt;
	private String updatedAt;

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	public String getQuoteId() { return quoteId; }
	public void setQuoteId(String quoteId) { this.quoteId = quoteId; }
	public String getRiderId() { return riderId; }
	public void setRiderId(String riderId) { this.riderId = riderId; }
	public String getRiderPhone() { return riderPhone; }
	public void setRiderPhone(String riderPhone) { this.riderPhone = riderPhone; }
	public String getCaptainId() { return captainId; }
	public void setCaptainId(String captainId) { this.captainId = captainId; }
	public String getPickupLocation() { return pickupLocation; }
	public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }
	public String getDropLocation() { return dropLocation; }
	public void setDropLocation(String dropLocation) { this.dropLocation = dropLocation; }
	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }
	public String getPaymentMethod() { return paymentMethod; }
	public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
	public String getPaymentStatus() { return paymentStatus; }
	public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
	public String getPaymentAmount() { return paymentAmount; }
	public void setPaymentAmount(String paymentAmount) { this.paymentAmount = paymentAmount; }
	public String getCurrency() { return currency; }
	public void setCurrency(String currency) { this.currency = currency; }
	public String getCreatedAt() { return createdAt; }
	public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
	public String getUpdatedAt() { return updatedAt; }
	public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}
