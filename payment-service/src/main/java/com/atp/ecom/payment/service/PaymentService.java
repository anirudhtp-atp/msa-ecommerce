package com.atp.ecom.payment.service;

import java.time.Duration;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.atp.ecom.payment.model.Payment;
import com.atp.ecom.payment.repository.PaymentRepository;

import reactor.core.publisher.Mono;

@Service
public class PaymentService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

	@Autowired
	PaymentRepository paymentRepository;

	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	public Payment processPayment(Payment payment, String requestId) {

		payment.setStatus("INITIATED");
		payment.setPaidAt(LocalDateTime.now());
		payment = paymentRepository.save(payment);
		
		LOGGER.info(requestId);
		LOGGER.info("Order proccessing : Payment processing : Initiated");
		
		LOGGER.info(redisTemplate.opsForValue().get(requestId).toString());
		
		redisTemplate.opsForValue().set(requestId, "Order proccessing : Payment processing : Initiated");
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		LOGGER.info(requestId);
		LOGGER.info("Order proccessing : Payment processing : Completed");
    	redisTemplate.opsForValue().set(requestId, "Order proccessing : Payment processing : Completed");
    	
    	try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return payment;
	}

	public Payment getPaymentByOrderId(Long orderId) {
		return paymentRepository.findByOrderId(orderId);
	}

}
