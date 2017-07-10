package com.hf.config.custom.listener;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MyServletListener implements ServletContextListener,HttpSessionListener,ServletRequestListener{

	private static Logger logger = LoggerFactory
			.getLogger(MyServletListener.class);
	
	public static final List<String> MANUAL_DESTROY_THREAD_IDENTIFIERS = Arrays.asList("schedulerFactoryBean_Worker");
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//super.contextInitialized(sce);
        /*ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        ApplicationContextHolder.setContext(context);*/
		logger.info("context初始化。。"+sce.getServletContext().getServerInfo());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("context销毁。。开始销毁一些资源");
		destroyJDBCDrivers();  
       // destroySpecifyThreads();
	}
	
	@Override
	public void requestInitialized(ServletRequestEvent sre) {
	}

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
	}

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		logger.info("HttpSessionEvent创建。。");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		logger.info("HttpSessionEvent销毁。。");
	}

	private void destroyJDBCDrivers() {
		final Enumeration<Driver> drivers = DriverManager.getDrivers();
		Driver driver;
		while (drivers.hasMoreElements()) {
			driver = drivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
				logger.debug(String.format(
						"Deregister JDBC driver %s successful", driver));
			} catch (SQLException e) {
				logger.warn(String.format("Deregister JDBC driver %s error",
						driver), e);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	private void destroySpecifyThreads() {
		final Set<Thread> threads = Thread.getAllStackTraces().keySet();
		for (Thread thread : threads) {
			if (needManualDestroy(thread)) {
				synchronized (this) {
					try {
						thread.stop();	//没有一个优雅的退出方式
						logger.debug(String.format("Destroy  %s successful",
								thread));
					} catch (Exception e) {
						logger.warn(String.format("Destroy %s error", thread),
								e);
					}
				}
			}
		}
	}

	private boolean needManualDestroy(Thread thread) {
		final String threadName = thread.getName();
		for (String manualDestroyThreadIdentifier : MANUAL_DESTROY_THREAD_IDENTIFIERS) {
			if (threadName.contains(manualDestroyThreadIdentifier)) {
				return true;
			}
		}
		return false;
	}
	
}
