package com.capstone.eta;

import javax.sql.DataSource;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;


@SpringBootTest
public class DatabaseConnectionTest {
	@Autowired
	DataSource dataSource;

	@Test
	void contextLoads() throws Exception{
		System.out.println("获取的数据库连接为:"+dataSource.getConnection()); 
	}
}	

