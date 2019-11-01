package com.hrious.store.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.hrious.store.dao.OrderDao;
import com.hrious.store.pojo.Order;
import com.hrious.store.pojo.OrderItem;
import com.hrious.store.pojo.Product;
import com.hrious.store.pojo.User;
import com.hrious.store.utils.JDBCUtils;

public class OrderDaoImpl implements OrderDao {

	@Override
	public void insOrder(Order order) throws SQLException {
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		QueryRunner queryRunner = new QueryRunner();
		Object[] params = {order.getOid(), order.getOrdertime(), order.getTotal(), order.getState(),
				order.getAddress(), order.getName(), order.getTelephone(), order.getUser().getUid()};
		queryRunner.update(JDBCUtils.getConnection(), sql, params);
	}

	@Override
	public void insOrderItem(OrderItem orderItem) throws SQLException {
		String sql = "insert into orderitem values(?,?,?,?,?)";
		QueryRunner queryRunner = new QueryRunner();
		Object[] params = {orderItem.getItemid(), orderItem.getQuantity(), orderItem.getTotal(), orderItem.getProduct().getPid(),
				orderItem.getOid()};
		queryRunner.update(JDBCUtils.getConnection(), sql, params);
	}

	@Override
	public List<Order> selOrdersByUidWithPage(int rows, int page, User user) throws SQLException {
		String sql = "select * from orders where uid=? order by ordertime desc limit ?,?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		List<Map<String, Object>> list = queryRunner.query(sql, new MapListHandler(), user.getUid(), (page - 1) * rows, rows);
		Order order = null;
		List<Order> orderList = new ArrayList<Order>();
		for (Map<String, Object> map : list) {
			order = new Order();
			try {
				BeanUtils.populate(order, map);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			orderList.add(order);
		}
		return orderList;
	}

	@Override
	public int selOrderCountByUid(User user) throws SQLException {
		String sql = "select count(*) from orders where uid=?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		Long value = (Long) queryRunner.query(sql, new ScalarHandler(), user.getUid());
		return value.intValue();
	}

	@Override
	public List<OrderItem> selOrderItemProductByOid(String oid) throws SQLException {
		// 遍历订单，查找所有的OrderItem和Product
		String sql = "select * from orderitem it inner join product p on it.pid=p.pid where it.oid=?";
		OrderItem orderItem = null;
		Product product = null;
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		List<Map<String, Object>> lists = queryRunner.query(sql, new MapListHandler(), oid);
		
		for (Map<String, Object> map : lists) {
			orderItem = new OrderItem();
			product = new Product();
			try {
				BeanUtils.populate(orderItem, map);
				BeanUtils.populate(product, map);

			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			// 设置
			orderItem.setProduct(product);
			orderItemList.add(orderItem);
		}
		return orderItemList;
	}

	@Override
	public Order selOrderByOid(String oid) throws SQLException {
		String sql = "select * from orders where oid=?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		return queryRunner.query(sql, new BeanHandler<>(Order.class), oid);
	}

	@Override
	public void updOrderConsignee(String oid, String name, String address, String telephone) throws SQLException {
		String sql = "update orders set name=?,address=?,telephone=? where oid=?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		queryRunner.update(sql, name, address, telephone, oid);
	}

	@Override
	public void updOrderState(String oid, int i) throws SQLException {
		String sql = "update orders set state=? where oid=?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		queryRunner.update(sql, i, oid);
	}

	@Override
	public int getOrdersCount() throws SQLException {
		String sql = "select count(*) from orders";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		Long value = (Long) queryRunner.query(sql, new ScalarHandler());
		return value.intValue();
	}

	@Override
	public int getOrdersCountByState(String state) throws SQLException {
		String sql = "select count(*) from orders where state=?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		Long value = (Long) queryRunner.query(sql, new ScalarHandler(), state);
		return value.intValue();
	}

	@Override
	public List<Order> findOrdersWithPage(int rows, int page) throws SQLException {
		String sql = "select * from orders order by ordertime desc limit ?,?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource()); 
		return queryRunner.query(sql, new BeanListHandler<>(Order.class), rows * (page - 1), rows);
	}

	@Override
	public List<Order> findOrdersByStateWithPage(int rows, int page, String state) throws SQLException {
		String sql = "select * from orders where state=? order by ordertime desc limit ?,?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource()); 
		return queryRunner.query(sql, new BeanListHandler<>(Order.class), state, rows * (page - 1), rows);
	}

}
