package com.hf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.hf.entity.onetoone.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer>,JpaSpecificationExecutor<Department>{

}
