package com.hf.config.datasource;

import java.util.Map;
import java.util.Random;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.hf.utils.reflect.Reflections;

/**
 * 切换数据源Advice
 *
 * @create 2016年1月23日
 */
//@Aspect
//@Order(-1)// 保证该AOP在@Transactional事物管理器之前执行
//@Component
public class DynamicDataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);
    
    @Autowired
    private DynamicDataSource dynamicDataSource;
    
    @SuppressWarnings("unchecked")
	@Before("@annotation(ds)")
    public void changeDataSource(JoinPoint point, TargetDataSource ds) throws Throwable {
        String dsId = ds.name();
        Map<Object, Object> map=(Map<Object, Object>) Reflections.getFieldValue(dynamicDataSource, "targetDataSources");
        if (!map.containsKey(dsId)) {
            logger.warn("数据源[{}]不存在，使用默认数据源 > {}", dsId, point.getSignature());
        } else if(dsId.equals("random")){
        	String[] keys = map.keySet().toArray(new String[0]);
        	Random random = new Random();
        	dsId = keys[random.nextInt(keys.length)];
        	logger.debug("使用随机数据源 : {} > {}", dsId, point.getSignature());
        	DynamicDataSourceContextHolder.setDataSourceType(dsId);
        }else{
        	logger.debug("使用数据源 : {} > {}", dsId, point.getSignature());
            DynamicDataSourceContextHolder.setDataSourceType(dsId);
        }
    }

    @After("@annotation(ds)")
    public void restoreDataSource(JoinPoint point, TargetDataSource ds) {
        logger.debug("Revert DataSource : {} > {}", ds.name(), point.getSignature());
        DynamicDataSourceContextHolder.clearDataSourceType();
    }

}
