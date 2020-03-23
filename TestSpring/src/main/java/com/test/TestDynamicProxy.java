package com.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/** 팩토리 빈을 이용한 트랜젝션 사용 예제
 * 
 * @author 이영한 ( http://blog.naver.com/websearch )
 */
public class TestDynamicProxy implements FactoryBean<Object>
{
	PlatformTransactionManager transactionManager;
	TestJdbcTemplate testJdbcTemplate;
	
	public void setTransactionManager( PlatformTransactionManager transactionManager )
	{
		this.transactionManager = transactionManager; 
	}
	
	public void setTestJdbcTemplate( TestJdbcTemplate testJdbcTemplate )
	{
		this.testJdbcTemplate = testJdbcTemplate;
	}
	
	@Override
	public Object getObject() throws Exception
	{
		TransactionHandler clsHandler = new TransactionHandler();
		clsHandler.m_clsTarget = testJdbcTemplate;
		clsHandler.transactionManager = transactionManager;
		return Proxy.newProxyInstance( getClass().getClassLoader( ), new Class[] { TestJdbcTemplateInterface.class }, clsHandler );
	}

	@Override
	public Class<?> getObjectType()
	{
		return TestJdbcTemplate.class;
	}

	@Override
	public boolean isSingleton()
	{
		return false;
	}
	
	/** Dynamic Proxy 생성을 위한 InvocationHandler
	 * 
	 * @author 이영한 ( http://blog.naver.com/websearch )
	 */
	public static class TransactionHandler implements InvocationHandler
	{
		Object m_clsTarget;
		PlatformTransactionManager transactionManager;

		@Override
		public Object invoke( Object proxy, Method method, Object[] args ) throws Throwable
		{
			boolean bUseTransaction = false;
			String strName = method.getName( );
			Object clsRet = null;
			
			// 메소드 이름으로 트랜잭션이 필요한지 검사한다.
			if( strName.equals( "DeleteAllException" ) )
			{
				bUseTransaction = true;
			}
			
			if( bUseTransaction )
			{
				// 트랜잭선이 필요하면 트랜잭션을 시작한다.
				TransactionStatus clsStatus = transactionManager.getTransaction( new DefaultTransactionDefinition() );
				
				try
				{
					clsRet = method.invoke( m_clsTarget, args );
					
					transactionManager.commit( clsStatus );
					System.out.println( "commit" );
				}
				catch( InvocationTargetException e )
				{
					transactionManager.rollback( clsStatus );
					System.out.println( "rollback" );
					throw e.getTargetException( );
				}
			}
			else
			{
				// 트랜잭선이 필요하지 않으면 트랜잭션 없이 메소드를 실행한다.
				clsRet = method.invoke( m_clsTarget, args );
			}
			
			return clsRet;
		}
	}
		
	public static void main( String [] args )
	{
		ApplicationContext context = new GenericXmlApplicationContext("com/test/bean.xml");
		
		TestJdbcTemplateInterface clsTest = context.getBean( "testDynamicProxy", TestJdbcTemplateInterface.class );
		
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
