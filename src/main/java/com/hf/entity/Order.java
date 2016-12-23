package com.hf.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;





import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@XmlRootElement
@Document
public class Order implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	@Field("client")
	private String customer;
	private String type;
	private Collection<Item> items = new LinkedHashSet<Item>();

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Collection<Item> getItems() {
		return items;
	}

	public void setItems(Collection<Item> items) {
		this.items = items;
	}

	public String getId() {
		return id;
	}

	public Order() {
		super();
	}

	public Order(String id, String customer, String type, Collection<Item> items) {
		super();
		this.id = id;
		this.customer = customer;
		this.type = type;
		this.items = items;
	}
}