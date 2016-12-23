package com.hf.repository;


import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hf.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	public User findByEmail(String email);
	
	public User findById(Integer id);
	
	@Cacheable(value = "user",keyGenerator = "keyGenerator")
	public User findByUsername(String username);
	
	@Modifying
	@Query("DELETE FROM User u WHERE u.id IN (:ids)")
	public int deleteByIds(Integer[] ids);
	
	@Query("FROM User u WHERE u.id IN (:ids)")
	public List<User> findByIds(Integer[] id);
} 
