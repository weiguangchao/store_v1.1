package com.hrious.store.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.hrious.store.pojo.User;

public class PrivilegeFilter implements Filter {
	
    public PrivilegeFilter() {
    }
    
	public void destroy() {
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		User user = (User) req.getSession().getAttribute("user");
		if (null == user) { // 未登录
			req.setAttribute("msg", "<font color=\"red\" size=\"12px\">请先登录!</font>");
			req.getRequestDispatcher("/jsp/info.jsp").forward(request, response);;
		} else { // 登录
			// 放行
			chain.doFilter(request, response);
		}
	}
	
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
