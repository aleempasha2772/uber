package com.uber.trip_service.Kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.uber.trip_service.Constants.AppConstant;
import com.uber.trip_service.Event.TripRequestedEvent;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Component
public class TripRequestedProducer {

	private final KafkaTemplate<String, String> kafkaTemplate;
	private final ObjectMapper objectMapper;

	public TripRequestedProducer(
			KafkaTemplate<String, String> kafkaTemplate,
			ObjectMapper objectMapper) {
		this.kafkaTemplate = kafkaTemplate;
		this.objectMapper = objectMapper;
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void publish(TripRequestedEvent event) {
		try {
			kafkaTemplate.send(
					AppConstant.TRIP_REQUESTED_TOPIC,
					event.getTripId(),
					objectMapper.writeValueAsString(event));
		} catch (JacksonException exception) {
			throw new IllegalStateException("Could not serialize trip request", exception);
		}
	}
}
