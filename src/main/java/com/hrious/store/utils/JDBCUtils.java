package com.hrious.store.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * JDBC工具类
 * @author Administrator
 *
 */
public class JDBCUtils {
	
	/**
	 * 数据库连接池
	 */
	private static DataSource ds = new ComboPooledDataSource();
	
	/**
	 * 线程私有的变量区域
	 */
	private static ThreadLocal<Connection> tl=new ThreadLocal<>();
	
	/**
	 * 工具类，构造方式私有
	 */
	private JDBCUtils() {}
	
	/**
	 * 从线程中获取Connection对象
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		// 从线程中获取Connection对象
		Connection conn = tl.get();
		if (null == conn) {
			conn = ds.getConnection();
			// 和当前线程绑定
			tl.set(conn);
		}
		return conn;
	}
	
	/**
	 * 获取数据库连接池
	 * @return
	 */
	public static DataSource getDataSource() {
		return ds;
	}
	
	/**
	 * 开启事务
	 * @throws SQLException 
	 */
	public static void startTranscation() throws SQLException {
		getConnection().setAutoCommit(false);
	}
	
	/**
	 * 提交事务并释放连接
	 * @throws SQLException 
	 */
	public static void commitAndClose() throws SQLException {
		Connection conn = getConnection();
		// 提交事务
		conn.commit();
		// 关闭资源
		close(conn);
	}
	
	/**
	 * 回滚
	 * @param conn
	 * @throws SQLException 
	 */
	public static void rollbackAndClose() throws SQLException {
		Connection conn = getConnection();
		// 回滚
		conn.rollback();
		// 关闭资源
		close(conn);
	}
	
	/**
	 * 关闭Statement和ResultSet
	 * @param stmt
	 * @param rs
	 */
	public static void close(Statement stmt, ResultSet rs) {
		close(stmt);
		close(rs);
	}
	
	/**
	 * 关闭Connection、Statement和ResultSet
	 * @param conn
	 * @param stmt
	 * @param rs
	 */
	public static void close(Connection conn, Statement stmt, ResultSet rs) {
		close(conn);
		close(stmt);
		close(rs);
	}
	
	/**
	 * 关闭Connection
	 * @param conn
	 */
	public static void close(Connection conn) {
		if (null != conn) {
			try {
				conn.close();
				// 和当前线程解绑
				tl.remove();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn = null;
		}
	}
	
	/**
	 * 关闭Statement
	 * @param stmt
	 */
	public static void close(Statement stmt) {
		if (null != stmt) {
			try {
				// 关闭资源
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			stmt = null;
		}
	}
	
	/**
	 * 关闭ResultSet
	 * @param rs
	 */
	public static void close(ResultSet rs) {
		if (null != rs) {
			try {
				// 关闭资源
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs = null;
		}
	}
}
