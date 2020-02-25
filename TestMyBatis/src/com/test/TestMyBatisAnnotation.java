package com.test;

import java.io.InputStream;
import java.util.Date;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/** 애노테이션 기반 마이바티스 테스트 클래스
 * 
 * @author 이영한 ( http://blog.naver.com/websearch )
 *
 */
public class TestMyBatisAnnotation
{
	String m_strConfigPath = "com/test/Config.xml";
	SqlSessionFactory m_clsFactory = null;
	
	/** SqlSessionFactory 를 리턴한다.
	 * 
	 * @return SqlSessionFactory 를 리턴한다.
	 */
	SqlSessionFactory CreateSqlSessionFactory()
	{
		if( m_clsFactory == null )
		{
			try
			{
				InputStream clsInput = Resources.getResourceAsStream( m_strConfigPath );
				m_clsFactory = new SqlSessionFactoryBuilder().build( clsInput );
				m_clsFactory.getConfiguration( ).addMapper( NoticeBoard.class );
			}
			catch( Exception e )
			{
				System.out.println( "ERROR(" + e.toString( ) + ")" );
				return null;
			}
		}
		
		return m_clsFactory;
	}
	
	/** 하나의 ROW 에 포함된 하나의 컬럼을 가져오는 예제
	 * 
	 * @param iId noticeboard 테이블의 nbId 컬럼값
	 * @return noticeboard 테이블의 nbId 컬럼과 일치하는 nbSubject 컬럼값을 리턴한다.
	 */
	String SelectSubject( int iId )
	{
		SqlSessionFactory clsFactory = CreateSqlSessionFactory();
		if( clsFactory == null ) return null;
		
		try( SqlSession clsSession = clsFactory.openSession( ) )
		{
			return clsSession.getMapper( NoticeBoard.class ).SelectSubject( iId );
		}
	}
	
	/** 하나의 ROW 에 포함된 모든 컬럼들을 가져오는 예제
	 * 
	 * @param iId noticeboard 테이블의 nbId 컬럼값
	 * @return noticeboard 테이블의 nbId 컬럼과 일치하는 모든 컬럼을 저장한 NoticeRow 객체를 리턴한다.
	 */
	NoticeRow SelectRow( int iId )
	{
		SqlSessionFactory clsFactory = CreateSqlSessionFactory();
		if( clsFactory == null ) return null;
		
		try( SqlSession clsSession = clsFactory.openSession( ) )
		{
			return clsSession.getMapper( NoticeBoard.class ).SelectRow( iId );
		}
	}
	
	/** 하나의 ROW 에 포함된 모든 컬럼들을 가져오는 예제
	 * 
	 * @param clsCondition 조건을 저장하는 객체
	 * @return NoticeRow 객체를 리턴한다.
	 */
	NoticeRow SelectRowCondition( NoticeRow clsCondition )
	{
		SqlSessionFactory clsFactory = CreateSqlSessionFactory();
		if( clsFactory == null ) return null;
		
		try( SqlSession clsSession = clsFactory.openSession( ) )
		{
			return clsSession.getMapper( NoticeBoard.class ).SelectRowCondition( clsCondition );
		}
	}
	
	/** 하나의 ROW 를 INSERT 하는 예제
	 * 
	 * @param clsRow NoticeRow 객체
	 * @return 성공하면 true 를 리턴하고 그렇지 않으면 false 를 리턴한다.
	 */
	boolean InsertRow( NoticeRow clsRow )
	{
		SqlSessionFactory clsFactory = CreateSqlSessionFactory();
		if( clsFactory == null ) return false;
		
		try( SqlSession clsSession = clsFactory.openSession( ) )
		{
			if( clsSession.getMapper( NoticeBoard.class ).InsertRow( clsRow ) > 0 )
			{
				clsSession.commit( );
				return true;
			}
		}
		
		return false;
	}
	
	/** nbContent 컬럼을 수정하는 예제
	 * 
	 * @param clsRow NoticeRow 객체
	 * @return 성공하면 true 를 리턴하고 그렇지 않으면 false 를 리턴한다.
	 */
	boolean UpdateContent( NoticeRow clsRow )
	{
		SqlSessionFactory clsFactory = CreateSqlSessionFactory();
		if( clsFactory == null ) return false;
		
		try( SqlSession clsSession = clsFactory.openSession( ) )
		{
			if( clsSession.getMapper( NoticeBoard.class ).UpdateContent( clsRow ) > 0 )
			{
				clsSession.commit( );
				return true;
			}
		}
		
		return false;
	}
	
	/** 입력된 ID 와 일치하는 ROW 를 삭제한다.
	 * 
	 * @param iId noticeboard 테이블의 nbId 컬럼값
	 * @return 성공하면 true 를 리턴하고 그렇지 않으면 false 를 리턴한다.
	 */
	boolean DeleteRow( int iId )
	{
		SqlSessionFactory clsFactory = CreateSqlSessionFactory();
		if( clsFactory == null ) return false;
		
		try( SqlSession clsSession = clsFactory.openSession( ) )
		{
			if( clsSession.getMapper( NoticeBoard.class ).DeleteRow( iId ) > 0 )
			{
				clsSession.commit( );
				return true;
			}
		}
		
		return false;
	}
	
	public static void main( String [] args )
	{
		TestMyBatisAnnotation clsTest = new TestMyBatisAnnotation();

		/*
		String strName = clsTest.SelectSubject( 3 );
		System.out.println( "SelectName(3) => (" + strName + ")" );
		*/
		
		/*
		NoticeRow clsRow = clsTest.SelectRow( 3 );
		System.out.println( clsRow.toString() );
		*/

		NoticeRow clsCondition = new NoticeRow();
		clsCondition.m_iId = 7;
		//clsCondition.m_strSubject = "MyBatis";
		
		NoticeRow clsRow = clsTest.SelectRowCondition( clsCondition );
		System.out.println( clsRow.toString() );
		
		/*
		NoticeRow clsRow = new NoticeRow();
		clsRow.m_strSubject = "MyBatis annotation";
		clsRow.m_strContent = "MyBatis annotation Content";
		clsRow.m_iReadCount = 3;
		clsRow.m_clsInsertDate = new Date();
		clsRow.m_iCommentCount = 5;
		
		clsTest.InsertRow( clsRow );
		*/
		
		/*
		NoticeRow clsRow = new NoticeRow();
		clsRow.m_iId = 9;
		clsRow.m_strContent = "MyBatis Content Updated";
		
		clsTest.UpdateContent( clsRow );
		*/
		
		//clsTest.DeleteRow( 9 );
	}
}
