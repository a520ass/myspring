package com.hf.config.jaxws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.stereotype.Component;

import com.hf.entity.Order;

/**
 * 
 * 这里把这个端点直接当作一个spring bean 。使用SimpleJaxWsServiceExporter来导出此端点
 * @author 520
 *
 */
//@Component
@WebService(serviceName="MyWebServiceInSpring")
public class WebServiceEndpointInSpring{
	
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
