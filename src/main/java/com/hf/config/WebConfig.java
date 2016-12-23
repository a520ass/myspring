package com.hf.config;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.CachingResourceResolver;
import org.springframework.web.servlet.resource.CachingResourceTransformer;
import org.springframework.web.servlet.resource.CssLinkResourceTransformer;
import org.springframework.web.servlet.resource.GzipResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.VersionResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import com.hf.config.custom.interceptor.MyHandlerInterceptor;

//@Configuration
@EnableWebMvc	//启用spring mvc DelegatingWebMvcConfiguration
@ComponentScan({GlobalConfig.BASEWEBPACKAGES})	//组件扫描
@EnableAspectJAutoProxy(proxyTargetClass=true)
@Lazy(true)
public class WebConfig extends WebMvcConfigurerAdapter{
	
	@Autowired
	@Qualifier("ehCachecacheManager")
	CacheManager cacheManager;
	
	@Autowired 
	@Qualifier("asyncExecutor")
	AsyncTaskExecutor asyncTaskExecutor;
	
	@Bean	//Thymeleaf视图解析器
	public ViewResolver viewResolver(TemplateEngine templateEngine) {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		if (templateEngine instanceof SpringTemplateEngine) {
			viewResolver.setTemplateEngine((SpringTemplateEngine)templateEngine);
		}
		viewResolver.setCharacterEncoding("UTF-8");
		viewResolver.setCache(false);
		viewResolver.setOrder(0);
		return viewResolver;
	}

	@Bean
	public TemplateEngine templateEngine(Set<ITemplateResolver> templateResolvers) {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolvers(templateResolvers);
		templateEngine.addDialect(new SpringSecurityDialect());	//添加方言、类似jsp添加标签
		return templateEngine;
	}
	//Servlet上下文下解析
	@Bean
	public TemplateResolver servletContextTemplateResolver() {
		TemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setPrefix("/WEB-INF/templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setCharacterEncoding("UTF-8");
		templateResolver.setCacheable(false);	//关闭缓存
		templateResolver.setOrder(2);
		return templateResolver;
	}
	//类路径下解析
	@Bean
	public TemplateResolver classLoaderTemplateResolver(){
		ClassLoaderTemplateResolver ctr = new ClassLoaderTemplateResolver();
		ctr.setPrefix("mail/");
		ctr.setTemplateMode("HTML5");
		ctr.setCharacterEncoding("UTF-8");
		ctr.setOrder(1);
		return ctr;
	}
	
	@Bean
	public ViewResolver jspViewResolver(){	//jsp视图解析器
		InternalResourceViewResolver resolver=
				new  InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		resolver.setExposeContextBeansAsAttributes(true);
		resolver.setOrder(1);
		return resolver;
	}
	
	//StandardServletMultipartResolver  servlet3.0以上
	@Bean
	public MultipartResolver multipartResolver() throws IOException {
		return new StandardServletMultipartResolver();
		
	}
	
	//CommonsMultipartResolver 兼容servlet3.0以下的
	/*@Bean
	public MultipartResolver multipartResolver() throws IOException {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setUploadTempDir(new FileSystemResource(
				"/tmp/spittr/uploads"));
		multipartResolver.setMaxUploadSize(2097152);
		multipartResolver.setMaxInMemorySize(0);
		return multipartResolver;
	}*/
	
	/**
	 * 	default-servlet-handler 将在 SpringMVC 上下文中定义一个 DefaultServletHttpRequestHandler,
		它会对进入 DispatcherServlet 的请求进行筛查, 如果发现是没有经过映射的请求, 就将该请求交由 WEB 应用服务器默认的 
		Servlet 处理. 如果不是静态资源的请求，才由 DispatcherServlet 继续处理
		一般 WEB 应用服务器默认的 Servlet 的名称都是 default.
		若所使用的 WEB 服务器的默认 Servlet 名称不是 default，则需要通过 default-servlet-name 属性显式指定
	 */
	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
		configurer
			.setDefaultTimeout(20*1000)
			.setTaskExecutor(asyncTaskExecutor);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		/** 
	     * 内置的VersionResourceResolver有FixedVersionStrategy和ContentVersionStrategy两种策略, 
	     * FixedVersionStrategy可以使用某项属性,或者日期,或者其它来作为版本. 
	     * 而ContentVersionStrategy是使用资源内容计算出来的MD5哈希作为版本. 
	     * ContentVersionStrategy是个不错的默认选择,除了某些不能使用的情况下(比如,带有javascript模块加截器). 
	     * 以下配置:如果是js的话使用FixedVersionStrategy,其它(如css,img)使用默认的ContentVersionStrategy策略. 
	     */
		
		/*registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/")
				.setCachePeriod(31556926);*/
				
		registry.addResourceHandler("/resources/**")
				.addResourceLocations("/WEB-INF/static/")
				.resourceChain(false)
				.addResolver(new GzipResourceResolver())
				.addResolver(new CachingResourceResolver(cacheManager,"spring-resource-chain-cache"))
				.addResolver(new VersionResourceResolver().addFixedVersionStrategy("5.0beta", "/**/*.js").addContentVersionStrategy("/**"))
				//.addResolver(new WebJarsResourceResolver())	自动添加
				//.addResolver(new PathResourceResolver())		自动添加 见此方法org.springframework.web.servlet.config.annotation.ResourceChainRegistration.getResourceResolvers()
				.addTransformer(new CachingResourceTransformer(cacheManager,"spring-resource-chain-cache"))
				.addTransformer(new CssLinkResourceTransformer());
	}
	
	@Override
	public void addFormatters(FormatterRegistry registry) {
        // Add formatters and/or converters
    }
	
	/**
	 * @see WebMvcConfigurationSupport 
	 * @see addDefaultHttpMessageConverters
	 */
    @Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    	int json=0;
    	int xml=0;
    	for (int i = 0; i < converters.size(); i++) {
    		//修改StringHttpMessageConverter的字符编码
    		if(converters.get(i) instanceof StringHttpMessageConverter){
        		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        		stringConverter.setWriteAcceptCharset(false);
        		converters.remove(i);	//移除原有的
        		converters.add(i, stringConverter);	//添加新增的
        		continue;//结束本次循环
        	}
    		if(converters.get(i) instanceof MappingJackson2XmlHttpMessageConverter||converters.get(i) instanceof Jaxb2RootElementHttpMessageConverter){
    			xml=i;
    			continue;
    		}
    		if(converters.get(i) instanceof MappingJackson2HttpMessageConverter || converters.get(i) instanceof GsonHttpMessageConverter){
    			json=i;
    			break;//结束所有循环
    		}
		}
    	//把先xml后json的顺序变为先json后xml
    	/*HttpMessageConverter<?> xmlMessageConverter = converters.get(xml);
    	converters.set(xml, converters.get(json));
    	converters.set(json, xmlMessageConverter);*/
	}
    
	/*@Override	重写会覆盖掉spring mvc默认注册的多个HttpMessageConverter
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // Configure the list of HttpMessageConverters to use
    }*/

	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyHandlerInterceptor());
    }
    
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer
				.setUseSuffixPatternMatch(true)
				.setUseTrailingSlashMatch(true)
				.setUseRegisteredSuffixPatternMatch(false);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("*")
				.allowedHeaders("*");
	}
    
	//可以在不需要Controller处理request的情况，转向到设置的View
	//相当于 <mvc:view-controller path="/" view-name="home"/>
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		//registry.addViewController("/").setViewName("home");
	}

	//rpc 使用的HandlerMapping
	@Bean
	public HandlerMapping rpcHandlerMapping(){
		SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
		Properties properties = new Properties();
		//properties.setProperty("/hessianrpc.service", "hessianServiceExporter");
		//properties.setProperty("/burlaprpc.service", "burlapServiceExporter");
		properties.setProperty("/httpinvokerrpc.service", "httpInvokerServiceExporter");
		properties.setProperty("/thriftrpc.service", "thriftHttpServiceExporter");
		mapping.setMappings(properties);
		mapping.setOrder(3);//在beannameurlhandlermanpping之后
		return mapping;
	}
	
}
