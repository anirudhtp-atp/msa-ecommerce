package com.atp.ecom.inventory.dto;

public class StockUpdateEvent {

	String productId;
	int quanitity;
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public int getQuanitity() {
		return quanitity;
	}
	public void setQuanitity(int quanitity) {
		this.quanitity = quanitity;
	}
	@Override
	public String toString() {
		return "StockUpdateEvent [productId=" + productId + ", quanitity=" + quanitity + "]";
	}
	
}
