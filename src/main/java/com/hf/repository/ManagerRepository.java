package com.hf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hf.entity.onetoone.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer>{

}
