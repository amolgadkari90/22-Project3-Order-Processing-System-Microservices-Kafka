package com.ecom.paymentservice.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.ecom.paymentservice.entity.Payment;
import com.ecom.paymentservice.kafka.consumer.dto.Event;
import com.ecom.paymentservice.kafka.producer.event.PaymentEvent;
import com.ecom.paymentservice.kafka.producer.service.KafkaPaymentService;
import com.ecom.paymentservice.repository.PaymentRepository;
import com.ecom.paymentservice.utility.PaymentEventType;

@Service
public class PaymentService {
	final PaymentRepository paymentRepository;
	final KafkaPaymentService kafkaPaymentService;

	public PaymentService(PaymentRepository paymentRepository, KafkaPaymentService kafkaPaymentService) {
		super();
		this.paymentRepository = paymentRepository;
		this.kafkaPaymentService = kafkaPaymentService;
	}


	public boolean processPayment(Event event) {
		// TODO Auto-generated method stub
		
		//Simulate payment
		boolean isPaid = simulateMakePayment();
		
		//Convert to Payment to sav event
		
		Payment payment = orderEventToPayment(event, isPaid);
		
		//save in db
		Payment savedPayment = paymentRepository.save(payment);
		
		//Payment -> PaymentEvent
		
		PaymentEvent paymentEvent = paymentToPaymentEvent(savedPayment);
		
		//produce event for delivery 
		
		if(paymentEvent.getPaymentStatus().equalsIgnoreCase("SUCCESS")) {
			paymentEvent.setPaymentStatus(PaymentEventType.PAYMENT_SUCCESS.toString());
			
		}else {
			paymentEvent.setPaymentStatus(PaymentEventType.PAYMENT_SUCCESS.toString());
		}
		
		kafkaPaymentService.sendMessage(PaymentEventType.PAYMENT_SUCCESS.toString(), paymentEvent );
		
		
		
		
		//return boolean 
		
		
		
		
		return paymentEvent != null? true: false;
	
		
	}



	private PaymentEvent paymentToPaymentEvent(Payment savedPayment) {
		// TODO Auto-generated method stub
		
		PaymentEvent paymentEvent = new PaymentEvent();
		
		paymentEvent.setAmount(savedPayment.getAmount());
		paymentEvent.setCustomerId(savedPayment.getCustomerId());
		paymentEvent.setEventId(savedPayment.getEventId());
		//paymentEvent.setEventType(savedPayment.getEventType());
		paymentEvent.setOrderId(savedPayment.getOrderId());
		paymentEvent.setPaymentId(savedPayment.getPaymentId());
		paymentEvent.setPaymentMethod(savedPayment.getPaymentMethod());
		paymentEvent.setPaymentStatus(savedPayment.getPaymentStatus());
		paymentEvent.setReason(savedPayment.getReason());
		
		if(savedPayment.getPaymentStatus().equalsIgnoreCase("SUCCESS")){
			paymentEvent.setEventType(PaymentEventType.PAYMENT_SUCCESS.toString());
		}else {
			paymentEvent.setEventType(PaymentEventType.PAYMENT_FAILED.toString());
		}
		
		return paymentEvent;
	}

	private boolean simulateMakePayment() {
		
		
		int random = generateRandomNumber();
		
		// TODO Auto-generated method stub
		return random%2 ==0? true : false;//if even true:false
	}
	
	

	public static int generateRandomNumber() {
	    Random random = new Random();
	    return random.nextInt(900000) + 100000;
	}

	private Payment orderEventToPayment(Event event, boolean isPaid) {
		// TODO Auto-generated method stub
		
		Payment payment = new Payment();
		payment.setAmount(event.getAmount());
		payment.setCustomerId(event.getCustomerId());
		payment.setEventId(event.getEventId());
		payment.setEventType(event.getEventType());
		payment.setOrderId(event.getOrderId());
		payment.setPaymentId("TXN-" + generateRandomNumber());
		payment.setPaymentMethod("UPI");
		
		if(!isPaid) {
			payment.setPaymentStatus("FAILED");
			payment.setReason("INSUFFICIENT_FUNDs");
		} else {
			payment.setPaymentStatus("SUCCESS");
		}
		
		return payment;
	}

}
