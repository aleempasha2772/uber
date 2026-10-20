package com.uber.trip_service.Model;

public class TripQuoteRequest {

	private String phoneNumber;
	private GeoLocation pickupLocation;
	private GeoLocation dropLocation;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
}
