package com.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

/** AOP 를 이용한 Dynamic Proxy 생성 테스트
 * 
 * @author 이영한 ( http://blog.naver.com/websearch )
 */
public class TestAop
{
	public static void main( String [] args )
	{
		ApplicationContext context = new GenericXmlApplicationContext("com/test/aop.xml");
		
		TestJdbcTemplateInterface clsTest = context.getBean( "testJdbcTemplate", TestJdbcTemplateInterface.class );
		
		try
		{
			clsTest.DeleteAllException( );
		}
		catch( RuntimeException e )
		{
			System.out.println( "exception - " + e );
		}
		
		((ConfigurableApplicationContext) context).close();
	}
}
