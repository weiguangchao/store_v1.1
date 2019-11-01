package com.hrious.store.utils;

import java.util.UUID;

public class UUIDUtils {
	
	/**
	 * 工具类，构造方式私有
	 */
	private UUIDUtils() {}
	
	/**
	 * 随机生成32位字符串
	 * @return
	 */
	public static String getCode() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
	
	/**
	 * 随机生成64位字符串
	 * @return
	 */
	public static String getCode64() {
		return getCode() + getCode();
	}
}
