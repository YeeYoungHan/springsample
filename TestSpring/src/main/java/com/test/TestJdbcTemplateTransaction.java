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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.test.TestJdbcTemplate.Notice;

/** GenericXmlApplicationContext 기반으로 JdbcTemplate 을 사용하는 예제
 * 
 * @author 이영한 ( http://blog.naver.com/websearch )
 *
 */
public class TestJdbcTemplateTransaction implements TestJdbcTemplateInterface
{
	JdbcTemplate jdbcTemplate;
	
	PlatformTransactionManager transactionManager;
		
	public void setJdbcTemplate( JdbcTemplate jdbcTemplate )
	{
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void setTransactionManager( PlatformTransactionManager transactionManager )
	{
		this.transactionManager = transactionManager; 
	}
	
	/** 테이블에 존재하는 모든 ROW 를 가져온다.
	 * 
	 * @return 테이블에 존재하는 모든 ROW 를 저장한 객체를 리턴한다.
	 */
	public List<Notice> SelectAll()
	{
		return jdbcTemplate.query( "SELECT nbId, nbSubject, nbContent FROM noticeboard", noticeMapper );
	}
	
	static RowMapper<Notice> noticeMapper = new RowMapper<Notice>() {

		@Override
		public Notice mapRow( ResultSet rs, int rowNum ) throws SQLException
		{
			Notice clsNotice = new Notice();
			
			clsNotice.m_iId = rs.getInt( "nbId" );
			clsNotice.m_strSubject = rs.getString( "nbSubject" );
			clsNotice.m_strContent = rs.getString( "nbContent" );
			
			return clsNotice;
		}
	};
	
	/** transaction 테스트 */
	public void TestTransaction()
	{
		TransactionStatus clsStatus = transactionManager.getTransaction( new DefaultTransactionDefinition() );
		
		try
		{
			DeleteAllException();
			
			transactionManager.commit( clsStatus );
			System.out.println( "commit" );
		}
		catch( RuntimeException e )
		{
			transactionManager.rollback( clsStatus );
			System.out.println( "rollback" ); 
		}
	}
	
	public void DeleteAllException()
	{
		List<Notice> arrNotice = SelectAll( );
		int iIndex = 0;
		
		for( Notice clsNotice : arrNotice )
		{
			System.out.println( clsNotice.toString( ) );
			jdbcTemplate.update( "DELETE FROM noticeboard WHERE nbId = ?", clsNotice.m_iId );
			++iIndex;
			if( iIndex == 2 ) throw new RuntimeException();
		}
	}
	
	public static void main( String [] args )
	{
		ApplicationContext context = new GenericXmlApplicationContext("com/test/transaction.xml");
		
		TestJdbcTemplateTransaction clsTest = context.getBean( "testJdbcTemplate", TestJdbcTemplateTransaction.class );
		
		// Transaction 테스트
		clsTest.TestTransaction( );
				
		((ConfigurableApplicationContext) context).close();
	}
}
