package com.hrious.store.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.hrious.store.dao.CategoryDao;
import com.hrious.store.pojo.Category;
import com.hrious.store.service.CategoryService;
import com.hrious.store.utils.BeanFactory;
import com.hrious.store.utils.JedisUtils;

public class CategoryServiceImpl implements CategoryService {
	
	private CategoryDao categoryDao = (CategoryDao) BeanFactory.getObject("categoryDao");

	@Override
	public List<Category> getAllCategory() {
		// 从redis中先查找相关信息
		String jsonVal = JedisUtils.get("categories");
		List<Category> categoryList = null;
		if (null == jsonVal || "".equals(jsonVal)) { // 如果没有获取到
			try {
				categoryList = categoryDao.selAllCategory();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			// 把获取到的信息放入redis中
			if (null != categoryList) {
				jsonVal = JSON.toJSONString(categoryList);
				JedisUtils.set("categories", jsonVal);
			}
		} else { // 在redis中
			categoryList = JSON.parseArray(jsonVal, Category.class);
		}
		// 查到了直接返回就可以
		return categoryList;
	}

	@Override
	public void addCategory(Category category) {
		// 调用dao层添加分类信息
		try {
			categoryDao.addCategory(category);
			// 重置redis
			JedisUtils.del("categories");
		} catch (SQLException e) {
			// 添加失败
			e.printStackTrace();
		}
	}

	@Override
	public Category findCategoryByCid(String cid) {
		try {
			return categoryDao.findCategoryByCid(cid);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void updCategory(Category category) {
		try {
			categoryDao.updCategory(category);
			// 重置redis
			JedisUtils.del("categories");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delCategory(String cid) {
		try {
			categoryDao.delCategory(cid);
			// 重置redis
			JedisUtils.del("categories");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
