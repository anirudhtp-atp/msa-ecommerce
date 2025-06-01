package com.atp.ecom.inventory.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.atp.ecom.inventory.dto.StockUpdateEvent;

@Configuration
public class KafkaConfig {

//	@Bean
//	ConsumerFactory<String, StockUpdateEvent> stockUpdateConsumerFactory() {
//		
//		JsonDeserializer<StockUpdateEvent> deserializer = new JsonDeserializer<>(StockUpdateEvent.class);
//		deserializer.addTrustedPackages("*");
//		
//		Map<String, Object> config = new HashMap<>();
//		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
//	}
//
//	@Bean
//	ConcurrentKafkaListenerContainerFactory<String, StockUpdateEvent> stockUpdateConsumerListenerContainerFactory() {
//		ConcurrentKafkaListenerContainerFactory<String, StockUpdateEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
//		factory.setConsumerFactory(stockUpdateConsumerFactory());
//		return factory;
//	}
}
