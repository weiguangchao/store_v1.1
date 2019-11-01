package com.hrious.store.dao;

import java.sql.SQLException;
import java.util.List;

import com.hrious.store.pojo.Category;

public interface CategoryDao {

	/**
	 * 获取全部分类信息
	 * @return
	 */
	List<Category> selAllCategory() throws SQLException;

	/**
	 * 通过cid查询分类名称
	 * @param cid
	 * @return
	 */
	String selCategoryName(String cid) throws SQLException;

	void addCategory(Category category) throws SQLException;

	Category findCategoryByCid(String cid) throws SQLException;

	void updCategory(Category category) throws SQLException;

	void delCategory(String cid) throws SQLException;

}
