package com.hrious.store.pojo;

import java.util.HashMap;
import java.util.Map;

public class Cart {
	private Map<String, CartItem> cartItems = new HashMap<String, CartItem>(); // 存放购物项
	private double integral; // 积分
	private double total; // 总价格
	
	/**
	 * 添加商品到购物车
	 * @param newItem
	 */
	public void addCartItem2Cart(CartItem newItem) {
		// 获取商品
		String pid = newItem.getProduct().getPid();
		
		// 计算价格
		double price = this.total + newItem.getTotal();
		// 计算积分
		double inte = this.integral + newItem.getIntegral();
		
		if (!this.cartItems.containsKey(pid)) { // 如果该商品不在购物车中
			// 添加到购物车
			this.cartItems.put(pid, newItem);
			
			// 重新计算购物车
			this.total = price;
			this.integral = inte;
		} else { // 购物车中拥有该商品
			// 购物车中原有的商品
			CartItem item = cartItems.get(pid);
			
			item.setQuantity(newItem.getQuantity() + item.getQuantity());
			item.setTotal(newItem.getTotal() + item.getTotal());
			item.setIntegral(newItem.getIntegral() + item.getIntegral());
			
			// 重新计算购物车
			this.total = price;
			this.integral = inte;
		}
	}
	
	/**
	 * 从购物车中移除商品
	 * @param cartItem
	 */
	public void removeCartItemFromCart(String pid) {
		// 获取商品
		CartItem item = cartItems.get(pid);
		// 重新计算购物车
		this.total -= item.getTotal();
		this.integral -= item.getIntegral();
		// 移除商品
		cartItems.remove(pid);
	}
	
	/**
	 * 清空购物车
	 */
	public void clear() {
		this.cartItems.clear();
		this.total = 0;
		this.integral = 0;
	}
	
	/**
	 * 判断购物车是否为空
	 * @return
	 */
	public boolean isEmpty() {
		return this.cartItems.isEmpty();
	}
	
	@Override
	public String toString() {
		return "Cart [cartItems=" + cartItems + ", integral=" + integral + ", total=" + total + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cartItems == null) ? 0 : cartItems.hashCode());
		long temp;
		temp = Double.doubleToLongBits(integral);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Cart other = (Cart) obj;
		if (cartItems == null) {
			if (other.cartItems != null)
				return false;
		} else if (!cartItems.equals(other.cartItems))
			return false;
		if (Double.doubleToLongBits(integral) != Double.doubleToLongBits(other.integral))
			return false;
		if (Double.doubleToLongBits(total) != Double.doubleToLongBits(other.total))
			return false;
		return true;
	}
	public Map<String, CartItem> getCartItems() {
		return cartItems;
	}
	public void setCartItems(Map<String, CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	public double getIntegral() {
		return integral;
	}
	public void setIntegral(double integral) {
		this.integral = integral;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public Cart(Map<String, CartItem> cartItems, double integral, double total) {
		super();
		this.cartItems = cartItems;
		this.integral = integral;
		this.total = total;
	}
	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
