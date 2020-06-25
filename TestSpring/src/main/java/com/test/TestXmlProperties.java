package com.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/** XML 파일에 properties 파일을 포함시켜서 properties 파일에 저장된 설정들을 XML 파일에서 사용하는 테스트
 * 
 * @author 이영한 ( http://blog.naver.com/websearch )
 *
 */
public class TestXmlProperties
{
	JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate( JdbcTemplate jdbcTemplate )
	{
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/** 테이블의 ROW 개수를 리턴한다.
	 * 
	 * @return 테이블의 ROW 개수를 리턴한다.
	 */
	public int GetCount()
	{
		return jdbcTemplate.queryForObject( "SELECT count(*) FROM noticeboard", Integer.class );
	}

	public static void main( String [] args )
	{
		ApplicationContext context = new GenericXmlApplicationContext("com/test/properties.xml");
		
		TestXmlProperties clsTest = context.getBean( "testProperties", TestXmlProperties.class );
		int iCount = clsTest.GetCount();
		
		System.out.println( "count = " + iCount );
		
		((ConfigurableApplicationContext) context).close();
	}
}
