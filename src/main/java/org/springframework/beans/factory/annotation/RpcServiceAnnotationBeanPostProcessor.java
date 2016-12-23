package org.springframework.beans.factory.annotation;

import java.rmi.RemoteException;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.remoting.RemoteService;
import org.springframework.remoting.ServiceType;
import org.springframework.remoting.caucho.BurlapServiceExporter;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.remoting.rmi.RmiServiceExporter;

public class RpcServiceAnnotationBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter implements PriorityOrdered{

	private int order = Ordered.LOWEST_PRECEDENCE;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
    
    /**
     * 	BeforeInstantiation 实例化前
    	【构造器】调用Person的构造器实例化
    	AfterInstantiation 实例化后
    	...
    	
    	//BeforeInitialization 初始化bean之前
	    //【InitializingBean接口】调用InitializingBean.afterPropertiesSet()
	    //【init-method】调用<bean>的init-method属性指定的初始化方法
	    //AfterInitialization	初始化bean之后
     */
    
    @Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException{
	    RemoteService service = AnnotationUtils.findAnnotation(bean.getClass(), RemoteService.class);
	    Object resultBean = bean;
	    if (null != service) {
	    	if(!beanName.startsWith("/")){
	            throw new FatalBeanException("Exception initializing  RpcService for "+beanName+",beanName should bean start with \"/\".");
	        }else{
	        	switch (service.serviceType()) {
				case HTTPINVOKER:
					HttpInvokerServiceExporter httpInvokerServiceExporter = new HttpInvokerServiceExporter();
	                httpInvokerServiceExporter.setServiceInterface(service.serviceInterface());
	                httpInvokerServiceExporter.setService(bean);
	                httpInvokerServiceExporter.afterPropertiesSet();
	                resultBean = httpInvokerServiceExporter;
					break;
				case HESSIAN:
					HessianServiceExporter hessianServiceExporter = new HessianServiceExporter();
	                hessianServiceExporter.setServiceInterface(service.serviceInterface());
	                hessianServiceExporter.setService(bean);
	                hessianServiceExporter.afterPropertiesSet();
	                resultBean = hessianServiceExporter;
	                break;
				case BURLAP:
					@SuppressWarnings("deprecation")
					BurlapServiceExporter burlapServiceExporter = new BurlapServiceExporter();
	                burlapServiceExporter.setServiceInterface(service.serviceInterface());
	                burlapServiceExporter.setService(bean);
	                burlapServiceExporter.afterPropertiesSet();
	                resultBean = burlapServiceExporter;
	                break;
				case RMI:
					RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
	                rmiServiceExporter.setServiceInterface(service.serviceInterface());
	                rmiServiceExporter.setService(bean);
	                rmiServiceExporter.setRegistryPort(service.registryPort());
	                String serviceName = beanName;
	                if(serviceName.startsWith("/")){
	                    serviceName = serviceName.substring(1);
	                }
	                rmiServiceExporter.setServiceName(serviceName);
	                try {
	                    rmiServiceExporter.afterPropertiesSet();
	                } catch (RemoteException remoteException) {
	                    throw new FatalBeanException("Exception initializing RmiServiceExporter", remoteException);
	                }
	                resultBean = rmiServiceExporter;
	                break;
				default:
					break;
				}
	        }
	    }
	
	    return resultBean;
	}

	public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int getOrder() {
        return order;
    }

}
