package com.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
	
	/** 하나의 ROW 를 저장할 클래스
	 * 
	 * @author 이영한 ( http://blog.naver.com/websearch )
	 */
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
		
		// ROW 삭제 테스트
		clsTest.Delete( 7 );
		
		// 테이블 ROW 개수를 가져오는 테스트
		int iCount = clsTest.GetCount( );
		
		System.out.println( "count = " + iCount );
		
		// 하나의 ROW 를 가져오는 테스트
		try
		{
			Notice clsNotice = clsTest.Select( 7 );
			System.out.println( clsNotice.toString( ) );
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
