package com.hrious.store.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hrious.store.pojo.Cart;
import com.hrious.store.pojo.CartItem;
import com.hrious.store.pojo.Order;
import com.hrious.store.pojo.OrderItem;
import com.hrious.store.pojo.PageModel;
import com.hrious.store.pojo.User;
import com.hrious.store.service.CartService;
import com.hrious.store.service.OrderService;
import com.hrious.store.service.impl.CartServiceImpl;
import com.hrious.store.service.impl.OrderServiceImpl;
import com.hrious.store.utils.UUIDUtils;
import com.hrious.store.web.base.BaseServlet;

public class OrderServlet extends BaseServlet {
	
	private OrderService orderService = new OrderServiceImpl();
	private CartService cartService = new CartServiceImpl();
	
	public String saveOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 判断用户是否登录，如果没有登录-的话就迫使用户登录
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (null == user) { // 用户没有登录
			// 重定向到登录界面
			session.setAttribute("msg", "请先登录！");
			response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
			return null;
		}
		// 从session中获取购物车
		Cart cart = (Cart) session.getAttribute("cart");
		
		// 购物车为空
		if (null == cart || cart.isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/jsp/index.jsp");
			return null;
		}
		
		// 创建订单对象
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		
		// 把购物车中的购物项对象封装成订单中的购物项对象
		Order order = new Order();
		order.setOid(UUIDUtils.getCode());
		order.setOrdertime(new Date());
		order.setTotal(cart.getTotal());
		order.setState(1);
		order.setUser(user);
		
		
		// 创建订单项对象
		OrderItem orderItem = null;
		Collection<CartItem> cartItems = cart.getCartItems().values();
		for (CartItem cartItem : cartItems) {
			orderItem = new OrderItem();
			orderItem.setItemid(UUIDUtils.getCode());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setTotal(cartItem.getTotal());
			orderItem.setOid(order.getOid());
			orderItem.setProduct(cartItem.getProduct());
			
			// 添加
			orderItemList.add(orderItem);
		}
		order.setList(orderItemList);
		
		// 调用业务层处理订单和订单项
		orderService.saveOrder(order);
		
		// 把Orders对象放入request中
		request.setAttribute("order", order);
		// 销毁购物车
		cartService.clearCart(cart);
		// 请求转发到/jsp/order_info.jsp
		return "/jsp/order_info.jsp";
	}
	
	public String showOrders(HttpServletRequest request, HttpServletResponse response) throws IOException {
		User user = (User) request.getSession().getAttribute("user");
		// (1)创建showOrders方法
		// (2)获取请求中的page、rows以及请求的用户id
		String pageStr = request.getParameter("page");
		String rowsStr = request.getParameter("rows");
		int page = pageStr == null ? 1 : Integer.parseInt(pageStr);
		int rows = rowsStr == null ? 4 : Integer.parseInt(rowsStr);
		// (3)调用业务层进行处理
		PageModel pageModel = orderService.showOrdersWithPage(rows, page, user);
		// (4)把装有订单的集合放入到request中
		request.setAttribute("page", pageModel);
		// (5)请求转发到/jsp/order_list.jsp
		return "/jsp/order_list.jsp";
	}
	
	public String getOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// (1)获取请求参数（oid）
		String oid = request.getParameter("oid");
		// (2)调用业务层进行处理（getOrder），返回查询到的order对象
		Order order = orderService.getOrder(oid);
		// (3)把order对象放入到request中
		request.setAttribute("order", order);
		// (4)请求转发到/jsp/order_info.jsp
		return "/jsp/order_info.jsp";
		
	}
	
	public String payOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取请求参数
		String oid = request.getParameter("oid");
		String address = request.getParameter("address");
		String name = request.getParameter("name");
		String telephone = request.getParameter("telephone");
		String payMet = request.getParameter("pay_met");
		
		// 调用业务层进行处理
		if ("0".equals(payMet)) { // 调用支付宝支付
			String form = orderService.alipayOrder(oid, name, address, telephone);
			PrintWriter out = response.getWriter();
			response.setContentType("text/html;charset=utf-8");
			out.write(form); // 直接输出支部包表单信息
			out.flush();
			out.close();
			return null;
		} else if ("1".equals(payMet)) { // 调用微信支付
			System.out.println("微信");
			request.setAttribute("msg", "微信支付暂不支持，请使用支付宝支付!");
			return "/jsp/info.jsp";
		} else {
			System.out.println("支付错误");
			request.setAttribute("msg", "支付错误!");
			return "/jsp/info.jsp";
		}
	}
}
