package com.atp.ecom.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atp.ecom.auth.dto.LoginRequest;
import com.atp.ecom.auth.dto.SignupRequest;
import com.atp.ecom.auth.model.Credential;
import com.atp.ecom.auth.repository.CredentialRepository;

@Service
public class CredentialService {

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
		if(credential == null  || !credential.equals(request.getPassword())) {
			throw new Exception("Invalid credentials");
		}
		
		String token = jwtUtil.genereateToken(credential);
		return token;
	}
	
}
