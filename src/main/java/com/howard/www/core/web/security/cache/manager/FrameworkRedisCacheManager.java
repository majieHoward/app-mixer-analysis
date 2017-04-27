package com.howard.www.core.web.security.cache.manager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.howard.www.core.web.security.cache.FrameworkRedisCache;

public class FrameworkRedisCacheManager implements CacheManager {
	protected final Logger log = LoggerFactory
			.getLogger(FrameworkRedisCacheManager.class);
	/**
	 * The Redis key prefix for caches
	 */
	private String redisSessionCacheKeyPrefix = "shiro_redis_session:";

	private FrameworkRedisManager frameworkRedisManager;

	// fast lookup by name map
	private final ConcurrentMap<String, FrameworkRedisCache> caches = new ConcurrentHashMap<String, FrameworkRedisCache>();

	public String getRedisSessionCacheKeyPrefix() {
		return redisSessionCacheKeyPrefix;
	}

	public void setRedisSessionCacheKeyPrefix(String redisSessionCacheKeyPrefix) {
		this.redisSessionCacheKeyPrefix = redisSessionCacheKeyPrefix;
	}

	public FrameworkRedisManager getFrameworkRedisManager() {
		return frameworkRedisManager;
	}

	public void setFrameworkRedisManager(
			FrameworkRedisManager frameworkRedisManager) {
		this.frameworkRedisManager = frameworkRedisManager;
	}

	@Override
	public <K, V> Cache<K, V> getCache(String paramString)
			throws CacheException {
		log.debug("获取名称为: " + paramString + " 的RedisCache实例");
		FrameworkRedisCache<K, V> cache = caches.get(paramString);
		if (cache == null) {
			// initialize the Redis manager instance
			frameworkRedisManager.initializationRedisConnection();
			// create a new cache instance
			cache = new FrameworkRedisCache<K, V>(frameworkRedisManager,
					this.redisSessionCacheKeyPrefix);
			// add it to the cache collection
			caches.put(paramString, cache);
		}
		return cache;
	}

	public <K, V> FrameworkRedisCache<K, V> obtainFrameworkRedisCache(
			String paramString) throws Exception {
		log.debug("获取名称为: " + paramString + " 的RedisCache实例");
		FrameworkRedisCache<K, V> cache = caches.get(paramString);
		if (cache == null) {
			// initialize the Redis manager instance
			frameworkRedisManager.initializationRedisConnection();
			// create a new cache instance
			cache = new FrameworkRedisCache<K, V>(frameworkRedisManager,
					this.redisSessionCacheKeyPrefix);
			// add it to the cache collection
			caches.put(paramString, cache);
		}
		return cache;
	}
}
