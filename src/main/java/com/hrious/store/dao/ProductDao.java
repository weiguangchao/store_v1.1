package com.hrious.store.dao;

import java.sql.SQLException;
import java.util.List;

import com.hrious.store.pojo.Product;

public interface ProductDao {

	/**
	 * 查询所有热门商品
	 * @return
	 */
	List<Product> selAllHotProduct() throws SQLException;

	/**
	 * 查询所有最新商品
	 * @return
	 */
	List<Product> selAllNewProduct() throws SQLException;

	/**
	 * 查询指定pid的产品
	 * @return
	 */
	Product selProductByPid(String pid) throws SQLException;

	/**
	 * 返回指定分类的商品数量
	 * @return
	 */
	int selProductCountByCid(String cid) throws SQLException;

	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	List<Product> selProductsByCidPage(String cid, int rows, int page) throws SQLException;

	/**
	 * 查询所有商品的数量
	 * @return
	 * @throws SQLException
	 */
	int selProductCount() throws SQLException;

	/**
	 * 查询所有商品
	 * @param i
	 * @param page
	 * @return
	 * @throws SQLException
	 */
	List<Product> selProductsByPage(int rows, int page) throws SQLException;

	/**
	 * 保存商品
	 * @param product
	 */
	void insertProduct(Product product) throws SQLException;

	int selProductCountByPflag(String pflag) throws SQLException;

	List<Product> findAllProductsByPflagWithPage(int rows, int page, String pflag) throws SQLException;
}
