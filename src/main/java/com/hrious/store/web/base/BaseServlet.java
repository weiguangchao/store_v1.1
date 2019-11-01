package com.hrious.store.web.base;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 判断请求中是否含有method
		String method = request.getParameter("method");
		if (null == method) {
			method = "execute"; // 默认method
		}
		
		String path = null;
		Class<? extends BaseServlet> clazz = this.getClass();
		try {
			Method m = clazz.getDeclaredMethod(method, HttpServletRequest.class,
					HttpServletResponse.class);
			path = (String) m.invoke(this, request, response); // 调用方法
			if (null != path && !"".equals(path.trim())) {
				request.getRequestDispatcher(path).forward(request, response); // 转发
			}
			return; // 返回
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * 默认方法
	 * @param request
	 * @param response
	 * @return
	 */
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}
}
