package com.hrious.store.service;

import java.util.List;

import com.hrious.store.pojo.Category;

public interface CategoryService {

	/**
	 * 获取全部分类信息
	 * @return
	 */
	List<Category> getAllCategory();

	/**
	 * 添加分类信息
	 * @param category
	 */
	void addCategory(Category category);

	/**
	 * 根据cid查询分类
	 * @param cid
	 * @return
	 */
	Category findCategoryByCid(String cid);

	/**
	 * 修改分类信息
	 * @param cid
	 */
	void updCategory(Category category);

	/**
	 * 删除分类
	 * @param cid
	 */
	void delCategory(String cid);

}
