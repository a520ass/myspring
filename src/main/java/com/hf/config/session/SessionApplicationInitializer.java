package com.hf.config.session;

import org.springframework.core.annotation.Order;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

/**
 * 编写一个SessionApplicationInitializer 类，
 * 它继承自AbstractHttpSessionApplicationInitializer。
 * 该类不需要重载或实现任何方法，它的作用是在Servlet容器初始化时，
 * 从Spring容器中获取一个默认名叫sessionRepositoryFilter的过滤器类（之前没有注册的话这里找不到会报错），并添加到Servlet过滤器链中
 * @author 520
 *
 */
@Order(100)
public class SessionApplicationInitializer extends AbstractHttpSessionApplicationInitializer {

	public SessionApplicationInitializer() {
		super();//不能调用有参数的，见org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer.onStartup(ServletContext) 112行
	}

}
