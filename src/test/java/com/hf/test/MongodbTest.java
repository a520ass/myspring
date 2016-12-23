package com.hf.test;


import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hf.config.mongo.MongoConfig;
import com.hf.entity.Item;
import com.hf.entity.Order;
import com.hf.repository.mongo.OrderRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MongoConfig.class})
public class MongodbTest {
	
	@Autowired MongoTemplate mongoTemplate;
	@Autowired OrderRepository orderRepository;
	
	@Test
	public void test(){
		Collection<Item> collection=new ArrayList<Item>();
		collection.add(new Item());
		Order entity=new Order("1","hefeng","document",collection);
		mongoTemplate.save(entity);
	}
	
	@Test
	public void test1(){
		Collection<Item> collection=new ArrayList<Item>();
		collection.add(new Item());
		Order entity=new Order("2","hefeng2","document",collection);
		orderRepository.save(entity);
	}

}

