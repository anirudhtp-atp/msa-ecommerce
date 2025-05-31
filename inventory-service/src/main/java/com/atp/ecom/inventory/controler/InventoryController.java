package com.atp.ecom.inventory.controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atp.ecom.inventory.model.Inventory;
import com.atp.ecom.inventory.service.InventoryService;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

	@Autowired
	InventoryService inventoryService;

	@GetMapping("/check")
	public boolean checkStock(@RequestParam Long productId, @RequestParam int quantity) {
		return inventoryService.isInStock(productId, quantity);
	}

	@PutMapping("/deduct")
	public void deductStock(@RequestParam Long productId, @RequestParam int quantity) {
		inventoryService.deductStock(productId, quantity);
	}

	@PutMapping("/restock")
	public void reStock(@RequestParam Long productId, @RequestParam int quantity) {
		inventoryService.reStock(productId, quantity);
	}

	@GetMapping
	public List<Inventory> getAll() {
		return inventoryService.getAll();
	}

}
