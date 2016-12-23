package com.hf.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.hf.entity.Order;

public interface OrderRepository extends MongoRepository<Order, String>{

}
