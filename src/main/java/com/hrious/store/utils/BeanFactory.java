package com.hrious.store.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class BeanFactory {
	
	private static Map<String, Object> map = new HashMap<String, Object>();
	
	// 初始化
	static {
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(BeanFactory.class.getClassLoader().getResourceAsStream("beans-context.xml"));
			Element rootElement = doc.getRootElement();
			List<Element> elements = rootElement.elements();
			for (Element element : elements) {
				// 获取每个bean标签的id和class
				String id = element.attributeValue("id");
				String className = element.attributeValue("class");
				
				Object obj = Class.forName(className).getConstructor().newInstance();
				map.put(id, obj);
			}
		} catch (Exception e) {
			// 忽略
		}
	}

	public static Object getObject(String name) {
		return map.get(name);
	}
}
