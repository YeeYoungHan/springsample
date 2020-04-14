package com.test;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

/** Map 타입 프로퍼티 테스트
 * 
 * @author 이영한 ( http://blog.naver.com/websearch )
 *
 */
public class TestMap
{
	Map<String,String> m_clsMap;
	
	public void setMap( Map<String,String> clsMap )
	{
		m_clsMap = clsMap;
	}
	
	public void Print()
	{
		for( String strKey : m_clsMap.keySet( ) )
		{
			String strValue = m_clsMap.get( strKey );
			
			System.out.println( "key[" + strKey + "] value[" + strValue + "]" );
		}
	}
	
	public static void main( String [] args )
	{
		ApplicationContext context = new GenericXmlApplicationContext("com/test/map.xml");
		
		TestMap clsTest = context.getBean( "test", TestMap.class );
		
		clsTest.Print();
		
		((ConfigurableApplicationContext) context).close();
	}
}
