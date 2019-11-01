package com.hrious.store.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.hrious.store.dao.ProductDao;
import com.hrious.store.pojo.PageModel;
import com.hrious.store.pojo.Product;
import com.hrious.store.utils.JDBCUtils;

public class ProductDaoImpl implements ProductDao {

	@Override
	public Product selProductByPid(String pid) throws SQLException {
		String sql = "select * from product where pid=?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource()); 
		return queryRunner.query(sql, new BeanHandler<>(Product.class), pid);
	}

	@Override
	public List<Product> selAllHotProduct() throws SQLException {
		String sql = "select * from product where pflag=0 and is_hot=1 order by pdate desc limit 0,9";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource()); 
		return queryRunner.query(sql, new BeanListHandler<>(Product.class));
	}

	@Override
	public List<Product> selAllNewProduct() throws SQLException {
		String sql = "select * from product where pflag=0 order by pdate desc limit 0,9";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource()); 
		return queryRunner.query(sql, new BeanListHandler<>(Product.class));
	}

	@Override
	public int selProductCountByCid(String cid) throws SQLException {
		String sql = "select count(*) from product where cid=?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource()); 
		return ((Long) queryRunner.query(sql, new ScalarHandler(), cid)).intValue();
	}

	@Override
	public List<Product> selProductsByCidPage(String cid, int rows, int page) throws SQLException {
		String sql = "select * from product where cid=? order by pdate desc limit ?,?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource()); 
		return queryRunner.query(sql, new BeanListHandler<>(Product.class), cid, rows * (page - 1), rows);
	}

	@Override
	public int selProductCount() throws SQLException {
		String sql = "select count(*) from product";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource()); 
		return ((Long) queryRunner.query(sql, new ScalarHandler())).intValue();
	}

	@Override
	public List<Product> selProductsByPage(int rows, int page) throws SQLException {
		String sql = "select * from product order by pdate desc limit ?,?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource()); 
		return queryRunner.query(sql, new BeanListHandler<>(Product.class), rows * (page - 1), rows);
	}

	@Override
	public void insertProduct(Product product) throws SQLException {
		String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		Object[] param = {product.getPid(), product.getPname(), product.getMarket_price(),
				product.getShop_price(), product.getPimage(), product.getPdata(),
				product.getIs_hot(), product.getPdesc(), product.getPflag(), product.getCid()};
		queryRunner.update(sql, param);
	}

	@Override
	public int selProductCountByPflag(String pflag) throws SQLException {
		String sql = "select count(*) from product where pflag=?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource()); 
		return ((Long) queryRunner.query(sql, new ScalarHandler(), pflag)).intValue();
	}

	@Override
	public List<Product> findAllProductsByPflagWithPage(int rows, int page, String pflag) throws SQLException {
		String sql = "select * from product where pflag=? order by pdate desc limit ?,?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource()); 
		return queryRunner.query(sql, new BeanListHandler<>(Product.class),pflag, rows * (page - 1), rows);
	}

}
