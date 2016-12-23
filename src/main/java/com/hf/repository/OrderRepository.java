package com.hf.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hf.entity.manytoone.Order;


public interface OrderRepository extends JpaRepository<Order, Integer>{

}
