package com.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

/** 등록된 bean 리스트를 출력한다.
 * 
 * @author 이영한 ( http://blog.naver.com/websearch )
 */
public class PrintBeanList
{
	public static void main( String [] args )
	{
		ApplicationContext context = new GenericXmlApplicationContext("com/test/bean.xml");
		
		for( String strName : context.getBeanDefinitionNames( ) )
		{
			System.out.println( strName + " => " + context.getBean(strName).getClass( ).getName( ) );
		}
		
		((ConfigurableApplicationContext) context).close();
	}
}
