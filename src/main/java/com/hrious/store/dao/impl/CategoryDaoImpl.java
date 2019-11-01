package com.hrious.store.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.hrious.store.dao.CategoryDao;
import com.hrious.store.pojo.Category;
import com.hrious.store.utils.JDBCUtils;

public class CategoryDaoImpl implements CategoryDao {

	@Override
	public List<Category> selAllCategory() throws SQLException {
		String sql = "select * from category";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		return queryRunner.query(sql, new BeanListHandler<>(Category.class));
	}

	@Override
	public String selCategoryName(String cid) throws SQLException {
		String sql = "select cname from category where cid=?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		return (String) queryRunner.query(sql, new ScalarHandler(), cid);
	}

	@Override
	public void addCategory(Category category) throws SQLException {
		String sql = "insert into category values(?,?)";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		queryRunner.update(sql, category.getCid(), category.getCname());
	}

	@Override
	public Category findCategoryByCid(String cid) throws SQLException {
		String sql = "select * from category where cid=?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		return queryRunner.query(sql, new BeanHandler<>(Category.class), cid);
	}

	@Override
	public void updCategory(Category category) throws SQLException {
		String sql = "update category set cname=? where cid=?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		queryRunner.update(sql, category.getCname(), category.getCid());
	}

	@Override
	public void delCategory(String cid) throws SQLException {
		String sql = "delete from category where cid=?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		queryRunner.update(sql, cid);
	}

}
