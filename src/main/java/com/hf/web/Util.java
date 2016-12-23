package com.hf.web;

import java.io.File;

public class Util {
	
	public static String[] list(){
		String basePath=Util.class.getResource("/").getPath();
		basePath=basePath.substring(1,basePath.length());
		return new File(basePath+File.separator+"diagrams").list();
	}

}
