package com.atp.ecom.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atp.ecom.auth.dto.LoginRequest;
import com.atp.ecom.auth.dto.SignupRequest;
import com.atp.ecom.auth.model.Credential;
import com.atp.ecom.auth.repository.CredentialRepository;

@Service
public class CredentialService {
	
	private static final Logger log = LoggerFactory.getLogger(CredentialService.class);


	@Autowired
	CredentialRepository credentialRepository;
	
	@Autowired
	JwtUtil jwtUtil;
	
	public Credential register(SignupRequest request) throws Exception {
		if(credentialRepository.findByEmail(request.getEmail()) !=null) {
			throw new Exception("Email already exists");
		}
		
		Credential credential = new Credential();
		
		credential.setEmail(request.getEmail());
		credential.setPassword(request.getPassword());
		credential.setRole(request.getRole());
		
		credential = credentialRepository.save(credential);
		
		return credential;
		
	}
	
	public String login(LoginRequest request) throws Exception {
		
		Credential credential =  credentialRepository.findByEmail(request.getEmail());
		if(credential == null  || !credential.getPassword().equals(request.getPassword())) {
			throw new Exception("Invalid credentials");
		}
		
		String token = jwtUtil.genereateToken(credential);
		return token;
	}

	public String validate(String token) {
		try {
			jwtUtil.validateToken(token);
			return "VALID_TOKEN";
		} catch (Exception e) {
			return "INVALID_TOKEN";
		}
	}
	
}
