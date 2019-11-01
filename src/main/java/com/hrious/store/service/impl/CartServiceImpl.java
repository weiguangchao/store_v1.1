package com.hrious.store.service.impl;

import java.sql.SQLException;

import com.hrious.store.dao.ProductDao;
import com.hrious.store.pojo.Cart;
import com.hrious.store.pojo.CartItem;
import com.hrious.store.pojo.Product;
import com.hrious.store.service.CartService;
import com.hrious.store.utils.BeanFactory;

public class CartServiceImpl implements CartService {
	
	private ProductDao productDao = (ProductDao) BeanFactory.getObject("productDao");

	@Override
	public Cart addCartItem2Cart(Cart cart, String pid, int quantity) {
		if (null == cart) { // 如果购物车不存在
			cart = new Cart();
		}
		
		// 根据pid获取到商品
		Product product = null;
		try {
			product = productDao.selProductByPid(pid);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		// 计算商品的价格和积分
		double price = product.getShop_price() * quantity;
		double integral = price;
		
		// 创建CartItem
		CartItem cartItem = new CartItem(product, quantity, price, integral);
		// 添加商品到购物车
		cart.addCartItem2Cart(cartItem);
		return cart;
	}

	@Override
	public Cart removeCartItemFromCart(Cart cart, String pid) {
		cart.removeCartItemFromCart(pid);
		if (cart.getCartItems().size() > 0) {
			return cart;
		}
		return null;
	}

	@Override
	public void clearCart(Cart cart) {
		cart.clear();
	}

}
