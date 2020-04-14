package com.test;

import javax.annotation.PostConstruct;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

/** @PostConstruct 테스트
 * 
 * @author 이영한 ( http://blog.naver.com/websearch )
 *
 */
public class TestPostConstruct
{
	String m_strName;
	
	public void setName( String strName )
	{
		m_strName = strName;
	}
	
	@PostConstruct
	public void Init()
	{
		System.out.println( "TestPostConstruct.Init() - name(" + m_strName + ")" );
	}
	
	public void Print()
	{
		System.out.println( "TestPostConstruct.Print()" );
	}
	
	public static void main( String [] args )
	{
		ApplicationContext context = new GenericXmlApplicationContext("com/test/postconstruct.xml");
		
		TestPostConstruct clsTest = context.getBean( "test", TestPostConstruct.class );
		
		clsTest.Print();
		
		((ConfigurableApplicationContext) context).close();
	}
}
