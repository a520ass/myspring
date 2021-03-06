package com.hf.test;

import org.junit.Test;

import static
org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static
org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static
org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.hf.web.HomeController;

public class HomeControllerTest {
	
	@Test
	public void testHomePage() throws Exception {
		HomeController controller = new HomeController();
		MockMvc mockMvc = standaloneSetup(controller).build();
		mockMvc.perform(get("/")).andExpect(view().name("home"));
	}

	@Test
	public void test(){
		Md5PasswordEncoder pwe=new Md5PasswordEncoder();
	  String string = pwe.encodePassword("123456", "admin");
	  System.err.print(string);
	}
}
