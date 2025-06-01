package com.atp.ecom.order.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atp.ecom.order.model.Order;
import com.atp.ecom.order.service.OrderService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

	@Autowired
	OrderService orderService;

	@PostMapping
	public ResponseEntity<?> placeOrder(@RequestBody Order order,
			@CookieValue(value = "X-Request-ID", required = false) String requestId, HttpServletResponse response) {

		if (requestId == null || requestId.isBlank()) {
			requestId = UUID.randomUUID().toString();

			Cookie cookie = new Cookie("X-Request-ID", requestId);
			cookie.setHttpOnly(true);
			cookie.setPath("/");
			cookie.setMaxAge(900); // 15 minutes (matching Redis TTL)
			response.addCookie(cookie);
		}

		return ResponseEntity.ok(orderService.createOrder(order));

	}

	@GetMapping("/customer")
	public ResponseEntity<?> getCustomerOrders(@RequestParam String email) {
		return ResponseEntity.ok(orderService.getOrdersOfCustomer(email));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getbyId(@PathVariable Long id) {
		return ResponseEntity.ok(orderService.getOrderById(id));
	}

	@PutMapping("/{id}/cancel")
	public ResponseEntity<?> cancelOrder(@PathVariable Long id) {
		orderService.cancelOrder(id);

		return ResponseEntity.ok("Order cancelled");

	}

}
