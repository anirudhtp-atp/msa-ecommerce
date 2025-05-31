package com.atp.ecom.inventory.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "inventory")
public class Inventory {

	public Inventory() {

	}

	public Inventory(Long productId, int stock) {
		this.productId = productId;
		this.stock = stock;
	}

	@Id
	private Long productId;

	private int stock;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

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
