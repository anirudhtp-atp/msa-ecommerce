package com.atp.ecom.order.DTO;

import java.time.LocalDateTime;

public class PaymentDto {
	private Long id;

	private Long orderId;

	private String paymentMethod;
	private double amount;
	private String status;
	private LocalDateTime paidAt;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getPaidAt() {
		return paidAt;
	}

	public void setPaidAt(LocalDateTime paidAt) {
		this.paidAt = paidAt;
	}

	@Override
	public String toString() {
		return "PaymentDto [id=" + id + ", orderId=" + orderId + ", paymentMethod=" + paymentMethod + ", amount="
				+ amount + ", status=" + status + ", paidAt=" + paidAt + "]";
	}

}
