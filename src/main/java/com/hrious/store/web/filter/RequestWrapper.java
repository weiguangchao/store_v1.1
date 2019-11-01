package com.hrious.store.web.filter;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class RequestWrapper extends HttpServletRequestWrapper {
	
	private HttpServletRequest request;
	private String charset;
	
	public RequestWrapper(HttpServletRequest request) {
		super(request);
		this.request = request;
	}
	
	public RequestWrapper(HttpServletRequest request, String charset) {
		super(request);
		this.request = request;
		this.charset = charset;
	}

	@Override
	public String getParameter(String name) {
		if (null == name || name.trim().length() == 0) {
			return null;
		}
		String[] values = request.getParameterValues(name);
		if (null == values || values.length == 0) {
			return null;
		}
		return values[0];
	}

	@Override
	public String[] getParameterValues(String name) {
		if (null == name || name.trim().length() == 0) {
			return null;
		}
		Map<String, String[]> map = request.getParameterMap();
		if (null == map || map.size() == 0) {
			return null;
		}
		return map.get(name);
	}
	
	@Override
	public Map<String, String[]> getParameterMap() {
		String method = request.getMethod();
		if ("post".equalsIgnoreCase(method)) { // post
			try {
				request.setCharacterEncoding("utf-8");
				return request.getParameterMap();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else { // get
			Map<String, String[]> map = request.getParameterMap();
			for (Map.Entry<String, String[]> m : map.entrySet()) {
				String[] values = m.getValue();
				// 循环重新编码
				for (int i = 0; i < values.length; i++) {
					values[i] = convert(values[i]);
				}
			}
			return map;
		}
		return super.getParameterMap();
	}
	
	/**
	 * 字符转换
	 * @param src
	 * @return
	 */
	private String convert(String src) {
		try {
			return new String(src.trim().getBytes("iso-8859-1"), charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return src;
	}

}
