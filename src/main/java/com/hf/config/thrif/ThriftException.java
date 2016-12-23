package com.hf.config.thrif;


public class ThriftException extends RuntimeException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public ThriftException(String string) {
		super(string);
	}


	public ThriftException(String string, Exception e) {
		super(string, e);
	}


	public ThriftException(String string, Throwable ex) {
		super(string, ex);
	}

}
