package com.ecom.orderservice.dto;

import com.ecom.orderservice.utility.OrderStatus;

public class OrderResponse {
	
	private long orderId;
	private OrderStatus status;
	

	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long l) {
		this.orderId = l;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
}
