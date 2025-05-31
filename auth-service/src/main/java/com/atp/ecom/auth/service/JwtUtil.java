package com.atp.ecom.auth.service;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.atp.ecom.auth.model.Credential;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	private final String SECRET_KEY = "x/ErRAsoCsd1dn6ddcL9yXp7lUJvGKhgL95ktxM0VB8=";

	public String genereateToken(Credential credential) {
		return Jwts.builder().setSubject(credential.getEmail()).claim("role", credential.getRole())
				.setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + 1 * 60 * 60 * 1000))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public Claims validateToken(String token) throws Exception {
		try {

			return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();

		} catch (MalformedJwtException e) {
			throw new Exception("INVALID_TOKEN");
		}
	}

}
