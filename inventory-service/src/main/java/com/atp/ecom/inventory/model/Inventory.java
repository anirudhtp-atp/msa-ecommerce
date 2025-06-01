package com.atp.ecom.inventory.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "inventory")
public class Inventory {

	public Inventory() {

	}

	public Inventory(String productId, int stock) {
		this.productId = productId;
		this.stock = stock;
	}

	@Id
	private String productId;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	private int stock;
	
	
	
	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return "Inventory [productId=" + productId + ", stock=" + stock + "]";
	}

	
}
