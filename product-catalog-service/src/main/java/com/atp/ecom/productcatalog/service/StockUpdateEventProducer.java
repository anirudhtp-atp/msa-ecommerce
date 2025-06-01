package com.atp.ecom.productcatalog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.atp.ecom.productcatalog.dto.StockUpdateEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class StockUpdateEventProducer {
	
	private static final Logger logger = LoggerFactory.getLogger(StockUpdateEventProducer.class);
    private static final String TOPIC = "re-stock-event";

	@Autowired 
	private KafkaTemplate<String, String> stockUpdateKafkaTemplate;
	
	@Autowired
	ObjectMapper mapper;
	
	public void publishStockUpdateEvent(StockUpdateEvent event  ) throws JsonProcessingException {
		this.stockUpdateKafkaTemplate.send(TOPIC, mapper.writeValueAsString(event));
	}
}
