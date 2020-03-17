package com.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

/** AnnotationConfigApplicationContext 기반으로 JdbcTemplate 을 사용하는 예제
 * 
 * @author 이영한 ( http://blog.naver.com/websearch )
 *
 */
@Configuration
public class TestJdbcTemplateAnnotation
{
	@Bean
	public SimpleDriverDataSource dataSource()
	{
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		
		dataSource.setDriverClass( org.mariadb.jdbc.Driver.class );
		dataSource.setUrl( "jdbc:mysql://localhost:3306/spring" );
		dataSource.setUsername( "spring" );
		dataSource.setPassword( "spring" );
		
		return dataSource;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate()
	{
		return new JdbcTemplate( dataSource() );
	}
	
	@Bean
	public TestJdbcTemplate2 testJdbcTemplate()
	{
		return new TestJdbcTemplate2();
	}
	
	public static class TestJdbcTemplate2
	{
		@Autowired
		JdbcTemplate jdbcTemplate;
		
		public void Delete( int iId )
		{
			jdbcTemplate.update( "DELETE FROM noticeboard WHERE nbId = ?", iId );
		}
	}
	
	public static void main( String [] args )
	{
		ApplicationContext context = new AnnotationConfigApplicationContext( TestJdbcTemplateAnnotation.class );
		
		TestJdbcTemplate2 clsTest = context.getBean( "testJdbcTemplate", TestJdbcTemplate2.class );
		
		clsTest.Delete( 5 );
		
		((ConfigurableApplicationContext) context).close();
	}
}
