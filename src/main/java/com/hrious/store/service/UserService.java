package com.hrious.store.service;

import com.hrious.store.pojo.User;

public interface UserService {

	/**
	 * 用户注册
	 * @param u
	 */
	int userRegister(User u);

	/**
	 * 用户激活
	 * @param code
	 * @return
	 */
	int userActive(String code);

	/**
	 * 用户登录
	 * @param u
	 * @return
	 */
	User userLogin(User u);

	/**
	 * 校验用户名是否存在
	 * @param username
	 * @return
	 */
	int checkUsername(String username);
	
}
