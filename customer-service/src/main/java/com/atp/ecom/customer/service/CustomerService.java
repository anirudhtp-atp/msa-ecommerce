package com.atp.ecom.customer.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atp.ecom.customer.dto.CustomerRegistrationRequest;
import com.atp.ecom.customer.model.Customer;
import com.atp.ecom.customer.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	AuthClientService authClientService;

	public Customer createCustomer(CustomerRegistrationRequest customerRegReq) {
		
		Map<String, String> request = new HashMap<String, String>();
		request.put("email", customerRegReq.getEmail());
		request.put("password", customerRegReq.getPassword());
		request.put("role", "CUSTOMER");
		
		authClientService.registerUser(request);
		
		Customer customer = new Customer();
		customer.setEmail(customerRegReq.getEmail());
		customer.setAddress(customerRegReq.getAddress());
		customer.setFullName(customerRegReq.getFullName());
		customer.setPhone(customerRegReq.getPhone());
		
		customer = customerRepository.save(customer);
		
		

		return customer;

	}

	public Optional<Customer> getCustomerById(Long id) {
		return customerRepository.findById(id);
	}

	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	public Customer updateCustomer(Long id, Customer updatedCustomer) {
		return customerRepository.findById(id).map(customer -> {
			customer.setFullName(updatedCustomer.getFullName());
			customer.setPhone(updatedCustomer.getPhone());
			customer.setAddress(updatedCustomer.getAddress());
			return customerRepository.save(customer);
		}).orElseThrow(() -> new RuntimeException("Customer not found"));
	}

	public void deleteCustomer(Long id) {
		customerRepository.deleteById(id);
	}

}
