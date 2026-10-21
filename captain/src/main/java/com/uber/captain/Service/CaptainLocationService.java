package com.uber.captain.Service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.uber.captain.Constants.AppConstant;

@Service
public class CaptainLocationService {

	private final KafkaTemplate<String, String> kafkaTemplate;

	public CaptainLocationService(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public boolean updateCaptainLocation(String captainId,Double latitude,Double longitude) {
		String captainLocation = latitude + "," + longitude;
		kafkaTemplate.send(AppConstant.CAPTAIN_LOCATION_TOPIC,captainId,captainLocation);
		return true;
	}
}
