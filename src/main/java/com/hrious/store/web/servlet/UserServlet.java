package com.hrious.store.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hrious.store.pojo.User;
import com.hrious.store.service.UserService;
import com.hrious.store.service.impl.UserServiceImpl;
import com.hrious.store.utils.CookieUtils;
import com.hrious.store.utils.MailUtils;
import com.hrious.store.utils.MyBeanUtils;
import com.hrious.store.utils.UUIDUtils;
import com.hrious.store.web.base.BaseServlet;

public class UserServlet extends BaseServlet {

	private UserService userService = new UserServiceImpl();

	public String registerUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return "/jsp/register.jsp";
	}

	public String register(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 1_接受用户数据
		User u = MyBeanUtils.populate(User.class, request.getParameterMap());

		// 2_设置部分参数
		u.setUid(UUIDUtils.getCode());
		u.setState(0);
		u.setCode(UUIDUtils.getCode());

		// 3_调用业务层的注册功能
		int i = userService.userRegister(u);
		if (i > 0) {
			// 注册成功，跳转到提示页面
			request.setAttribute("msg", "用户注册成功，请激活");

			// 发送邮件
			MailUtils.sendMail(u.getEmail(), u.getCode());
		} else {
			// 注册失败
			request.setAttribute("msg", "用户注册失败");
		}
		// 4_提示客户端是否注册成功
		return "/jsp/info.jsp";
	}

	public String active(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1_获取激活码
		String code = request.getParameter("code");
		// 2_调用service层进行处理
		int i = userService.userActive(code);
		// 3_展示相关界面
		if (i > 0) { // 激活成功
			request.getSession().setAttribute("msg", "用户激活成功");
			// 重定向到登录界面
			response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
			return null;
		} else { // 激活失败
			return "/jsp/register.jsp";
		}
	}

	public String loginUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 判断用户是否重复登陆
		if (null != request.getSession().getAttribute("user")) {
			response.sendRedirect(request.getContextPath() + "/index.jsp");
			return null;
		}
		// 获取含有用户名的cookie
		Cookie remeCookie = CookieUtils.getCookieByName("remember", request.getCookies());
		if (null != remeCookie) { // 含有cookie
			// 把用户名存入到session中
			request.getSession().setAttribute("username", remeCookie.getValue());
		}
		return "/jsp/login.jsp";
	}

	public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 获取用户请求数据
		String autoLogin = request.getParameter("autoLogin"); // 自动登录
		String remember = request.getParameter("remember"); // 记住用户名
		User u = new User();
		MyBeanUtils.populate(u, request.getParameterMap());
		
		// 调用service层进行业务处理
		User user = userService.userLogin(u);
		
		// 账号密码错误
		if (null == user) {
			request.setAttribute("msg", "用户名密码错误");
			return "/jsp/login.jsp";
		}
		// 用户未激活
		if (user.getState() == 0) {
			request.setAttribute("msg", "用户未激活");
			return "/jsp/login.jsp";
		}

		// 勾选自动登录, 告知浏览器存储cookie
		if ("1".equals(autoLogin)) {
			Cookie autoCookie = new Cookie("autoLogin", user.getUsername() + "@" + user.getPassword());
			autoCookie.setMaxAge(24 * 60 * 60);
			response.addCookie(autoCookie);
		}
		
		if ("1".equals(remember)) { // 勾选记住用户名, 告知浏览器存储cookie（在用户成功登陆之后）
			Cookie remeCookie = new Cookie("remember", u.getUsername());
			remeCookie.setMaxAge(24 * 60 * 60);
			response.addCookie(remeCookie);
		} else { // 没有勾选记住用户名
			Cookie remeCookie = CookieUtils.getCookieByName("remember", request.getCookies());
			if (null != remeCookie) {
				// 使cookie失效
				remeCookie.setMaxAge(0);
				response.addCookie(remeCookie);
			}
		}
		// 登录成功重定向到/store/index.jsp
		request.getSession().setAttribute("user", user);
		response.sendRedirect(request.getContextPath() + "/index.jsp");
		return null;
	}

	public String logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 1_重新设置cookie
		Cookie autoCookie = CookieUtils.getCookieByName("autoLogin", request.getCookies());
		if (null != autoCookie) {
			// 如果cookie使用时间为0的话, 客户端会自动销毁
			autoCookie.setMaxAge(0);
			response.addCookie(autoCookie);
		}
		// 2_销毁session
		request.getSession().invalidate();
		// 2_重定向到主页面
		response.sendRedirect("/store/index.jsp");
		return null;
	}
	
	public String checkUsername(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 设置响应类型及编码
		response.setContentType("application/json;charset=utf-8");
		// 获取请求参数
		String username = request.getParameter("username");
		// 调用service层进行处理
		int i = userService.checkUsername(username);
		response.getWriter().write("{\"checkUsername\": \"" + i + "\"}");
		return null;
	}
}
	