package com.atp.ecom.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atp.ecom.payment.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	Payment findByOrderId(Long orderId);
}
