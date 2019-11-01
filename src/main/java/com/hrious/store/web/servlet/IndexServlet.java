package com.hrious.store.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hrious.store.pojo.Product;
import com.hrious.store.service.ProductService;
import com.hrious.store.service.impl.ProductServiceImpl;
import com.hrious.store.web.base.BaseServlet;

public class IndexServlet extends BaseServlet {

	private ProductService productService = new ProductServiceImpl();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		// 1、获取最热商品
		List<Product> hots = productService.getAllHotProduct();
		// 2、获取最新商品
		List<Product> news = productService.getAllNewProduct();
		// 3、把最热商品和最新商品放入到作用域中
		request.setAttribute("hots", hots);
		request.setAttribute("news", news);
		// 4、请求转发到index.jsp
		return "/jsp/index.jsp";
	}
	
}
