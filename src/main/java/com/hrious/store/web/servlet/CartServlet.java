package com.hrious.store.web.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hrious.store.pojo.Cart;
import com.hrious.store.service.CartService;
import com.hrious.store.service.impl.CartServiceImpl;
import com.hrious.store.web.base.BaseServlet;

public class CartServlet extends BaseServlet {
	
	private CartService cateService = new CartServiceImpl(); 
	
	public String cartUI(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "/jsp/cart.jsp";
	}
	
	public String addCartItem2Cart(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取请求参数
		String pid = request.getParameter("pid");
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		// 从session中获取购物车
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		// 调用业务层进行处理
		cart = cateService.addCartItem2Cart(cart, pid, quantity);
		// 把处理结果添加到session中
		session.setAttribute("cart", cart);
		// 重定向到/jsp/cart.jsp
		response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
		return null;
	}
	
	public String removeCartItemFromCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取pid
		String pid = request.getParameter("pid");
		// 获取session
		HttpSession session = request.getSession();
		// 获取购物车
		Cart cart = (Cart) session.getAttribute("cart");
		// 调用业务层进行处理
		cart = cateService.removeCartItemFromCart(cart, pid);
		// 重新设置session
		session.setAttribute("cart", cart);
		// 重定向到/jsp/cart.jsp
		response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
		return null;
	}
	
	public String removeCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取session
		HttpSession session = request.getSession();
		// 获取购物车
		Cart cart = (Cart) session.getAttribute("cart");
		// 调用业务层进行处理
		cateService.clearCart(cart);
		// 重定向到/jsp/cart.jsp
		response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
		return null;
	}
}
