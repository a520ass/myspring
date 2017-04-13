package org.apache.ibatis.cache.impl;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import com.hf.config.custom.SpringContextHolder;

/**
 * mybatis 二级缓存 spring cache实现
 * @author hefeng
 *
 */
public class MybatisCache implements Cache {

	private static final Logger LOG = LoggerFactory
			.getLogger(MybatisCache.class);

	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

	@SuppressWarnings({ "rawtypes" })
	private CacheManager cacheManager = SpringContextHolder
			.getBean("cacheManager");
	private static final String CACHENAME="mybatis-cache";

	private String id;

	public MybatisCache(final String id) {
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

	@Override
	public void putObject(Object key, Object value) {
		if (value != null) {
			cacheManager.getCache(CACHENAME).put(key,value);// opsForValue().set();
		}
	}

	@Override
	public Object getObject(Object key) {
		try {
			if (key != null) {
				return cacheManager.getCache(CACHENAME).get(key);//redisTemplate.opsForValue().get(key);
			}
		} catch (Exception e) {
			LOG.error("redis cache getobject error");
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object removeObject(Object key) {
		Object o=null;
		try {
			if (key != null) {
				o=cacheManager.getCache(CACHENAME).get(key);
				cacheManager.getCache(CACHENAME).evict(key);
			}
		} catch (Exception e) {
		}
		return o;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void clear() {
		cacheManager.getCache(CACHENAME).clear();
		/*redisTemplate.execute(new RedisCallback<Integer>() {

			@Override
			public Integer doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.flushDb();
				return 1;
			}
		});*/
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getSize() {
		//cacheManager.getCache(CACHENAME).
		/*Long size = (Long) redisTemplate.execute(
				new RedisCallback<Long>() {
					@Override
					public Long doInRedis(RedisConnection connection)
							throws DataAccessException {
						return connection.dbSize();
					}
				});*/
		return 1;
	}

	@Override
	public ReadWriteLock getReadWriteLock() {
		return this.readWriteLock;
	}

}
