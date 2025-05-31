package com.atp.ecom.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atp.ecom.auth.dto.LoginRequest;
import com.atp.ecom.auth.dto.SignupRequest;
import com.atp.ecom.auth.service.CredentialService;

@RestController
@RequestMapping("/api/v1")
public class CredentialController {
	
	private static final Logger log = LoggerFactory.getLogger(CredentialController.class);

	@Autowired
	CredentialService credentialService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody SignupRequest request) {
		try {
			credentialService.register(request);
			return ResponseEntity.ok("User registered successfully");
		} catch (Exception e) {
			log.error("CredentialController.create() error : " + e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());

		}

	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		try {
			String token = credentialService.login(request);
			return ResponseEntity.ok(token);
		} catch (Exception e) {
			log.error("CredentialController.login() error : " + e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/validate")
	public ResponseEntity<?> validate(@RequestHeader("Authorization") String token) {

		String result = credentialService.validate(token);
		return ResponseEntity.ok(result);

	}
}
