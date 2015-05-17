package com.kafka.consumer;

/**
 * Interface for defining consumer
 * @author mohitmanga
 *
 */
public interface IConsumer {

	/**
	 * This will start task in order
	 * to consume message for topic from
	 * kafka queue
	 */
	public void consumerMessage();
}
