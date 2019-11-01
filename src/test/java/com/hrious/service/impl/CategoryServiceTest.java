package com.hrious.service.impl;

import java.util.List;

import org.junit.Test;

import com.hrious.store.pojo.Category;
import com.hrious.store.service.CategoryService;
import com.hrious.store.service.impl.CategoryServiceImpl;

public class CategoryServiceTest {

	@Test
	public void getAllCategoryTest() {
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> categories = categoryService.getAllCategory();
		System.out.println(categories);
	}
}
