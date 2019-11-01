package com.hrious.store.utils;

import javax.servlet.http.Cookie;

public class CookieUtils {

	/**
	 * 工具类，构造方式私有
	 */
	private CookieUtils() {}

	public static Cookie getCookieByName(String name, Cookie[] cookies) {
		if (null != cookies) {
			for (Cookie c : cookies) {
				// 根据name找到
				if (name.equals(c.getName())) {
					return c;
				}
			}
		}
		// 没有找到返回null
		return null;
	}
}
