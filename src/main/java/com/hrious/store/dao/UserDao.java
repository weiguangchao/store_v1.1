package com.hrious.store.dao;

import java.sql.SQLException;

import com.hrious.store.pojo.User;

public interface UserDao {
	
	/**
	 * 插入一条用户数据
	 * @param u
	 * @return
	 */
	void insUser(User u) throws SQLException;

	/**
	 * 更新用户状态码
	 * @return
	 */
	User selUserByCode(String code) throws SQLException;
	
	/**
	 * 跟新用户信息
	 * @param u
	 * @return
	 * @throws SQLException 
	 */
	int updUser(User u) throws SQLException;

	/**
	 * 根据用户名密码选择用户
	 * @param u
	 * @return
	 */
	User selUserByNamePwd(User u) throws SQLException;

	/**
	 * 根据用户名选择用户
	 * @param username
	 * @return
	 */
	User selUserByUsername(String username) throws SQLException;
}
