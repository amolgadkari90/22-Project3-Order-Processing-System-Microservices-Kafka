package com.ecom.orderservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecom.orderservice.entity.Orders;

@Repository
public interface OrderRepository extends CrudRepository<Orders, Long> {

}
