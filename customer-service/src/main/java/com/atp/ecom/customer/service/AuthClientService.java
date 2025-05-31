package com.atp.ecom.customer.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AuthClientService {

	private static final Logger log = LoggerFactory.getLogger(AuthClientService.class);

	@Autowired
	private ApplicationContext context;

	public boolean validateToken(String token) {
		log.info("AuthClientService.validateToken() called with token: " + token);

		WebClient authValidateWebClient = context.getBean("authServiceWebClient", WebClient.class);

		log.info("Calling auth-service to validate token: " + token);

		String authResponse = authValidateWebClient.get().uri("/validate").header("Authorization", token).retrieve()
				.bodyToMono(String.class).block();

		log.info("Response from auth-service : " + token);

		if (authResponse.equals("VALID_TOKEN")) {
			log.info("Token is valid");
			return true;
		} else if (authResponse.equals("INVALID_TOKEN")) {
			log.info("Token is invalid");
			return false;
		} else {
			log.info("Error in auth-service: " + authResponse);
			throw new RuntimeException("Error in auth-service: " + authResponse);

		}

	}

	public void registerUser(Map<String, String> request) {
		log.info("AuthClientService.registerUser() called with token: " + request.toString());

		WebClient authValidateWebClient = context.getBean("authServiceWebClient", WebClient.class);

		String authResponse = authValidateWebClient.post().uri("/register").bodyValue(request).retrieve()
				.bodyToMono(String.class).block();

		log.info("Response from auth-service : " + authResponse);

	}
}
