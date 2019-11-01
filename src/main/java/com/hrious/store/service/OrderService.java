package com.hrious.store.service;

import com.hrious.store.pojo.Order;
import com.hrious.store.pojo.PageModel;
import com.hrious.store.pojo.User;

public interface OrderService {

	/**
	 * 保存订单
	 * @param cart
	 * @return
	 */
	void saveOrder(Order order);

	/**
	 * 查询指定用户的的订单信息（分页查询）
	 * @param rows
	 * @param page
	 * @param user
	 * @return
	 */
	PageModel showOrdersWithPage(int rows, int page, User user);

	/**
	 * 通过指定的oid查询指定的order对象
	 * @param oid
	 * @return
	 */
	Order getOrder(String oid);

	/**
	 * 支付宝支付
	 * @param name
	 * @param address
	 * @param telephone
	 */
	String alipayOrder(String oid, String name, String address, String telephone);

	/**
	 * 支付成功，修改订单状态
	 * @param out_trade_no
	 */
	void alipaySucccess(String oid);

	PageModel findOrders(String state, int page);

	void updOrderState(String oid, int i);

}
