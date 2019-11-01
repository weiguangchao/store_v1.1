package com.hrious.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.hrious.store.dao.ProductDao;
import com.hrious.store.dao.impl.ProductDaoImpl;
import com.hrious.store.pojo.Product;

public class ProductDaoImplTest {

	@Test
	public void selProductsByPage() throws SQLException {
		ProductDao productDao = new ProductDaoImpl();
		for (int i = 1; i < 7; i++) {
			List<Product> products = productDao.selProductsByPage(8, i);
			for (Product product : products) {
				System.out.println(product);
			}
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
		}
	}

}
