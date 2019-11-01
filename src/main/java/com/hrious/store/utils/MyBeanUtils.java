package com.hrious.store.utils;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

public class MyBeanUtils {

	private MyBeanUtils() {}
	
	public static void populate(Object bean, Map<String, String[]> map) throws Exception {
		// 1、创建时间类型转换器
		DateConverter convert = new DateConverter();
		// 2、设置转换格式
		convert.setPattern("yyyy-MM-dd");
		// 3、注册转换器
		ConvertUtils.register(convert, java.util.Date.class);
		BeanUtils.populate(bean, map);
	}
	
	public static<T> T populate(Class<T> clazz, Map<String, String[]> map) throws Exception {
		T bean = clazz.newInstance();
		MyBeanUtils.populate(bean, map);
		return bean;
	}
	
}
