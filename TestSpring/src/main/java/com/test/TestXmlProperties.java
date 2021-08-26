/* 
 * Copyright (C) 2018 Yee Young Han <websearch@naver.com> (http://blog.naver.com/websearch)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
 */

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
