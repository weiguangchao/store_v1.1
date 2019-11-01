package com.hrious.store.service;

import java.util.List;

import com.hrious.store.pojo.Product;
import com.hrious.store.pojo.PageModel;

public interface ProductService {

	/**
	 * 显示所有热门商品
	 * @return
	 */
	List<Product> getAllHotProduct();

	/**
	 * 显示所有最新商品
	 * @return
	 */
	List<Product> getAllNewProduct();

	/**
	 * 通过指定的pid获取产品
	 * @param pid
	 * @return
	 */
	Product getProductByPid(String pid);

	/**
	 * 根据cid、rows、page查询商品分页
	 * @param cid 商品分类
	 * @param page 页数
	 * @return
	 */
	PageModel getProductsByCidWithPage(String cid, int page);

	/**
	 * 查询所有的商品
	 * @param page
	 * @return
	 */
	PageModel findAllProductsWithPage(int page);

	/**
	 * 保存产品
	 * @param product
	 */
	void saveProduct(Product product);

	PageModel findAllProductsByPflagWithPage(String pflag, int page);
}
