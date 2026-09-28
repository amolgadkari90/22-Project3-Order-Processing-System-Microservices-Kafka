package com.ecom.orderservice.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ecom.orderservice.kafka.event.Event;

@Service
public class KafkaService {
	
	final KafkaTemplate<String, Event> kafkaTemplate;

	KafkaService(KafkaTemplate<String, Event> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void sendMessage(String _topic, Event event) {
		System.out.println("KafkaService.sendMessage() -> sending");
		
		kafkaTemplate.send(_topic, event);
	}

}
