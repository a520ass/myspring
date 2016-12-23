package com.hf.web.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.hf.entity.Item;
import com.hf.entity.Order;
import com.hf.repository.mongo.OrderRepository;

@RestController
@RequestMapping("/order")
public class OrderMongoController {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@RequestMapping("/save")
	public Object save(){
		Collection<Item> collection=new ArrayList<Item>();
		collection.add(new Item());
		Order entity=new Order("1","hefeng","document",collection);
		orderRepository.save(entity);
		return entity;
	}
	
	@RequestMapping("/get")
	public Callable<Order> get(){
		return new Callable<Order>() {

			@Override
			public Order call() throws Exception {
				Thread.sleep(3000);
				return orderRepository.findOne("1");
			}
		};
	}
	
	private DeferredResult<Order> deferredResult;
	
	@RequestMapping("/get1")
	public DeferredResult<Order> get1(){
		deferredResult=new DeferredResult<>();
		return deferredResult;
	}
	
	@Scheduled(fixedDelay=5000)
	private void refresh(){
		if (deferredResult != null) {
            deferredResult.setResult(orderRepository.findOne("1"));
        }
	}
}
