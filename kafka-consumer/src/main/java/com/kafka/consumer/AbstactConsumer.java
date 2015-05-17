package com.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kafka.consumer.ConsumerConfig;
import kafka.javaapi.consumer.ConsumerConnector;

/**
 * Abstract consumer
 * @author mohitmanga
 *
 */
public abstract class AbstactConsumer implements IConsumer {

	/**
	 * Topic Id
	 */
	protected String topic;
	/**
	 * Consumer configurations
	 * 
	 */
	protected ConsumerConfig consumerConfig;
	/**
	 * Connector for consuming message fron kafka
	 */
	protected ConsumerConnector consumerConnector;

	/* (non-Javadoc)
	 * @see com.kafka.consumer.IConsumer#consumerMessage()
	 */
	public void consumerMessage() {
		new ConsumerThread().start();
	}
	
	private Logger logger = LoggerFactory.getLogger(AbstactConsumer.class);

	/**
	 * Task to be executed in consumer thread 
	 */
	public abstract void execute();

	/**
	 * Consumer thread
	 * @author mohitmanga
	 *
	 */
	private class ConsumerThread extends Thread {
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			/*
			 * On server startup there can be case where we are
			 * not able to create consumer. In order to handle
			 * this case we will try on connecting to zookeeper
			 * with the sleep time 3 minutes.
			 */
			while(consumerConnector == null){
				try {
					consumerConnector = kafka.consumer.Consumer.createJavaConsumerConnector(consumerConfig);
					if(consumerConnector != null){
						break;
					}else{
						Thread.sleep(180000l);
					}
				} catch (Exception e) {
					logger.error("Could not connect to consumer for topic id :: {}", new Object[]{topic});
				}
			}
			execute();
		}
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public ConsumerConfig getConsumerConfig() {
		return consumerConfig;
	}

	public void setConsumerConfig(ConsumerConfig consumerConfig) {
		this.consumerConfig = consumerConfig;
	}

	public ConsumerConnector getConsumerConnector() {
		return consumerConnector;
	}

	public void setConsumerConnector(ConsumerConnector consumerConnector) {
		this.consumerConnector = consumerConnector;
	}
}
