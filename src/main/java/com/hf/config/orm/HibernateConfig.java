package com.hf.config.orm;

import com.hf.config.GlobalConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * Created by 520 on 2017/1/9.
 */

public class HibernateConfig {

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) throws IOException {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setConfigLocation(new ClassPathResource("hibernate.cfg.xml"));

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        //将加载多个绝对匹配的所有Resource
        //将首先通过ClassLoader.getResources("META-INF")加载非模式路径部分
        //然后进行遍历模式匹配
        Resource[] resources=resolver.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX+"hibernate/**/*.hbm.xml");
        sessionFactoryBean.setMappingLocations(resources);
        sessionFactoryBean.setPackagesToScan("com.hibernate");
        return sessionFactoryBean;
    }
}
