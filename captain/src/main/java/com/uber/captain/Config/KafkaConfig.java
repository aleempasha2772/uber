package com.uber.captain.Config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import com.uber.captain.Constants.AppConstant;

@Configuration
public class KafkaConfig {

	@Bean
	public NewTopic captainLocationTopic() {
		return TopicBuilder
				.name(AppConstant.CAPTAIN_LOCATION_TOPIC)
				.partitions(1)
				.replicas(1)
				.build();
	}
}
