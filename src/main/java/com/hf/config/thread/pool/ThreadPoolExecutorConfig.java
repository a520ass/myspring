package com.hf.config.thread.pool;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class ThreadPoolExecutorConfig {
	
	@Bean
	public Executor myThreadPoolExecutor(){
		return Executors.newCachedThreadPool();
	}
	
}
