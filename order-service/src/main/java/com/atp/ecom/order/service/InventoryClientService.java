package com.atp.ecom.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class InventoryClientService {

	private final ApplicationContext context;

	private final WebClient webClient;

	InventoryClientService(WebClient.Builder builder, ApplicationContext context) {
		
		this.context = context;
		this.webClient = this.context.getBean("inventoryServiceWebClient", WebClient.class);
	}
	
	public boolean isInStock(String productId, int quantity) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/check")
                        .queryParam("productId", productId)
                        .queryParam("quantity", quantity)
                        .build())
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }

}
