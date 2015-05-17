package com.kafka.service;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kafka.consumer.IConsumer;

/**
 * Service for starting all the registered user.
 * Consumers will be registered against the topic ids
 * @author mohitmanga
 *
 */
@Service
public class KafkaConsumerService {

	/**
	 * Topic id - Consumer - Map
	 */
	@Resource(name = "topicConsumerMap")
	private Map<String, IConsumer> topicConsumerMap;

	private Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

	/**
	 * Iterate over all the consumer map
	 * and start thread in order to consumer
	 * messages for corresponding topic Id
	 */
	@PostConstruct
	public void startKafkaConsumers() {
		if (topicConsumerMap != null) {
			for (Map.Entry<String, IConsumer> entry : topicConsumerMap.entrySet()) {
				logger.warn("Starting consumer for topic id :: {}", new Object[] { entry.getKey() });
				entry.getValue().consumerMessage(); 
			}
		} else {
			logger.warn("No kafka consumer defined!");
		}
	}
}
