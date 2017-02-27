package com.hf.service.base;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.hf.utils.reflect.Reflections;

/**
 * 基本的CRUD
 * @author 520
 *
 * @param <T> Entity类型
 * @param <ID> ID类型
 */
@Service
public abstract class BaseService<T, ID extends Serializable> {

	@Autowired
	protected CrudRepository<T, ID> repository;
	
	public T findOne(ID id){
		return repository.findOne(id);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findByIds(ID[] ids){
		return (List<T>) Reflections.invokeMethodByName(repository, "findByIds", ids);
	}

	@SuppressWarnings("unchecked")
	public T save(T entity) {
		/*ID idFieldValue = (ID) Reflections.getFieldValue(entity, "id");
		if(idFieldValue==null){
			Reflections.setFieldValue(entity, "id", UUID.randomUUID());
		}*/
		return repository.save(entity);
	}
	
	public void delete(ID id){
		repository.delete(id);
	}
	
	public void deleteByIds(ID[] ids){
		//使用反射调用特定方法
		Reflections.invokeMethodByName(repository, "deleteByIds", ids);
	}
}
