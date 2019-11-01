package com.hrious.store.service.impl;

import java.sql.SQLException;

import com.hrious.store.dao.UserDao;
import com.hrious.store.pojo.User;
import com.hrious.store.service.UserService;
import com.hrious.store.utils.BeanFactory;

public class UserServiceImpl implements UserService {
	
	private UserDao userDao = (UserDao) BeanFactory.getObject("userDao");

	@Override
	public int userRegister(User u) {
		try {
			userDao.insUser(u);
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public int userActive(String code) {
		try {
			User u = userDao.selUserByCode(code);
			if (null != u) { // 激活成功
				u.setState(1);
				u.setCode(null);
				userDao.updUser(u);
				return 1;
			}
			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			// 激活失败
			return -1;
		}
	}

	@Override
	public User userLogin(User u) {
		try {
			return userDao.selUserByNamePwd(u);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int checkUsername(String username) {
		try {
			User u = userDao.selUserByUsername(username);
			if (null == u) { // 用户不存在
				return 0;
			} else {
				if (u.getState() == 0) { // 用户未激活
					return -1;
				} else { // 正常
					return 1;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

}
