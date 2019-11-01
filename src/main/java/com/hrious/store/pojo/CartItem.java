package com.hrious.store.pojo;

public class CartItem {
	private Product product; // 商品信息
	private int quantity; // 商品数量
	private double total; // 小计
	private double integral; // 积分
	@Override
	public String toString() {
		return "CartItem [product=" + product + ", quantity=" + quantity + ", total=" + total + ", integral=" + integral
				+ "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(integral);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + quantity;
		temp = Double.doubleToLongBits(total);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartItem other = (CartItem) obj;
		if (Double.doubleToLongBits(integral) != Double.doubleToLongBits(other.integral))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (quantity != other.quantity)
			return false;
		if (Double.doubleToLongBits(total) != Double.doubleToLongBits(other.total))
			return false;
		return true;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public double getIntegral() {
		return integral;
	}
	public void setIntegral(double integral) {
		this.integral = integral;
	}
	public CartItem(Product product, int quantity, double total, double integral) {
		super();
		this.product = product;
		this.quantity = quantity;
		this.total = total;
		this.integral = integral;
	}
	public CartItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
