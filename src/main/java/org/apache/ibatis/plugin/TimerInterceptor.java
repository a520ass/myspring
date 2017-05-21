package org.apache.ibatis.plugin;

import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据库操作性能拦截器,记录耗时
 * 
 * @Intercepts 定义Signature数组,因此可以拦截多个,但是只能拦截类型为：
 * Executor 
 * ParameterHandler
 * StatementHandler
 * ResultSetHandler
 * */
@Intercepts(value = {
		@Signature(type = Executor.class, method = "update", args = {
				MappedStatement.class, Object.class }),
		@Signature(type = Executor.class, method = "query", args = {
				MappedStatement.class, Object.class, RowBounds.class,
				ResultHandler.class, CacheKey.class, BoundSql.class }),
		@Signature(type = Executor.class, method = "query", args = {
				MappedStatement.class, Object.class, RowBounds.class,
				ResultHandler.class }) })
public class TimerInterceptor implements Interceptor {

	private static final Logger logger = LoggerFactory
			.getLogger(TimerInterceptor.class);

	/**
	 * 实现拦截的地方
	 * */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object target = invocation.getTarget();
		Object result = null;
		if (target instanceof Executor) {
			long start = System.currentTimeMillis();
			Method method = invocation.getMethod();
			/** 执行方法 */
			result = invocation.proceed();
			long end = System.currentTimeMillis();
			logger.info("[TimerInterceptor] execute [" + method.getName()
					+ "] cost [" + (end - start) + "] ms");
		}
		return result;
	}

	/**
	 * Plugin.wrap生成拦截代理对象
	 * */
	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {

	}

}
