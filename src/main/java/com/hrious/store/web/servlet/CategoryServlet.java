package com.hrious.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.hrious.store.pojo.Category;
import com.hrious.store.service.CategoryService;
import com.hrious.store.service.impl.CategoryServiceImpl;
import com.hrious.store.web.base.BaseServlet;

public class CategoryServlet extends BaseServlet {
	
	private CategoryService categoryService = new CategoryServiceImpl();
	
	public String getAllCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Category> categoryList = categoryService.getAllCategory();
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(JSON.toJSONString(categoryList));
		return null;
	}
}
