package com.hrious.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.hrious.store.pojo.Order;
import com.hrious.store.pojo.OrderItem;
import com.hrious.store.pojo.PageModel;
import com.hrious.store.service.OrderService;
import com.hrious.store.service.impl.OrderServiceImpl;
import com.hrious.store.web.base.BaseServlet;

public class AdminOrderServlet extends BaseServlet {
	
	private OrderService orderService = new OrderServiceImpl();

	// findOrders
	public String findOrdersWithPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取请求参数
		String state = request.getParameter("state");
		int page = Integer.parseInt(request.getParameter("page"));
		
		PageModel pageModel = orderService.findOrders(state, page);
		request.setAttribute("page", pageModel);
		return "/admin/order/list.jsp";
	}
	
	// findOrderByOid
	public String findOrderByOid(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取请求参数
		String oid = request.getParameter("oid");
		// 调用业务层进行处理
		Order order = orderService.getOrder(oid);
		List<OrderItem> orderItems = order.getList();
		// 响应会浏览器
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(JSON.toJSONString(orderItems));
		return null;
	}
	
	// updateOrderState
	public String updateOrderState(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取请求参数
		String oid = request.getParameter("oid");
		// 调用业务层进行处理
		orderService.updOrderState(oid, 3);
		// 重定向到/adminOrder?method=findOrdersWithPage&state=3&page=1
		response.sendRedirect(request.getContextPath() + "/adminOrder?method=findOrdersWithPage&state=3&page=1");
		return null;
	}


}
