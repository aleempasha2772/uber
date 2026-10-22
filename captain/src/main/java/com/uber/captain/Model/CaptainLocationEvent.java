package com.uber.captain.Model;

import java.time.Instant;

public class CaptainLocationEvent {

	private String captainId;
	private Double latitude;
	private Double longitude;
	private boolean available;
	private Instant timestamp;

	public CaptainLocationEvent() {
	}

	public CaptainLocationEvent(
			String captainId,
			Double latitude,
			Double longitude,
			boolean available,
			Instant timestamp) {
		this.captainId = captainId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.available = available;
		this.timestamp = timestamp;
	}

	public String getCaptainId() { return captainId; }
	public void setCaptainId(String captainId) { this.captainId = captainId; }
	public Double getLatitude() { return latitude; }
	public void setLatitude(Double latitude) { this.latitude = latitude; }
	public Double getLongitude() { return longitude; }
	public void setLongitude(Double longitude) { this.longitude = longitude; }
	public boolean isAvailable() { return available; }
	public void setAvailable(boolean available) { this.available = available; }
	public Instant getTimestamp() { return timestamp; }
	public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
