package com.atp.ecom.order.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.atp.ecom.order.DTO.PaymentDto;
import com.atp.ecom.order.dto.StockUpdateEvent;
import com.atp.ecom.order.model.Order;
import com.atp.ecom.order.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import reactor.core.publisher.Mono;

@Service
public class OrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	ApplicationContext context;

	@Autowired
	StockUpdateEventProducer stockUpdateEventProducer;

	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	public String getCurrentOrderStatus(String requestId) {
		LOGGER.info("Getting current status for request " +  requestId);
		LOGGER.info(redisTemplate.opsForValue().get(requestId).toString());
		return redisTemplate.opsForValue().get(requestId).toString();
	}

	public String createOrder(Order order, String requestId, HttpServletResponse response) {
//
//		LOGGER.info("Processing order........");
//
//		redisTemplate.opsForValue().set(requestId, "Order proccessing : Inventory check");
		
		WebClient inventpryWebClient = this.context.getBean("inventoryServiceWebClient", WebClient.class);

		LOGGER.info("Inventory check......");
		
		// Blocking inventory check - checking availability of each item
		order.getProducts().forEach((productId, quantity) -> {

			boolean isAvailable = inventpryWebClient.get().uri(uriBuilder -> uriBuilder.path("/check")
					.queryParam("productId", productId).queryParam("quantity", quantity).build()).retrieve()
					.bodyToMono(Boolean.class).block();

			if (!isAvailable) {
				redisTemplate.opsForValue().set(requestId, "Order proccessing : Out of stock");
				throw new IllegalStateException("Product ID is out of stock");
			}
		});

		order.setCreatedAt(LocalDateTime.now());
		LOGGER.info("Saving order ......");
		order = orderRepository.save(order);
		

		LOGGER.info("Updating inventory");
		order.getProducts().forEach((product, quantity) -> {
			StockUpdateEvent data = new StockUpdateEvent();
			data.setQuanitity(quantity);
			data.setProductId(product);
			try {
				this.stockUpdateEventProducer.publishStockUpdateEvent(data);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		});
		
		// Asynchronus block

		redisTemplate.opsForValue().set(requestId, "Order proccessing : Payment processing");

		WebClient paymentServiceWebClient = this.context.getBean("paymentServiceWebClient", WebClient.class);

		PaymentDto paymentDto = new PaymentDto();
		paymentDto.setAmount(order.getTotalAmount());
		paymentDto.setOrderId(order.getId());
		paymentDto.setPaymentMethod("ONLINE");
		LOGGER.info("Initiating payment.......");
		Mono<PaymentDto> paymentServiceResponse = paymentServiceWebClient.post()
				.header("COOKIE", "X-Request-ID=" + requestId).contentType(MediaType.APPLICATION_JSON)
				.bodyValue(paymentDto).retrieve().bodyToMono(PaymentDto.class);

		paymentServiceResponse.subscribe(res -> {
			LOGGER.info("Payment initiated " + res.toString());
			redisTemplate.opsForValue().set(requestId, "Order proccessing : Completed");
		});

		return redisTemplate.opsForValue().get(requestId).toString();

	}

	public List<Order> getOrdersOfCustomer(String email) {
		return orderRepository.findByEmail(email);
	}

	public Order getOrderById(Long id) {
		return orderRepository.findById(id).orElse(null);
	}

	public void cancelOrder(Long id) {
		Order order = getOrderById(id);

		if (order != null && !order.getStatus().equals("SHIPPED")) {
			order.setStatus("CANCELLED");
			orderRepository.save(order);
		}
	}

}
