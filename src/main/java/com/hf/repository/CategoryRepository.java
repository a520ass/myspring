package com.hf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hf.entity.manytomany.Category;


public interface CategoryRepository extends JpaRepository<Category, Integer>,JpaSpecificationExecutor<Category>{
	
}
