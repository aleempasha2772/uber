package com.uber.captain.Model;

public class CaptainLocationRequest {

	private Double latitude;
	private Double longitude;
	private Boolean available = true;

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public boolean isAvailable() {
		return available == null || available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}
}
