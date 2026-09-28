package com.ecom.paymentservice.kafka.producer.service;

import java.util.concurrent.CompletableFuture;

import org.jspecify.annotations.Nullable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.ecom.paymentservice.kafka.consumer.dto.Event;
import com.ecom.paymentservice.kafka.producer.event.PaymentEvent;


@Service
public class KafkaPaymentService {
	
	final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

	KafkaPaymentService(KafkaTemplate<String, PaymentEvent> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void sendMessage(String _topic, PaymentEvent event) {
		System.out.println("KafkaService.sendMessage() -> sending");
		
		kafkaTemplate.send(_topic, event);
		
		
		
	}


}
