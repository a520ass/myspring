package com.hf.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hf.entity.manytomany.Item;


public interface ItemRepository extends JpaRepository<Item, Integer>{
	
}
