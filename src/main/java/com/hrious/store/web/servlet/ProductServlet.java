package com.hrious.store.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hrious.store.pojo.Product;
import com.hrious.store.pojo.PageModel;
import com.hrious.store.service.ProductService;
import com.hrious.store.service.impl.ProductServiceImpl;
import com.hrious.store.web.base.BaseServlet;

public class ProductServlet extends BaseServlet {
	
	private ProductService productService = new ProductServiceImpl();
	
	public String getProductByPid(HttpServletRequest request, HttpServletResponse reponse) {
		// 1、获取pid
		String pid = request.getParameter("pid");
		// 2、调用业务层通过pid获取指定产品
		Product p = productService.getProductByPid(pid);
		// 3、把获取到的结果放入到request中
		request.setAttribute("product", p);
		// 4、请求转发到/jsp/product_info.jsp
		return "/jsp/product_info.jsp";
	}
	
	public String getProductsByCidWithPage(HttpServletRequest request, HttpServletResponse reponse) {
		// 1、获取cid、pag
		String cid = request.getParameter("cid");
		int page = Integer.parseInt(request.getParameter("page"));
		// 2、调用业务层获取指定cid的商品分页信息
		PageModel pageModel = productService.getProductsByCidWithPage(cid, page);
		// 3、把productsPage放入到作用域中
		request.setAttribute("page", pageModel);
		// 4、请求转发到/jsp/product_list.jsp
		return "/jsp/product_list.jsp";
	}
}
