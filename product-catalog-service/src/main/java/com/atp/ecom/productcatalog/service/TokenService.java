package com.atp.ecom.productcatalog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class TokenService {

	private static final Logger log = LoggerFactory.getLogger(TokenService.class);

	@Autowired
	private ApplicationContext context;

	public boolean validateToken(String token) {
		log.info("TokenService.validateToken() called with token: " + token);

		WebClient authValidateWebClient = context.getBean("authValidateWebClient", WebClient.class);

		log.info("Calling auth-service to validate token: " + token);

		String authResponse = authValidateWebClient.get().header("Authorization", token).retrieve()
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
}
