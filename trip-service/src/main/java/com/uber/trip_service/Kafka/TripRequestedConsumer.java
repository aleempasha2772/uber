package com.uber.trip_service.Kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.uber.trip_service.Constants.AppConstant;
import com.uber.trip_service.Event.TripRequestedEvent;
import com.uber.trip_service.Service.TripMatchingService;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Component
public class TripRequestedConsumer {

	private static final Logger log = LoggerFactory.getLogger(TripRequestedConsumer.class);
	private final ObjectMapper objectMapper;
	private final TripMatchingService tripMatchingService;

	public TripRequestedConsumer(
			ObjectMapper objectMapper,
			TripMatchingService tripMatchingService) {
		this.objectMapper = objectMapper;
		this.tripMatchingService = tripMatchingService;
	}

	@KafkaListener(topics = AppConstant.TRIP_REQUESTED_TOPIC)
	public void consume(String message) {
		try {
			TripRequestedEvent event = objectMapper.readValue(
					message,
					TripRequestedEvent.class);
			tripMatchingService.match(event);
		} catch (JacksonException exception) {
			log.error("Could not read trip requested event: {}", message, exception);
		}
	}
}
