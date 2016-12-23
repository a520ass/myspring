package com.hf.properties;

import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 *那么你向ResourceBundle（资源约束名称为base）获取abc变量的值的时候，ResourceBundle会先后搜索  
	  base_zh_CN_abc.properties  
	  base_zh_CN.properties  
	  base_zh.properties  
	  base.properties  
  	文件，直到找到abc为止
  
 *  在.properties里面，不能直接使用中文之类文字，而是要转成unicode形式(iso8859-1编码读取)
 * @author 520
 *
 */
public class PropertiesUtils {
	
	public static void main(String[] args) {
		ResourceBundle conf= ResourceBundle.getBundle("i18n/messages");
		String value= conf.getString("i18n.username");
	 
		Properties prop = new Properties();
		try {
			InputStream is = PropertiesUtils.class.getResourceAsStream("/i18n/messages.properties");
			prop.load(is);
			if (is != null) {
				is.close();
			}
		} catch (Exception e) {
			System.out.println( "file " + "catalogPath.properties" + " not found!\n" + e);
		}
		String value1= prop.getProperty("i18n.username").toString();
	}
}
//interface 
