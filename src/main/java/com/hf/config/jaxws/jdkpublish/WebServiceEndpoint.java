package com.hf.config.jaxws.jdkpublish;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.hf.entity.Order;

/**
 * 当对象的生命周期不是spring管理的，而对象的属性又需要注入spring所管理的bean时.可以使用SpringBeanAutowiringSupport
 * 这里。webservice端点已经有了，可以使用jdk自带的Endpoint.publish来导入此端点？？
 * @author 520
 *
 */
@WebService(serviceName="MyWebService")
public class WebServiceEndpoint extends SpringBeanAutowiringSupport{
	
	/*@Autowired
	OrderService orderService;*/
	
	@WebMethod
	public void addOrder(Order order){
		//orderService.
	}
	
	@WebMethod
	public List<Order> getOrders(){
		return null;
	}
}
