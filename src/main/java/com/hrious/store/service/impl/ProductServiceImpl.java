package com.hrious.store.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.hrious.store.dao.ProductDao;
import com.hrious.store.pojo.PageModel;
import com.hrious.store.pojo.Product;
import com.hrious.store.service.ProductService;
import com.hrious.store.utils.BeanFactory;

public class ProductServiceImpl implements ProductService {


	private ProductDao productDao = (ProductDao) BeanFactory.getObject("productDao");
	
	@Override
	public List<Product> getAllHotProduct() {
		try {
			return productDao.selAllHotProduct();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Product getProductByPid(String pid) {
		try {
			return productDao.selProductByPid(pid);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Product> getAllNewProduct() {
		try {
			return productDao.selAllNewProduct();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public PageModel getProductsByCidWithPage(String cid, int page) {
		try {
			// 1.创建对象
			int total = productDao.selProductCountByCid(cid);
			// 2.关联集合
			List<Product> products = productDao.selProductsByCidPage(cid, 12, page);
			// 3.关联url
			return new PageModel(products, 12, total, page, total / 12, "product?method=getProductsByCidWithPage&cid=" + cid);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public PageModel findAllProductsWithPage(int page) {
		// 创建对象
		int total = 0;
		try {
			total = productDao.selProductCount();
			// 关联集合
			List<Product> products = productDao.selProductsByPage(8, page);
			// 关联url
			return new PageModel(products, 8, total, page, total / 8, "adminProduct?method=findAllProductsWithPage");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void saveProduct(Product product) {
		try {
			productDao.insertProduct(product);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public PageModel findAllProductsByPflagWithPage(String pflag, int page) {
		// 创建对象
		int total = 0;
		try {
			total = productDao.selProductCountByPflag(pflag);
			// 关联集合
			List<Product> products = productDao.findAllProductsByPflagWithPage(8, page, pflag);
			// 关联url
			return new PageModel(products, 8, total, page, total / 8, "adminProduct?method=findAllProductsWithPage&pflag=" + pflag);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
