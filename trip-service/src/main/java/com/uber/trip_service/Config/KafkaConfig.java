package com.uber.trip_service.Config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import com.uber.trip_service.Constants.AppConstant;

@Configuration
public class KafkaConfig {

	@Bean
	public NewTopic captainLocationTopic() {
		return TopicBuilder.name(AppConstant.CAPTAIN_LOCATION_TOPIC)
				.partitions(1)
				.replicas(1)
				.build();
	}

	@Bean
	public NewTopic tripRequestedTopic() {
		return TopicBuilder.name(AppConstant.TRIP_REQUESTED_TOPIC)
				.partitions(1)
				.replicas(1)
				.build();
	}
}
