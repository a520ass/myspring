package com.hf.config.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class EhcacheConfig {

	/**
	 * net.sf.ehcache.CacheManager 工厂
	 * @return
	 */
	@Bean
	public EhCacheManagerFactoryBean ehCacheManager(){
		EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
		ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache/ehcache.xml"));
		return ehCacheManagerFactoryBean;
	}
	
	/**
	 * 通过net.sf.ehcache.CacheManager 产生spring封装好的cacheManager 
	 * @param ehCacheManager
	 * @return
	 */
	@Bean
	public CacheManager ehCachecacheManager(net.sf.ehcache.CacheManager ehCacheManager){
		EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager(ehCacheManager);
		return ehCacheCacheManager;
	}
}
