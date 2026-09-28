package com.ecom.paymentservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecom.paymentservice.entity.Payment;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {

}
