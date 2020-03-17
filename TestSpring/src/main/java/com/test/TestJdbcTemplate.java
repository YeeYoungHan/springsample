package com.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

/** GenericXmlApplicationContext 기반으로 JdbcTemplate 을 사용하는 예제
 * 
 * @author 이영한 ( http://blog.naver.com/websearch )
 *
 */
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
	
	public int GetCount()
	{
		return jdbcTemplate.queryForObject( "SELECT count(*) FROM noticeboard", Integer.class );
	}
	
	public static void main( String [] args )
	{
		ApplicationContext context = new GenericXmlApplicationContext("com/test/bean.xml");
		
		TestJdbcTemplate clsTest = context.getBean( "testJdbcTemplate", TestJdbcTemplate.class );
		
		clsTest.Delete( 7 );
		
		int iCount = clsTest.GetCount( );
		
		System.out.println( "count = " + iCount );
	}
}
