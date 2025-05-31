package com.atp.ecom.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atp.ecom.auth.CredentialService;
import com.atp.ecom.auth.constants.ExceptionMessages;
import com.atp.ecom.auth.dto.LoginRequest;
import com.atp.ecom.auth.dto.SignupRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class CredentialController {

	@Autowired
	CredentialService credentialService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody SignupRequest request) {
		try {
			credentialService.register(request);
			return ResponseEntity.ok("User registered successfully");
		} catch (Exception e) {
			
			return ResponseEntity.badRequest().body(e.getMessage());

		}

		
	}
	
	@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		try {
			String token = credentialService.login(request);
			return ResponseEntity.ok(token);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
