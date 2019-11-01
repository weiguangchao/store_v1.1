package com.hrious.store.service;

import com.hrious.store.pojo.Cart;

public interface CartService {

	/**
	 * 添加商品到购物车中
	 * @param cart
	 * @param pid
	 * @param quantity
	 * @return
	 */
	Cart addCartItem2Cart(Cart cart, String pid, int quantity);

	/**
	 * 从购物车中移除商品
	 * @param cart
	 * @param pid
	 * @return
	 */
	Cart removeCartItemFromCart(Cart cart, String pid);

	/**
	 * 清空购物车
	 * @param cart
	 * @return
	 */
	void clearCart(Cart cart);

}
