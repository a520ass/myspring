package com.hf.reflection;

public class TestUser {
	
	private String privateField;
	protected String protectedField;
	String defaultField;
	public String publicField;
	
	static{
		System.out.println("静态代码块");
	}
	
	{
		System.out.println("非静态代码块");
	}
	
	private String getPrivateField() {
		return privateField;
	}
	private void setPrivateField(String privateField) {
		this.privateField = privateField;
	}
	
	protected String getProtectedField() {
		return protectedField;
	}
	protected void setProtectedField(String protectedField) {
		this.protectedField = protectedField;
	}
	
	 String getDefaultField() {
		return defaultField;
	}
	 void setDefaultField(String defaultField) {
		this.defaultField = defaultField;
	}
	 
	public String getPublicField() {
		return publicField;
	}
	public void setPublicField(String publicField) {
		this.publicField = publicField;
	}
	
	
}
