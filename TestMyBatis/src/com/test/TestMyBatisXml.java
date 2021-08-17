package com.test;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/** XML ��� ���̹�Ƽ�� �׽�Ʈ Ŭ����
 * 
 * @author �̿��� ( http://blog.naver.com/websearch )
 *
 */
public class TestMyBatisXml
{
	String m_strConfigPath = "com/test/Config.xml";
	SqlSessionFactory m_clsFactory = null;
	
	/** SqlSessionFactory �� �����Ѵ�.
	 * 
	 * @return SqlSessionFactory �� �����Ѵ�.
	 */
	SqlSessionFactory CreateSqlSessionFactory()
	{
		if( m_clsFactory == null )
		{
			try
			{
				InputStream clsInput = Resources.getResourceAsStream( m_strConfigPath );
				m_clsFactory = new SqlSessionFactoryBuilder().build( clsInput );
			}
			catch( Exception e )
			{
				System.out.println( "ERROR(" + e.toString( ) + ")" );
				return null;
			}
		}
		
		return m_clsFactory;
	}
	
	/** �ϳ��� ROW �� ���Ե� �ϳ��� �÷��� �������� ����
	 * 
	 * @param iId noticeboard ���̺��� nbId �÷���
	 * @return noticeboard ���̺��� nbId �÷��� ��ġ�ϴ� nbSubject �÷����� �����Ѵ�.
	 */
	String SelectSubject( int iId )
	{
		SqlSessionFactory clsFactory = CreateSqlSessionFactory();
		if( clsFactory == null ) return null;
		
		try( SqlSession clsSession = clsFactory.openSession( ) )
		{
			return clsSession.selectOne( "com.test.TestMyBatis.SelectSubject", iId );
		}
	}
	
	/** �ϳ��� ROW �� ���Ե� ��� �÷����� �������� ����
	 * 
	 * @param iId noticeboard ���̺��� nbId �÷���
	 * @return noticeboard ���̺��� nbId �÷��� ��ġ�ϴ� ��� �÷��� ������ NoticeRow ��ü�� �����Ѵ�.
	 */
	NoticeRow SelectRow( int iId )
	{
		SqlSessionFactory clsFactory = CreateSqlSessionFactory();
		if( clsFactory == null ) return null;
		
		try( SqlSession clsSession = clsFactory.openSession( ) )
		{
			return clsSession.selectOne( "com.test.TestMyBatis.SelectRow", iId );
		}
	}
	
	/** �ϳ��� ROW �� ���Ե� ��� �÷����� �������� ����
	 * 
	 * @param clsCondition ������ �����ϴ� ��ü
	 * @return NoticeRow ��ü�� �����Ѵ�.
	 */
	NoticeRow SelectRowCondition( NoticeRow clsCondition )
	{
		SqlSessionFactory clsFactory = CreateSqlSessionFactory();
		if( clsFactory == null ) return null;
		
		try( SqlSession clsSession = clsFactory.openSession( ) )
		{
			return clsSession.selectOne( "com.test.TestMyBatis.SelectRowCondition", clsCondition );
		}
	}
	
	/** N ���� ROW �� �������� ����
	 * 
	 * @return N ���� ROW �� ������ ��ü�� �����Ѵ�.
	 */
	List<NoticeRow> SelectRowList( )
	{
		SqlSessionFactory clsFactory = CreateSqlSessionFactory();
		if( clsFactory == null ) return null;
		
		try( SqlSession clsSession = clsFactory.openSession( ) )
		{
			return clsSession.selectList( "com.test.TestMyBatis.SelectRowList" );
		}
	}
	
	/** �ϳ��� ROW �� INSERT �ϴ� ����
	 * 
	 * @param clsRow NoticeRow ��ü
	 * @return �����ϸ� true �� �����ϰ� �׷��� ������ false �� �����Ѵ�.
	 */
	boolean InsertRow( NoticeRow clsRow )
	{
		SqlSessionFactory clsFactory = CreateSqlSessionFactory();
		if( clsFactory == null ) return false;
		
		try( SqlSession clsSession = clsFactory.openSession( ) )
		{
			if( clsSession.insert( "com.test.TestMyBatis.InsertRow", clsRow ) > 0 )
			{
				clsSession.commit( );
				return true;
			}
		}
		
		return false;
	}
	
	/** nbContent �÷��� �����ϴ� ����
	 * 
	 * @param clsRow NoticeRow ��ü
	 * @return �����ϸ� true �� �����ϰ� �׷��� ������ false �� �����Ѵ�.
	 */
	boolean UpdateContent( NoticeRow clsRow )
	{
		SqlSessionFactory clsFactory = CreateSqlSessionFactory();
		if( clsFactory == null ) return false;
		
		try( SqlSession clsSession = clsFactory.openSession( ) )
		{
			if( clsSession.insert( "com.test.TestMyBatis.UpdateContent", clsRow ) > 0 )
			{
				clsSession.commit( );
				return true;
			}
		}
		
		return false;
	}
	
	/** �Էµ� ID �� ��ġ�ϴ� ROW �� �����Ѵ�.
	 * 
	 * @param iId noticeboard ���̺��� nbId �÷���
	 * @return �����ϸ� true �� �����ϰ� �׷��� ������ false �� �����Ѵ�.
	 */
	boolean DeleteRow( int iId )
	{
		SqlSessionFactory clsFactory = CreateSqlSessionFactory();
		if( clsFactory == null ) return false;
		
		try( SqlSession clsSession = clsFactory.openSession( ) )
		{
			if( clsSession.delete( "com.test.TestMyBatis.DeleteRow", iId ) > 0 )
			{
				clsSession.commit( );
				return true;
			}
		}
		
		return false;
	}
	
	public static void main( String [] args )
	{
		TestMyBatisXml clsTest = new TestMyBatisXml();

		/*
		String strName = clsTest.SelectSubject( 3 );
		System.out.println( "SelectName(3) => (" + strName + ")" );
		*/
		
		/*
		NoticeRow clsRow = clsTest.SelectRow( 3 );
		System.out.println( clsRow.toString() );
		*/
		
		/*
		NoticeRow clsCondition = new NoticeRow();
		clsCondition.m_iId = 7;
		clsCondition.m_strSubject = "MyBatis";
		
		NoticeRow clsRow = clsTest.SelectRowCondition( clsCondition );
		System.out.println( clsRow.toString() );
		*/
		
		List<NoticeRow> clsList = clsTest.SelectRowList();
		for( NoticeRow clsRow : clsList )
		{
			System.out.println( clsRow.toString() );
		}
		
		/*
		NoticeRow clsRow = new NoticeRow();
		clsRow.m_strSubject = "MyBatis";
		clsRow.m_strContent = "MyBatis Content";
		clsRow.m_iReadCount = 3;
		clsRow.m_clsInsertDate = new Date();
		clsRow.m_iCommentCount = 5;
		
		clsTest.InsertRow( clsRow );
		*/
		
		/*
		NoticeRow clsRow = new NoticeRow();
		clsRow.m_iId = 7;
		clsRow.m_strContent = "MyBatis Content Updated";
		
		clsTest.UpdateContent( clsRow );
		*/
		
		/*
		clsTest.DeleteRow( 8 );
		*/
	}
}
