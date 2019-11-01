package com.hrious.store.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.alipay.api.AlipayApiException;
import com.hrious.store.dao.OrderDao;
import com.hrious.store.pojo.Order;
import com.hrious.store.pojo.OrderItem;
import com.hrious.store.pojo.PageModel;
import com.hrious.store.pojo.Product;
import com.hrious.store.pojo.User;
import com.hrious.store.service.OrderService;
import com.hrious.store.utils.AlipayUtils;
import com.hrious.store.utils.BeanFactory;
import com.hrious.store.utils.JDBCUtils;

public class OrderServiceImpl implements OrderService {
	
	private OrderDao orderDao = (OrderDao) BeanFactory.getObject("orderDao");

	@Override
	public void saveOrder(Order order) {
		try {
			// 开启事务
			JDBCUtils.startTranscation();
			// 在订单表中插入数据
			orderDao.insOrder(order);
			// 在订单项表中插入数据
			for (OrderItem orderItem : order.getList()) {
				orderDao.insOrderItem(orderItem);
			}
			JDBCUtils.commitAndClose();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				// 回滚
				JDBCUtils.rollbackAndClose();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public PageModel showOrdersWithPage(int rows, int page, User user) {
		try {
			// 获取订单总数
			int quantity = orderDao.selOrderCountByUid(user);
			// 获取分页的订单信息
			List<Order> orders = orderDao.selOrdersByUidWithPage(rows, page, user);
			// 获取指定订单下所有的订单项和商品
			List<OrderItem> orderItemList = null;
			for (Order order : orders) {
				orderItemList = orderDao.selOrderItemProductByOid(order.getOid());
				// 添加到订单中
				order.setList(orderItemList);
			}
			// 创建PageModel对象，并返回
			/*PageModel pageModel = new PageModel();
			pageModel.setList(orders);
			pageModel.setRows(rows);
			pageModel.setQuantity(quantity);
			pageModel.setCurrentPage(page);
			pageModel.setMaxPage(quantity / rows);
			pageModel.setUrl("");*/
			return new PageModel(orders, rows, quantity, page, quantity / rows, "order?method=showOrders");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Order getOrder(String oid) {
		try {
			Order order = orderDao.selOrderByOid(oid);
			List<OrderItem> orderItemList = orderDao.selOrderItemProductByOid(oid);
			order.setList(orderItemList);
			return order;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String alipayOrder(String oid, String name, String address, String telephone) {
		// 更新订单表中的数据
		try {
			orderDao.updOrderConsignee(oid, name, address, telephone);
			Order order = orderDao.selOrderByOid(oid);
			return AlipayUtils.pay(oid, String.valueOf(order.getTotal()), oid, "");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("订单插入失败");
		} catch (AlipayApiException e) {
			e.printStackTrace();
			System.out.println("支付宝操作异常");
		}
		return "<h3>操作异常</h3>";
	}

	@Override
	public void alipaySucccess(String oid) {
		try {
			orderDao.updOrderState(oid, 2);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public PageModel findOrders(String state, int page) {
		// 创建对象
		int total = 0;
		List<Order> orders = null;
		try {
			// 判断state的状态
			if (null == state || "".equals(state)) { // 查询所有订单
				total = orderDao.getOrdersCount();
				orders = orderDao.findOrdersWithPage(6, page);
				// 关联集合
				// 关联url
				return new PageModel(orders, 6, total, page, total / 6, "adminOrder?method=findOrdersWithPage");
			} else { // 查询带有特定状态的订单
				total = orderDao.getOrdersCountByState(state);
				orders = orderDao.findOrdersByStateWithPage(8, page, state);
				// 关联集合
				// 关联url
				return new PageModel(orders, 6, total, page, total / 6, "adminOrder?method=findOrdersWithPage&state=" + state);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void updOrderState(String oid, int i) {
		try {
			orderDao.updOrderState(oid, i);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
