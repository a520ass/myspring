package com.hf.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.CustomAuthenticationEventPublisher;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;

import com.hf.service.CustomUserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	@Autowired
	private MessageSource messageSource;

	@Bean
	@Override
	protected UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**","/marco/**","/ws/**","/*.service");
	}
	
	/**
	 * 类似监听器。当session销毁时。会做一些处理
	 * 
	 */
	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	/*@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}*/

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/register").permitAll()
				.anyRequest().authenticated()
				.and()
			.exceptionHandling()
				.accessDeniedPage("/login.jsp?authorization_error=true")
				.and()
				// TODO: put CSRF protection back into this endpoint
				.csrf()
				.requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
				.disable()
			.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/home")
				.permitAll()
				.and()
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout")
				.logoutSuccessHandler(new CustomLogoutSuccessHandler())
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.permitAll()	//Grants access to the logoutSuccessUrl(String) and the logoutUrl(String) for every user.
				.and()
			.headers()
				.xssProtection()
				.block(false)
				.and().and()
			.sessionManagement()
				.invalidSessionUrl("/login?invalidsession")
				.maximumSessions(2)		//只能同时登陆两个
				.maxSessionsPreventsLogin(false)	//Prevents阻止。。。相当于xml配置中的error-if-maximum-exceeded 
				/**
				 * 当同一用户同时存在的已经通过认证的session数量超过了max-sessions所指定的值时，Spring Security的默认策略是将先前的设为无效。
				 * 如果要限制用户再次登录可以设置concurrency-control的error-if-maximum-exceeded的值为true
				 */
				.expiredUrl("/login?expired")
				.sessionRegistry(sessionRegistry());
		// @formatter:on
				
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		
		auth.authenticationProvider(authenticationProvider)
			/**
			 * 认证事件发布
			 */
			.authenticationEventPublisher(new CustomAuthenticationEventPublisher());
		//auth.userDetailsService(this.userDetailsService());
//		.inMemoryAuthentication()
//		.withUser("user").password("user").roles("USER").and()
//		.withUser("admin").password("admin").roles("USER", "ADMIN");
	}
	
	@Bean(name="myAuthenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, UserCache userCache, SaltSource saltSource){
		DaoAuthenticationProvider dap=new DaoAuthenticationProvider();
		dap.setHideUserNotFoundExceptions(false);
		dap.setUserDetailsService(userDetailsService);
		dap.setUserCache(userCache);
		dap.setMessageSource(messageSource);
		dap.setPasswordEncoder(new Md5PasswordEncoder());
		dap.setSaltSource(saltSource);
		return dap;
	}
	
	@Bean
	public UserCache userCache(CacheManager cacheManager) throws Exception{
		SpringCacheBasedUserCache uc=new SpringCacheBasedUserCache(cacheManager.getCache("spring-security-usercache"));
		return uc;
	}
	
	@Bean
	public SaltSource saltSource(){
		ReflectionSaltSource rss=new ReflectionSaltSource();
		rss.setUserPropertyToUse("username");	//这里引用username字段的值为salt值。可以自己实现继承org.springframework.security.core.userdetails.User，增加salt存储字段
		return rss;
	}
}
