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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/** GenericXmlApplicationContext 기반으로 JdbcTemplate 을 사용하는 예제
 * 
 * @author 이영한 ( http://blog.naver.com/websearch )
 *
 */
public class TestJdbcTemplate implements TestJdbcTemplateInterface
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
	
	public long Insert( String strSubject, String strContent )
	{
		KeyHolder clsKey = new GeneratedKeyHolder();
		jdbcTemplate.update( connection -> {
        PreparedStatement ps = connection.prepareStatement( "INSERT INTO noticeboard( nbSubject, nbContent, nbInsertDate ) VALUES( ?, ?, now() )", Statement.RETURN_GENERATED_KEYS );
        ps.setString( 1, strSubject );
        ps.setString( 2, strContent );
          return ps;
        }, clsKey );
		
		return (long)clsKey.getKey();
	}
	
	/** 하나의 ROW 를 삭제한다.
	 * 
	 * @param iId 키
	 */
	public void Delete( int iId )
	{
		jdbcTemplate.update( "DELETE FROM noticeboard WHERE nbId = ?", iId );
	}
	
	/** 테이블의 ROW 개수를 리턴한다.
	 * 
	 * @return 테이블의 ROW 개수를 리턴한다.
	 */
	public int GetCount()
	{
		return jdbcTemplate.queryForObject( "SELECT count(*) FROM noticeboard", Integer.class );
	}
	
	/** 하나의 ROW 를 가져온다.
	 * 
	 * @param iId 키
	 * @return 하나의 ROW 를 저장한 객체를 리턴한다.
	 */
	public Notice Select( int iId )
	{
		return jdbcTemplate.queryForObject( "SELECT nbId, nbSubject, nbContent FROM noticeboard WHERE nbId = ?", new Object[] { iId }, noticeMapper );
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
	
	/** BeanPropertyRowMapper 클래스를 이용하여서 하나의 ROW 를 가져온다.
	 * 
	 * @param iId 키
	 * @return 하나의 ROW 를 저장한 객체를 리턴한다.
	 */
	public Notice SelectAutoMapper( int iId )
	{
		return jdbcTemplate.queryForObject( "SELECT nbId, nbSubject, nbContent FROM noticeboard WHERE nbId = ?"
				, new BeanPropertyRowMapper<Notice>(Notice.class), iId );
	}
	
	/** 하나의 ROW 를 저장할 클래스
	 * 
	 * @author 이영한 ( http://blog.naver.com/websearch )
	 */
	public static class Notice
	{
		public int m_iId;
		public String m_strSubject;
		public String m_strContent;
		
		public void setNbId( int iId )
		{
			m_iId = iId;
		}
		
		public void setNbSubject( String strSubject )
		{
			m_strSubject = strSubject;
		}
		
		public void setNbContent( String strContent )
		{
			m_strContent = strContent;
		}
		
		public String toString()
		{
			return "id[" + m_iId + "] subject[" + m_strSubject + "] content[" + m_strContent + "]";
		}
	}
	
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
		ApplicationContext context = new GenericXmlApplicationContext("com/test/bean.xml");
		
		TestJdbcTemplate clsTest = context.getBean( "testJdbcTemplate", TestJdbcTemplate.class );
		
		// INSERT 테스트
		long iId = clsTest.Insert( "test", "test1234" );
		
		System.out.println( "id=" + iId );
		
		// ROW 삭제 테스트
		clsTest.Delete( 7 );
		
		// 테이블 ROW 개수를 가져오는 테스트
		int iCount = clsTest.GetCount( );
		
		System.out.println( "count = " + iCount );
		
		// 하나의 ROW 를 가져오는 테스트
		try
		{
			Notice clsNotice = clsTest.Select( 4 );
			System.out.println( clsNotice.toString( ) );
			
			clsNotice = clsTest.SelectAutoMapper( 3 );
			System.out.println( "SelectAutoMapper => " + clsNotice.toString( ) );
		}
		catch( EmptyResultDataAccessException e )
		{
			System.out.println( "not found" ); 
		}
		
		// N 개의 ROW 를 가져오는 테스트
		try
		{
			List<Notice> arrNotice = clsTest.SelectAll( );
			
			for( Notice clsNotice : arrNotice )
			{
				System.out.println( clsNotice.toString( ) );
			}
		}
		catch( EmptyResultDataAccessException e )
		{
			System.out.println( "not found" ); 
		}
		
		// Transaction 테스트
		clsTest.TestTransaction( );
				
		((ConfigurableApplicationContext) context).close();
	}
}
