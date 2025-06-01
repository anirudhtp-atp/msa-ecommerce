package com.atp.ecom.order.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atp.ecom.order.dto.StockUpdateEvent;
import com.atp.ecom.order.model.Order;
import com.atp.ecom.order.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class OrderService {
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	InventoryClientService inventoryClientService;
	
	@Autowired
	StockUpdateEventProducer stockUpdateEventProducer;
	
	public Order createOrder(Order order ) {
		
		order.getProducts().forEach((productId, quantity)->{
			boolean isAvailable = inventoryClientService.isInStock(productId, quantity);
			if(!isAvailable) {
				throw new IllegalStateException("Product ID is out of stock");
			}
		});
		
		order.setStatus("CREATED");
		order.setCreatedAt(LocalDateTime.now());
		
		order = orderRepository.save(order);
		
		order.getProducts().forEach((product, quantity)->{
			StockUpdateEvent data = new StockUpdateEvent();
			data.setQuanitity(quantity);
			data.setProductId(product);
			try {
				this.stockUpdateEventProducer.publishStockUpdateEvent(data);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		});
		
		return order;
	}
	
	public List<Order> getOrdersOfCustomer(String email){
		return orderRepository.findByEmail(email);
	}
	
	public Order getOrderById(Long id) {
		return orderRepository.findById(id).orElse(null);
	}
	
	public void cancelOrder(Long id) {
		Order order = getOrderById(id);
		
		if(order != null && !order.getStatus().equals("SHIPPED")){
			order.setStatus("CANCELLED");
			orderRepository.save(order);
		}
	}

}
