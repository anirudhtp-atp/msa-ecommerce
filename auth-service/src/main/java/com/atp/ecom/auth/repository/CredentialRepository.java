package com.atp.ecom.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atp.ecom.auth.model.Credential;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {
	
	Credential findByEmail(String email);
}
