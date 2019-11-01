package com.hrious.store.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.hrious.store.dao.UserDao;
import com.hrious.store.pojo.User;
import com.hrious.store.utils.JDBCUtils;

public class UserDaoImpl implements UserDao {

	@Override
	public void insUser(User u) throws SQLException {
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)"; // SQL语句
		QueryRunner queryRuner = new QueryRunner(JDBCUtils.getDataSource());
		Object[] param = new Object[] { u.getUid(), u.getUsername(), u.getPassword(), u.getName(), u.getEmail(),
				u.getTelephone(), u.getBirthday(), u.getSex(), u.getState(), u.getCode() }; // 参数
		queryRuner.update(sql, param); // 执行插入操作
	}

	@Override
	public User selUserByCode(String code) throws SQLException {
		String sql = "select * from user where code=?"; // SQL语句
		QueryRunner queryRuner = new QueryRunner(JDBCUtils.getDataSource());
		return queryRuner.query(sql, new BeanHandler<>(User.class), code);
	}

	@Override
	public int updUser(User u) throws SQLException {
		String sql = "update user set username=?,password=?,name=?,email=?,telephone=?,birthday=?,sex=?,state=?,code=? where uid=?"; // SQL语句
		Object[] param = new Object[] { u.getUsername(), u.getPassword(), u.getName(), u.getEmail(), u.getTelephone(),
				u.getBirthday(), u.getSex(), u.getState(), u.getCode(), u.getUid() }; // 参数
		QueryRunner queryRuner = new QueryRunner(JDBCUtils.getDataSource());
		return queryRuner.update(sql, param);
	}

	@Override
	public User selUserByNamePwd(User u) throws SQLException {
		String sql = "select * from user where username=? and password=?";
		QueryRunner queryRuner = new QueryRunner(JDBCUtils.getDataSource());
		return queryRuner.query(sql, new BeanHandler<>(User.class), u.getUsername(), u.getPassword());
	}

	@Override
	public User selUserByUsername(String username) throws SQLException {
		String sql = "select * from user where username=?";
		QueryRunner queryRuner = new QueryRunner(JDBCUtils.getDataSource());
		return queryRuner.query(sql, new BeanHandler<>(User.class), username);
	}

}
