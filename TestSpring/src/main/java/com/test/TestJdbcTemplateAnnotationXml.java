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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

/** AnnotationConfigApplicationContext 를 사용하면서 XML bean 도 사용하는 예제
 * 
 * @author 이영한 ( http://blog.naver.com/websearch )
 *
 */
@Configuration
@ImportResource("classpath:/com/test/bean.xml")
public class TestJdbcTemplateAnnotationXml
{
	@Autowired
	SimpleDriverDataSource dataSource;
	
	@Bean
	public JdbcTemplate jdbcTemplate()
	{
		return new JdbcTemplate( dataSource );
	}
	
	@Bean
	public TestJdbcTemplate3 getJdbcTemplate()
	{
		return new TestJdbcTemplate3();
	}
	
	public static class TestJdbcTemplate3
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
		ApplicationContext context = new AnnotationConfigApplicationContext( TestJdbcTemplateAnnotationXml.class );
		
		TestJdbcTemplate3 clsTest = context.getBean( "getJdbcTemplate", TestJdbcTemplate3.class );
		
		clsTest.Delete( 4 );
		
		((ConfigurableApplicationContext) context).close();
	}
}
