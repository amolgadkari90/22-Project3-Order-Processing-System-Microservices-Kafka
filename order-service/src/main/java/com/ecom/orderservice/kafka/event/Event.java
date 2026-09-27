package com.ecom.orderservice.kafka.event;

import com.ecom.orderservice.kafka.utility.EventType;

public class Event {
	
	
	private String eventId;
	private long orderId;	
	private EventType eventType;
	private long customerId;
	private double amount;
	private String deliveryAddress;
	
	
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public EventType getEventType() {
		return eventType;
	}
	public void setEventType(EventType orderCreated) {
		this.eventType = orderCreated;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
}
