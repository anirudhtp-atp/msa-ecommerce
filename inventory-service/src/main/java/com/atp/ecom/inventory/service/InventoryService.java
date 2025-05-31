package com.atp.ecom.inventory.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atp.ecom.inventory.model.Inventory;
import com.atp.ecom.inventory.repository.InventoryRepository;
import com.atp.ecom.inventory.repository.InventoryRepository;

@Service
public class InventoryService {

	@Autowired
	InventoryRepository inventoryRepository;

	public boolean isInStock(Long productId, int quantity) {
		Optional<Inventory> inventory = inventoryRepository.findById(productId);

		return inventory.map(inv -> inv.getStock() >= quantity).orElse(false);
	}

	public void deductStock(Long productId, int quantity) {
		Inventory inventory = inventoryRepository.findById(productId)
				.orElseThrow(() -> new IllegalArgumentException("Product not found in inventory"));
		
		if(inventory.getStock() < quantity) {
			throw new IllegalStateException("Insufficient Stock");
		}
		
		inventory.setStock(inventory.getStock() - quantity);
		inventoryRepository.save(inventory);
		
	}
	
	public void reStock(Long productId, int quantity) {
		Inventory inventory = inventoryRepository.findById(productId)
				.orElse( new Inventory(productId, 0));
		
		inventory.setStock(inventory.getStock() + quantity);
		inventoryRepository.save(inventory);
		
	}
	
	public List<Inventory> getAll(){
		return inventoryRepository.findAll();
	}
	
}
