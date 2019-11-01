package com.hrious.store.utils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.hrious.store.config.AlipayConfig;

public class AlipayUtils {
	// 阿里支付客户端
	private static AlipayClient alipayClient = new DefaultAlipayClient(
			AlipayConfig.gatewayUrl, 
			AlipayConfig.app_id, 
			AlipayConfig.merchant_private_key, 
			"json", 
			AlipayConfig.charset, 
			AlipayConfig.alipay_public_key, 
			AlipayConfig.sign_type);
	
	/**
	 * 支付订单
	 * @param outTradeNo
	 * @param totalAmount
	 * @param subject
	 * @param body
	 * @return
	 * @throws AlipayApiException
	 */
	public static String pay(String outTradeNo, String totalAmount, String subject, String body) throws AlipayApiException {
		//设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(AlipayConfig.return_url);
		alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
		
		AlipayTradePagePayModel model = new AlipayTradePagePayModel(); 
		model.setOutTradeNo(outTradeNo);
		model.setTotalAmount(totalAmount);
		model.setSubject(subject);
		model.setBody(body);
		model.setProductCode("FAST_INSTANT_TRADE_PAY");
		
		alipayRequest.setBizModel(model);
		
		return alipayClient.pageExecute(alipayRequest).getBody();
	}
	
	/**
	 * 校验订单
	 * @param params
	 * @return
	 * @throws AlipayApiException 
	 */
	public static boolean check(Map<String, String> params) throws AlipayApiException {
		return AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset,
				AlipayConfig.sign_type); // 调用SDK验证签名
	}
	
	/**
	 * 过滤请求参数
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static Map<String, String> filterParams(Map<String, String[]> requestParams) throws UnsupportedEncodingException {
		Map<String, String> params = new HashMap<String, String>();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		return params;
	}
}
