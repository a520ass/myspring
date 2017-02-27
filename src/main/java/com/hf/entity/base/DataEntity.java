/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hf.entity.base;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CreationUser;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UpdateUser;

import java.util.Date;

import javax.persistence.MappedSuperclass;


/**
 * Simple JavaBean domain object adds a name property to <code>BaseEntity</code>. Used as a base class for objects
 * needing these properties.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author hefeng
 */
@MappedSuperclass
public class DataEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;
	@CreationUser
	protected String createUser;
	@UpdateUser
	protected String updateUser;
	
	//@DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
	@CreationTimestamp
	protected Date createDate;
	
	//@DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
	@UpdateTimestamp
	protected Date updateDate;
	
	protected Integer sort;
	
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	

}
