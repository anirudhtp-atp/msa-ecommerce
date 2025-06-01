package com.atp.ecom.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atp.ecom.inventory.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, String> {
	
}
