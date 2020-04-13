package com.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import com.test.TestJdbcTemplate.Notice;

/** @Transactional 애노테이션 테스트
 * 
 * @author 이영한 ( http://blog.naver.com/websearch )
 *
 */
public class TestTransactionalAnnotation
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
		return jdbcTemplate.query( "SELECT nbId, nbSubject, nbContent FROM noticeboard", TestJdbcTemplate.noticeMapper );
	}
	
	@Transactional
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
		ApplicationContext context = new GenericXmlApplicationContext("com/test/transactionalannotation.xml");
		
		TestTransactionalAnnotation clsTest = context.getBean( "testTransactionalAnnotation", TestTransactionalAnnotation.class );
		
		try
		{
			clsTest.DeleteAllException( );
		}
		catch( RuntimeException e )
		{
			System.out.println( "exception - " + e );
		}
		
		((ConfigurableApplicationContext) context).close();
	}
}
