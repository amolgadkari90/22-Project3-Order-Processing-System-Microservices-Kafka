package com.ecom.orderservice.service;


import org.springframework.stereotype.Service;

import com.ecom.orderservice.dto.OrderDetailedResponse;
import com.ecom.orderservice.dto.OrderRequest;
import com.ecom.orderservice.dto.OrderResponse;
import com.ecom.orderservice.entity.Orders;
import com.ecom.orderservice.kafka.KafkaService;
import com.ecom.orderservice.kafka.event.Event;
import com.ecom.orderservice.kafka.utility.EventType;
import com.ecom.orderservice.kafka.utility.GenerateEventId;
import com.ecom.orderservice.repository.OrderRepository;
import com.ecom.orderservice.utility.OrderStatus;



@Service
public class OrderService {
	
	final OrderRepository orderRepo;
	final KafkaService kafkaService;

	OrderService(OrderRepository orderRepo, KafkaService kafkaService) {
		this.orderRepo = orderRepo;
		this.kafkaService = kafkaService;
	}

	public OrderResponse createOrder(OrderRequest request) {
		
		//Request -> Order	
		Orders order = requestToOrder(request);
		
		//Save in DB
		order.setStatus(OrderStatus.CREATED);
		Orders savedOrder = orderRepo.save(order);
		
		//Order -> Response
		Event event = new Event();
		OrderResponse response = new OrderResponse();
		
		if(savedOrder != null) {
			response = orderToResponse(savedOrder);
			//Kafka event 
			event = orderToEvent(savedOrder);
			event.setEventType(EventType.ORDER_CREATED);
		}
		else {
			//Throw exception
		}
		
		
		if(event != null) {
			//Publish kafka event
			
			kafkaService.sendMessage(EventType.ORDER_CREATED.toString(), event);
			
			
		}
		
				
		/****************/
		
		return response;
	}
	
	
	public OrderDetailedResponse getByOrderId(long orderId) {
		
		Orders orders = orderRepo.findById(orderId).orElseThrow();
		
		OrderDetailedResponse detailedResponse = orderToOrderDetailedResponse(orders);
		
		return detailedResponse;
	}
	
	/****************Utility **********************/

	private Event orderToEvent(Orders savedOrder) {
		
		Event event = new Event();
		
		event.setAmount(savedOrder.getAmount());
		event.setCustomerId(savedOrder.getCustomerId());
		event.setDeliveryAddress(savedOrder.getAddress());
		event.setOrderId(savedOrder.getOrderId());
		event.setEventId(GenerateEventId.generateEventId());
		
		return event;
	}

	private OrderResponse orderToResponse(Orders savedOrder) {
		
		OrderResponse response = new OrderResponse();
		
		response.setOrderId(savedOrder.getOrderId());
		response.setStatus(savedOrder.getStatus());
		
		return response;
	}

	private Orders requestToOrder(OrderRequest request) {
		// TODO Auto-generated method stub
		
		Orders orders = new Orders();		
		orders.setAddress(request.getAddress());
		orders.setAmount(request.getAmount());
		orders.setCustomerId(request.getCustomerId());
		orders.setCustomerName(request.getCustomerName());
		orders.setProductId(request.getProductId());
		orders.setProductName(request.getProductName());
		orders.setQuantity(request.getQuantity());		
		
		return orders;
	}
	
	
	
	
	private OrderDetailedResponse orderToOrderDetailedResponse(Orders orders) {
		
		OrderDetailedResponse detailedOrder = new OrderDetailedResponse();
		
		detailedOrder.setAddress(orders.getAddress());
		detailedOrder.setAmount(orders.getAmount());
		detailedOrder.setCustomerId(orders.getCustomerId());
		detailedOrder.setCustomerName(orders.getCustomerName());
		detailedOrder.setOrderId(orders.getOrderId());
		detailedOrder.setProductId(orders.getProductId());
		detailedOrder.setProductName(orders.getProductName());
		detailedOrder.setQuantity(orders.getQuantity());
		detailedOrder.setStatus(orders.getStatus());
		
		return detailedOrder;
	}
}
