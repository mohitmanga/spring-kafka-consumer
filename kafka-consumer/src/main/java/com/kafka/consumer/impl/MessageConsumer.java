package com.kafka.consumer.impl;

import java.util.List;
import java.util.Map;

import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;

import com.kafka.consumer.AbstactConsumer;

/**
 * Consumer for reading messages.
 * @author mohitmanga
 *
 */
public class MessageConsumer extends AbstactConsumer {

	/* (non-Javadoc)
	 * @see com.kafka.consumer.AbstactConsumer#execute()
	 */
	@Override
	public void execute() {
		Map<String, Integer> topicCountMap = new java.util.HashMap<String, Integer>();
		topicCountMap.put(topic, 1);
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumerConnector
				.createMessageStreams(topicCountMap);
		if (consumerMap != null && consumerMap.containsKey(topic)) {
			KafkaStream<byte[], byte[]> stream = consumerMap.get(topic).get(0);
			for (MessageAndMetadata<byte[], byte[]> messageAndMetadata : stream) {
				System.out.println(new String(messageAndMetadata.message()));
			}

		}
	}

}
