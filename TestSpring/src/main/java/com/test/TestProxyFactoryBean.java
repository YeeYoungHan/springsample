package com.test;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/** 프록시 팩토리 빈을 이용한 트랜젝션 사용 예제
 * 
 * @author 이영한 ( http://blog.naver.com/websearch )
 */
public class TestProxyFactoryBean implements MethodInterceptor
{
	PlatformTransactionManager transactionManager;

	public void setTransactionManager( PlatformTransactionManager transactionManager )
	{
		this.transactionManager = transactionManager; 
	}
	
	@Override
	public Object invoke( MethodInvocation invocation ) throws Throwable
	{
		TransactionStatus clsStatus = transactionManager.getTransaction( new DefaultTransactionDefinition() );
		
		try
		{
			Object clsObj = invocation.proceed( );			
			transactionManager.commit( clsStatus );
			System.out.println( "commit" );
			
			return clsObj;
		}
		catch( RuntimeException e )
		{
			transactionManager.rollback( clsStatus );
			System.out.println( "rollback" );
			throw e;
		}
	}
	
	
	public static void main( String [] args )
	{
		ApplicationContext context = new GenericXmlApplicationContext("com/test/bean.xml");
		
		TestJdbcTemplateInterface clsTest = context.getBean( "testProxyFactoryBean", TestJdbcTemplateInterface.class );
		
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
