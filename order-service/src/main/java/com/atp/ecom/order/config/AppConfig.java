package com.atp.ecom.order.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

	@Autowired
	EurekaDiscoveryClient discoveryClient;
	
	@Bean
	@Scope("prototype")
	WebClient inventoryServiceWebClient(WebClient.Builder webClientBuilder) {
		
		List<ServiceInstance> instances = discoveryClient.getInstances("inventory-service");
		
		String baseUrl = instances.get(0).getUri().toString();
		
		return webClientBuilder.baseUrl(String.format("%s/api/v1/inventory", baseUrl)).build();
		
		
	}
	
}
