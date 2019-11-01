package com.hrious.store.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.hrious.store.pojo.User;
import com.hrious.store.service.UserService;
import com.hrious.store.service.impl.UserServiceImpl;
import com.hrious.store.utils.CookieUtils;

public class LoginFilter implements Filter {

	UserService userService = new UserServiceImpl();

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 1_获取cookie
		Cookie autoCookie = CookieUtils.getCookieByName("autoLogin", ((HttpServletRequest) request).getCookies());
		if (null != autoCookie) { // 如果有cookie
			// 2_把cookie中的用户名和密码与数据库中的用户名和密码进行比较
			String[] strArr = autoCookie.getValue().split("@");
			User u = new User();
			// 用户名strArr[0]
			u.setUsername(strArr[0]);
			// 密码strArr[1]
			u.setPassword(strArr[1]);
			
			// 调用业务层对用户进行查找
			User user = userService.userLogin(u);
			if (null != user && user.getState() == 1) { // 找到用户信息，并且用户已经激活
				((HttpServletRequest) request).getSession().setAttribute("user", user);
			}
		}
		chain.doFilter(request, response);
		return;
	}

	@Override
	public void init(FilterConfig conifg) throws ServletException {
	}

}
