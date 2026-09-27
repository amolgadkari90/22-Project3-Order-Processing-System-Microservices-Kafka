package com.ecom.orderservice.controller;


import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.orderservice.dto.OrderDetailedResponse;
import com.ecom.orderservice.dto.OrderRequest;
import com.ecom.orderservice.dto.OrderResponse;
import com.ecom.orderservice.service.OrderService;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	final OrderService orderService;


	OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	
	//create
	
	@PostMapping("/createOrder")
	public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request ){
		
		OrderResponse response =  orderService.createOrder(request);		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);			
	}
		
	//Read
	@GetMapping("/getOrderById/{orderId}")
	public ResponseEntity<OrderDetailedResponse> getByOrderId(@PathVariable long orderId) {
		
		OrderDetailedResponse detailedResponse =  orderService.getByOrderId(orderId);
		
		return ResponseEntity.status(HttpStatus.FOUND).body(detailedResponse);
		
	}
	
	
	
	//Update
	
	//delete
	
	
	
	
	
	

}
