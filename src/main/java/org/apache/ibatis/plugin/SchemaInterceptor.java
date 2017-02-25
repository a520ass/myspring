package org.apache.ibatis.plugin;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

@Intercepts({
		@Signature(type = Executor.class, method = "query", args = {
				MappedStatement.class, Object.class, RowBounds.class,
				ResultHandler.class }),
		@Signature(type = Executor.class, method = "query", args = {
				MappedStatement.class, Object.class, RowBounds.class,
				ResultHandler.class, CacheKey.class, BoundSql.class }),
		 })
public class SchemaInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object[] args = invocation.getArgs();
		MappedStatement ms = (MappedStatement) args[0];
		Object parameter = args[1];
		RowBounds rowBounds = (RowBounds) args[2];
		ResultHandler resultHandler = (ResultHandler) args[3];
		Executor executor = (Executor) invocation.getTarget();
		CacheKey cacheKey;
		BoundSql boundSql;
		// 由于逻辑关系，只会进入一次
		if (args.length == 4) {
			// 4 个参数时
			boundSql = ms.getBoundSql(parameter);
			cacheKey = executor.createCacheKey(ms, parameter, rowBounds,
					boundSql);
		} else {
			// 6 个参数时
			cacheKey = (CacheKey) args[4];
			boundSql = (BoundSql) args[5];
		}
		String sql = boundSql.getSql();

		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {

	}

}
