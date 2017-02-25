package com.hf.config;

import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.core.annotation.Order;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.filter.Slf4jRequestLoggingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.hf.config.custom.Listener.MyServletListener;
import com.hf.config.custom.Listener.SessionCreatedListener;
import com.hf.config.custom.Listener.SessionDestroyedListener;
import com.hf.config.custom.filter.MyFilter;
import com.hf.config.custom.servlet.MyServlet;

/**
 * order 设置顺序
 * org.springframework.web.SpringServletContainerInitializer.onStartup(Set<Class<?>>, ServletContext) 此类方法中最后几句
 * @author 520
 *
 */
@Order(102)
public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {RootConfig.class};	//spring root
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {WebConfig.class};	//spring mvc
	}

	@Override
	protected String[] getServletMappings() {
		return new String[]{"/","*.service"};
	}
	
	@Override
	protected void customizeRegistration(ServletRegistration.Dynamic registration) {
		//StandardServletMultipartResolver  servlet3.0以上
		registration.setMultipartConfig(new MultipartConfigElement("d:/tmp"));
	}
	
	//org.springframework.security.web.FilterChainProxy.VirtualFilterChain.doFilter(ServletRequest, ServletResponse)
	//spring security中的这个方法，应该是保证了附加的spring security的过滤器先于原始的servlet过滤器执行
	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		super.onStartup(servletContext);
		servletContext.setInitParameter("spring.profiles.active", "home");
		//servlet
		ServletRegistration.Dynamic myServlet = servletContext.addServlet("myServlet",
				MyServlet.class);
		myServlet.addMapping("/servlet/myServlet");
		//filer
		/**
		 * characterEncodingFilter 编码过滤器 (只能解决post请求中的乱码问题，get请求。在tomcat中设置，或者。使用com.hf.config.custom.request.GetHttpServletRequestWrapper)
		 */
		FilterRegistration.Dynamic characterEncodingFilter = servletContext
				.addFilter("characterEncodingFilter", CharacterEncodingFilter.class);
		characterEncodingFilter.setInitParameter("encoding", "UTF-8");
		characterEncodingFilter.setInitParameter("forceEncoding", "true");
		characterEncodingFilter.addMappingForUrlPatterns(null, false, "/*");
		/**
		 * hiddenHttpMethodFilter 可以把POST请求转为PUT和DELETE
		 */
		FilterRegistration.Dynamic hiddenHttpMethodFilter = servletContext
				.addFilter("hiddenHttpMethodFilter", HiddenHttpMethodFilter.class);
		hiddenHttpMethodFilter.addMappingForUrlPatterns(null, false, "/*");
		/**
		 * openEntityManagerInViewFilter 可以解决hibernate（jpa的一个实现）的懒加载异常的问题
		 */
		FilterRegistration.Dynamic openEntityManagerInViewFilter = servletContext
				.addFilter("openEntityManagerInViewFilter", OpenEntityManagerInViewFilter.class);
		openEntityManagerInViewFilter.addMappingForUrlPatterns(null, false, "/*");

		/**
		 * 自定义的RequestLogging过滤器
		 */
		FilterRegistration.Dynamic logFilter = servletContext
				.addFilter("logFilter", Slf4jRequestLoggingFilter.class);
		logFilter.addMappingForUrlPatterns(null, false, "/*");
		/**
		 * 自定义的过滤器
		 */
		FilterRegistration.Dynamic filter = servletContext
				.addFilter("myFilter", MyFilter.class);
		filter.addMappingForUrlPatterns(null, false, "/custom/*");
		//listener
		servletContext.addListener(MyServletListener.class);
	}

}
