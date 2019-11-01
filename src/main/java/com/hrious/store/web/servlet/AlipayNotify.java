package com.hrious.store.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alipay.api.AlipayApiException;
import com.hrious.store.service.OrderService;
import com.hrious.store.service.impl.OrderServiceImpl;
import com.hrious.store.utils.AlipayUtils;

public class AlipayNotify extends HttpServlet {
	
	private OrderService orderService = new OrderServiceImpl();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		// 获取支付宝GET过来反馈信息
		Map<String, String> params = AlipayUtils.filterParams(request.getParameterMap());
		try {
			boolean signVerified = AlipayUtils.check(params);
			if (signVerified) {// 验证成功
				// 商户订单号
				String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

				// 支付宝交易号
				String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

				// 交易状态
				String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

				if (trade_status.equals("TRADE_FINISHED")) { // 这笔交易已经完全完成了，支付宝不会再发送任何关于该交易的请求给商家，交易已经关闭了
					// 判断该笔订单是否在商户网站中已经做过处理
					// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					// 如果有做过处理，不执行商户的业务程序

					orderService.alipaySucccess(out_trade_no);
					
					// 注意：
					// 退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
				} else if (trade_status.equals("TRADE_SUCCESS")) { // 代表支付完成，在这之后还有一些后续的操作，例如退款。支付宝还会发送TRADE_FINISHED给商家
					// 判断该笔订单是否在商户网站中已经做过处理
					// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					// 如果有做过处理，不执行商户的业务程序
					
					orderService.alipaySucccess(out_trade_no);

					// 注意：
					// 付款完成后，支付宝系统发送该交易状态通知
				}

				// 告知支付宝交易成功
				out.println("success");

			} else {// 验证失败
				// 告知支付宝验证失败
				out.println("fail");

				// 调试用，写文本函数记录程序运行情况是否正常
				// String sWord = AlipaySignature.getSignCheckContentV1(params);
				// AlipayConfig.logResult(sWord);
			}
			out.flush();
			out.close();
			// ——请在这里编写您的程序（以上代码仅作参考）——
		} catch (AlipayApiException e) {
			e.printStackTrace();
			System.out.println("校验支付宝参数异常");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
