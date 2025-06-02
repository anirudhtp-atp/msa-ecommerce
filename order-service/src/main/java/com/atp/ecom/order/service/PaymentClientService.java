package com.atp.ecom.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.atp.ecom.order.DTO.PaymentDto;

import reactor.core.publisher.Mono;

@Service
public class PaymentClientService {

	private final ApplicationContext context;

	private final WebClient webClient;

	PaymentClientService(WebClient.Builder builder, ApplicationContext context) {
		
		this.context = context;
		this.webClient = this.context.getBean("paymentServiceWebClient", WebClient.class);
	}
	
	public Mono<PaymentDto> initiatePayment(PaymentDto payment) {
        return webClient.post()
        		.contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payment)
                .retrieve()
                .bodyToMono(PaymentDto.class);
    }

}
