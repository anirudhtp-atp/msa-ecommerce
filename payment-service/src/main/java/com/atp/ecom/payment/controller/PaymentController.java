package com.atp.ecom.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atp.ecom.payment.model.Payment;
import com.atp.ecom.payment.service.PaymentService;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

	@Autowired
	PaymentService paymentService;

	@PostMapping
	public Payment makePayment(@RequestBody Payment payment,
			@CookieValue(value = "X-Request-ID", required = false) String requestId) {
		return paymentService.processPayment(payment, requestId);
	}

	@GetMapping("/{orderId}")
	public Payment getPayment(@PathVariable Long orderId) {
		return paymentService.getPaymentByOrderId(orderId);
	}

}
