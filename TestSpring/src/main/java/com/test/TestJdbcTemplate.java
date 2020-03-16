package com.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

public class TestJdbcTemplate 
{
	SimpleDriverDataSource dataSource;
	
	JdbcTemplate jdbcTemplate;
		
	public void setDataSource( SimpleDriverDataSource dataSource )
	{
		this.dataSource = dataSource;
		jdbcTemplate = new JdbcTemplate( dataSource );
	}
		
	public void Delete( int iId )
	{
		jdbcTemplate.update( "DELETE FROM noticeboard WHERE nbId = ?", iId );
	}
	
	public static void main( String [] args )
	{
		ApplicationContext context = new GenericXmlApplicationContext("com/test/bean.xml");
		
		TestJdbcTemplate clsTest = context.getBean( "testJdbcTemplate", TestJdbcTemplate.class );
		
		clsTest.Delete( 7 );
	}
}
