package com.atp.ecom.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atp.ecom.customer.model.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Customer findByEmail(String email);
}
