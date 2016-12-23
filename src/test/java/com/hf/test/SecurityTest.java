package com.hf.test;

import org.junit.Test;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class SecurityTest {
	
	@Test
    public void ps(){
    	Md5PasswordEncoder pwe=new Md5PasswordEncoder();
    	String string = pwe.encodePassword("admin", "hefeng");
    	System.err.print(string);
    }
}
