package com.hrious.utils;

import java.util.List;

import org.junit.Test;

import com.hrious.store.dao.ProductDao;
import com.hrious.store.pojo.Product;
import com.hrious.store.utils.BeanFactory;

public class BeanFactoryTest {

	@Test
	public void getObjectTest() throws Exception {
		ProductDao productDao = (ProductDao) BeanFactory.getObject("productDao");
		List<Product> products = productDao.selAllNewProduct();
		for (Product product : products) {
			System.out.println(product);
		}
	}
}
