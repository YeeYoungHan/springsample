package com.test;

import java.io.InputStream;
import java.util.Date;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/** �ֳ����̼� ��� ���̹�Ƽ�� �׽�Ʈ Ŭ����
 * 
 * @author �̿��� ( http://blog.naver.com/websearch )
 *
 */
public class TestMyBatisAnnotation
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
			return clsSession.getMapper( NoticeBoard.class ).SelectSubject( iId );
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
			return clsSession.getMapper( NoticeBoard.class ).SelectRow( iId );
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
			return clsSession.getMapper( NoticeBoard.class ).SelectRowCondition( clsCondition );
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
			if( clsSession.getMapper( NoticeBoard.class ).InsertRow( clsRow ) > 0 )
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
			if( clsSession.getMapper( NoticeBoard.class ).UpdateContent( clsRow ) > 0 )
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
