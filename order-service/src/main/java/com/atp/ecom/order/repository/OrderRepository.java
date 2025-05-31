package com.atp.ecom.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atp.ecom.order.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findByEmail(String email);
}
