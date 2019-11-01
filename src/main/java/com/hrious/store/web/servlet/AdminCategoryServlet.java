package com.hrious.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.hrious.store.pojo.Category;
import com.hrious.store.service.CategoryService;
import com.hrious.store.service.impl.CategoryServiceImpl;
import com.hrious.store.utils.UUIDUtils;
import com.hrious.store.web.base.BaseServlet;

public class AdminCategoryServlet extends BaseServlet {
	
	private CategoryService categoryService = new CategoryServiceImpl();

	public String findAllCats(HttpServletRequest request, HttpServletResponse response) {
		List<Category> allCategory = categoryService.getAllCategory();
		request.setAttribute("allCategory", allCategory);
		return "/admin/category/list.jsp";
	}
	
	// addCategoryUI
	public String addCategoryUI(HttpServletRequest request, HttpServletResponse response) {
		return "/admin/category/add.jsp";
	}
	
	// addCategory
	public String addCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取请求参数
		String cname = request.getParameter("cname");
		Category category = new Category(UUIDUtils.getCode(), cname);
		// 调用业务层进行处理
		categoryService.addCategory(category);
		response.sendRedirect(request.getContextPath() + "/adminCategory?method=findAllCats");
		return null;
	}
	
	// editUI
	public String editUI(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取请求参数
		String cid = request.getParameter("cid");
		// 调用业务层查找分类信息
		Category category = categoryService.findCategoryByCid(cid);
		// 显示编辑菜单
		request.setAttribute("category", category);
		// 转发到/admin/category/edit.jsp
		return "/admin/category/edit.jsp";
	}
	
	// edit
	public String edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 获取请求参数
		Category category = new Category();
		BeanUtils.populate(category, request.getParameterMap());
		// 调用业务层完成分类信息的修改
		categoryService.updCategory(category);
		// 显示编辑菜单
		response.sendRedirect(request.getContextPath() + "/adminCategory?method=findAllCats");
		return null;
	}
	
	// delete
	public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 获取请求参数
		String cid = request.getParameter("cid");
		// 调用业务层进行处理
		categoryService.delCategory(cid);
		// 进行页面跳转
		response.sendRedirect(request.getContextPath() + "/adminCategory?method=findAllCats");
		return null;
	}


}
