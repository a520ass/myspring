package com.hf.repository;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.hf.entity.manytoone.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Integer>,JpaSpecificationExecutor<Customer>{
	
	Customer getById(Integer id);
	
	@QueryHints({@QueryHint(name = "org.hibernate.cacheable", value ="true")}) 
	@Query("select c from Customer c where c.id=(select max(c2.id) from Customer c2))")
	Customer findMaxIdCustomer();
}
