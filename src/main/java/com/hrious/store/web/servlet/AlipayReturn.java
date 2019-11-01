package com.hrious.store.web.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.hrious.store.config.AlipayConfig;
import com.hrious.store.utils.AlipayUtils;

public class AlipayReturn extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取支付宝GET过来反馈信息
		Map<String, String> params = AlipayUtils.filterParams(request.getParameterMap());

		String result = "";
		try {
			boolean signVerified = AlipayUtils.check(params);
			// ——请在这里编写您的程序（以下代码仅作参考）——
			if (signVerified) {
				// 商户订单号
				String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

				// 支付宝交易号
				String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

				// 付款金额
				String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

				result = "<h3>支付成功</h3>" +
						"共消费 <font color=\"blue\">" + total_amount + "<font> 元";
			} else {
				result = "<h3>支付过程异常</h3>";
			}
			// ——请在这里编写您的程序（以上代码仅作参考）——
		} catch (AlipayApiException e) {
			e.printStackTrace();
			result = "<h3>系统繁忙请稍等</h3>";
		}
		
		request.setAttribute("msg", result);
		request.getRequestDispatcher("/jsp/info.jsp").forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
