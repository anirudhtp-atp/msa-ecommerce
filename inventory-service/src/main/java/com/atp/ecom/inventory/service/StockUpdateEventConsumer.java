package com.atp.ecom.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.atp.ecom.inventory.dto.StockUpdateEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class StockUpdateEventConsumer {

	@Autowired
	InventoryService inventoryService;
	
	@Autowired
	ObjectMapper mapper;
	
	@KafkaListener(topics = "re-stock-event",groupId = "inventory-group")
	public void handleStockUpdateEvent(String message) throws JsonMappingException, JsonProcessingException {
		
		StockUpdateEvent event = mapper.readValue(message, StockUpdateEvent.class);
		
		inventoryService.reStock(event.getProductId(), event.getQuanitity());
	}
	
	@KafkaListener(topics = "deduct-stock-event", groupId = "inventory-group")
	public void handleStockDeductEvent(String message) throws JsonMappingException, JsonProcessingException {
		StockUpdateEvent event = mapper.readValue(message, StockUpdateEvent.class);
		inventoryService.deductStock(event.getProductId(), event.getQuanitity());
	}
	
}
