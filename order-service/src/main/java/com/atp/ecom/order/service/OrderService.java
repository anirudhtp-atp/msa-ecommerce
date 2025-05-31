package com.atp.ecom.order.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atp.ecom.order.model.Order;
import com.atp.ecom.order.repository.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	InventoryClientService inventoryClientService;
	
	public Order createOrder(Order order ) {
		order.getProductIds().forEach(id->{
			boolean isAvailable = inventoryClientService.isInStock(id, 1);
		});
		order.setStatus("CREATED");
		order.setCreatedAt(LocalDateTime.now());
		
		return orderRepository.save(order);
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
