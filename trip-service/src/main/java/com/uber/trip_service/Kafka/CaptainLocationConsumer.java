package com.uber.trip_service.Kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.uber.trip_service.Constants.AppConstant;
import com.uber.trip_service.Event.CaptainLocationEvent;
import com.uber.trip_service.Service.CaptainLocationRegistry;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Component
public class CaptainLocationConsumer {

	private static final Logger log = LoggerFactory.getLogger(CaptainLocationConsumer.class);
	private final ObjectMapper objectMapper;
	private final CaptainLocationRegistry locationRegistry;

	public CaptainLocationConsumer(ObjectMapper objectMapper,CaptainLocationRegistry locationRegistry) {
		this.objectMapper = objectMapper;
		this.locationRegistry = locationRegistry;
	}

	@KafkaListener(topics = AppConstant.CAPTAIN_LOCATION_TOPIC)
	public void consume(String message) {
		try {
			CaptainLocationEvent location = objectMapper.readValue(
					message,
					CaptainLocationEvent.class);
			locationRegistry.update(location);
			log.debug("Updated location for captain {}", location.getCaptainId());
		} catch (JacksonException exception) {
			log.error("Could not read captain location event: {}", message, exception);
		}
	}
}
