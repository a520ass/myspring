package com.hf;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

import org.junit.Test;

public class JdbcTest {
	
	@Test
	public void test1() throws SQLException, ClassNotFoundException{
		//这两种可以
		//System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
		//Class.forName("com.mysql.jdbc.Driver");
		//不推荐这种。因为会注册两个driver
		//DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		
		//从jdbc4.0开始，不需要显示注册了。上述操作不需要了。
		
//		final String url="jdbc:mysql://localhost:3306/myspring";
		final String oracleUrl="jdbc:oracle:thin:@125.35.5.94:1521:orcl";
		Connection connection = DriverManager.getConnection(oracleUrl, "HEFENG3", "admin");
		final String sql = "select * from \"test\"";
		/*Statement statement = connection.createStatement();
		
		ResultSet resultSet = statement.executeQuery(sql);
		while(resultSet.next()){
			System.out.print("name=" + resultSet.getObject("name"));
			System.out.println(" password=" + resultSet.getObject("password"));
		}*/
		PreparedStatement prepareStatement = connection.prepareStatement(sql);
		ResultSet resultSet = prepareStatement.executeQuery();
		while(resultSet.next()){
			//resultSet.getRowId(columnLabel);
			System.out.print("name=" + resultSet.getObject("name"));
			//System.out.println(" password=" + resultSet.getObject("password"));
		}
		Enumeration<Driver> drivers = DriverManager.getDrivers();  
        while(drivers.hasMoreElements()){  
            Driver driver=drivers.nextElement();  
            System.out.println(driver);  
        }
		resultSet.close();
		prepareStatement.close();
		connection.close();
	}
}
