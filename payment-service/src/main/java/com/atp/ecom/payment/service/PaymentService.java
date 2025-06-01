package com.atp.ecom.payment.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atp.ecom.payment.model.Payment;
import com.atp.ecom.payment.repository.PaymentRepository;

@Service
public class PaymentService {

	
	@Autowired
	PaymentRepository paymentRepository;
	
	
	public Payment processPayment(Payment payment) {
       
                payment.setStatus("SUCCESS");
                payment.setPaidAt(LocalDateTime.now());
                
        return paymentRepository.save(payment);
    }

    public Payment getPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }
	
	
}
