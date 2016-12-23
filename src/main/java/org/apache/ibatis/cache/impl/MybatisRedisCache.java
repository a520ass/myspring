package org.apache.ibatis.cache.impl;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import com.hf.config.custom.SpringContextHolder;

/**
 * mybatis 二级缓存 redis实现
 * @author hefeng
 *
 */
public class MybatisRedisCache implements Cache {

	private static final Logger LOG = LoggerFactory
			.getLogger(MybatisRedisCache.class);

	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

	@SuppressWarnings({ "rawtypes" })
	private RedisTemplate redisTemplate = SpringContextHolder
			.getBean("redisTemplate");

	private String id;

	public MybatisRedisCache(final String id) {
		if (id == null) {
			throw new IllegalArgumentException("Cache instances require an ID");
		}
		LOG.info("Redis Cache id " + id);
		this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void putObject(Object key, Object value) {
		if (value != null) {
			redisTemplate.opsForValue().set(key,value);
		}
	}

	@Override
	public Object getObject(Object key) {
		try {
			if (key != null) {
				return redisTemplate.opsForValue().get(key);
			}
		} catch (Exception e) {
			LOG.error("redis cache getobject error");
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object removeObject(Object key) {
		try {
			if (key != null) {
				redisTemplate.delete(key);
			}
		} catch (Exception e) {
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void clear() {
		redisTemplate.execute(new RedisCallback<Integer>() {

			@Override
			public Integer doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.flushDb();
				return 1;
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getSize() {
		Long size = (Long) redisTemplate.execute(
				new RedisCallback<Long>() {
					@Override
					public Long doInRedis(RedisConnection connection)
							throws DataAccessException {
						return connection.dbSize();
					}
				});
		return size.intValue();
	}

	@Override
	public ReadWriteLock getReadWriteLock() {
		return this.readWriteLock;
	}

}
