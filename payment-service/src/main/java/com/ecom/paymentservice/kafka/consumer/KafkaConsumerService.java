package com.ecom.paymentservice.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ecom.paymentservice.kafka.consumer.dto.Event;
import com.ecom.paymentservice.service.PaymentService;

@Service
public class KafkaConsumerService {

	private final PaymentService paymentService;

    public KafkaConsumerService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @KafkaListener(
        topics = "ORDER_CREATED",
        groupId = "payment-service-group"
    )
    public void consumeOrder(
            ConsumerRecord<String, Event> record) {
    	System.out.println("Key" + record.key());
    	System.out.println("Offset" + record.offset());
    	System.out.println("Partition" + record.partition());
    	
    	//process the event
        paymentService.processPayment(record.value());
    }
}
