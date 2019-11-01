package com.hrious.store.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtils {
	
	/**
	 * 连接池
	 */
	private static final JedisPool jedisPool;
	
	/**
	 * 配置信息
	 */
	private static JedisPoolConfig jedisPoolConfig;
	
	static {
		jedisPoolConfig = new JedisPoolConfig();
		// 最大连接数
		jedisPoolConfig.setMaxTotal(150);
		// 最大空闲连接数
		jedisPoolConfig.setMaxIdle(30);
		// 最小空闲数
		jedisPoolConfig.setMinIdle(10);
		// 引入一个redis实例，最大的等待时间
		jedisPoolConfig.setMaxWaitMillis(3 * 1000);
		
		jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379);
	}
	
	/**
	 * 获取连接
	 */
	public static Jedis getJedis() {
		return jedisPool.getResource();
	}
	
	/**
	 * 关闭资源
	 */
	public static void close(Jedis jedis) {
		if (null != jedis) {
			jedis.close();
		}
	}
	
	public static void set(String key, String value) {
		Jedis jedis = getJedis();
		jedis.set(key, value);
		close(jedis);
	}
	
	public static String get(String key) {
		Jedis jedis = getJedis();
		String value = jedis.get(key);
		close(jedis);
		return value;
	}
	
	public static void del(String key) {
		Jedis jedis = getJedis();
		jedis.del(key);
		close(jedis);
	}
	
}
