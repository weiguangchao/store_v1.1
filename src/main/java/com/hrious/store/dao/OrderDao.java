package com.hrious.store.dao;

import java.sql.SQLException;
import java.util.List;

import com.hrious.store.pojo.Order;
import com.hrious.store.pojo.OrderItem;
import com.hrious.store.pojo.Product;
import com.hrious.store.pojo.User;

public interface OrderDao {

	void insOrder(Order order) throws SQLException;

	void insOrderItem(OrderItem orderItem) throws SQLException;

	List<Order> selOrdersByUidWithPage(int rows, int page, User user) throws SQLException;

	int selOrderCountByUid(User user) throws SQLException;

	List<OrderItem> selOrderItemProductByOid(String oid) throws SQLException;

	Order selOrderByOid(String oid) throws SQLException;

	void updOrderConsignee(String oid, String name, String address, String telephone) throws SQLException;

	void updOrderState(String oid, int i) throws SQLException;

	int getOrdersCount() throws SQLException;

	int getOrdersCountByState(String state) throws SQLException;

	List<Order> findOrdersWithPage(int rows, int page) throws SQLException;

	List<Order> findOrdersByStateWithPage(int rows, int page, String state) throws SQLException;
	
}
