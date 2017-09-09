package com.hibernate.manytoone;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class Customer {

	private Integer customerId;
	private String customerName;
	private String sex;

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
