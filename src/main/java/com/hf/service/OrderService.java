package com.hf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hf.entity.Order;
import com.hf.repository.mongo.OrderRepository;
import com.hf.service.base.BaseService;

@Service
@Transactional
public class OrderService extends BaseService<Order, String>{
	
	/*@Autowired
	private OrderRepository orderRepository;*/
}
