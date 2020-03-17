package com.test;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

/** GenericXmlApplicationContext 기반으로 JdbcTemplate 을 사용하는 예제
 * 
 * @author 이영한 ( http://blog.naver.com/websearch )
 *
 */
public class TestJdbcTemplate 
{
	SimpleDriverDataSource dataSource;
	
	JdbcTemplate jdbcTemplate;
		
	public void setDataSource( SimpleDriverDataSource dataSource )
	{
		this.dataSource = dataSource;
		jdbcTemplate = new JdbcTemplate( dataSource );
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
	
	RowMapper<Notice> noticeMapper = new RowMapper<Notice>() {

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
	
	public static class Notice
	{
		public int m_iId;
		public String m_strSubject;
		public String m_strContent;
		
		public String toString()
		{
			return "id[" + m_iId + "] subject[" + m_strSubject + "] content[" + m_strContent + "]";
		}
	}
	
	public static void main( String [] args )
	{
		ApplicationContext context = new GenericXmlApplicationContext("com/test/bean.xml");
		
		TestJdbcTemplate clsTest = context.getBean( "testJdbcTemplate", TestJdbcTemplate.class );
		
		clsTest.Delete( 7 );
		
		int iCount = clsTest.GetCount( );
		
		System.out.println( "count = " + iCount );
		
		try
		{
			Notice clsNotice = clsTest.Select( 7 );
			System.out.println( clsNotice.toString( ) );
		}
		catch( EmptyResultDataAccessException e )
		{
			System.out.println( "not found" ); 
		}
	}
}
