package com.uber.captain.Service;

import java.time.Instant;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.uber.captain.Constants.AppConstant;
import com.uber.captain.Model.CaptainLocationEvent;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Service
public class CaptainLocationService {

	private final KafkaTemplate<String, String> kafkaTemplate;
	private final ObjectMapper objectMapper;

	public CaptainLocationService(
			KafkaTemplate<String, String> kafkaTemplate,
			ObjectMapper objectMapper) {
		this.kafkaTemplate = kafkaTemplate;
		this.objectMapper = objectMapper;
	}

	public boolean updateCaptainLocation(
			String captainId,
			Double latitude,
			Double longitude,
			boolean available) {
		CaptainLocationEvent event = new CaptainLocationEvent(
				captainId,
				latitude,
				longitude,
				available,
				Instant.now());
		try {
			kafkaTemplate.send(
					AppConstant.CAPTAIN_LOCATION_TOPIC,
					captainId,
					objectMapper.writeValueAsString(event));
		} catch (JacksonException exception) {
			throw new IllegalStateException("Could not serialize captain location", exception);
		}
		return true;
	}
}
