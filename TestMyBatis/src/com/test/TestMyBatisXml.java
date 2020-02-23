package com.test;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class TestMyBatisXml
{
	String m_strConfigPath = "com/test/Config.xml";
	SqlSessionFactory m_clsFactory = null;
	
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
	
	String SelectSubject( int iId )
	{
		SqlSessionFactory clsFactory = CreateSqlSessionFactory();
		if( clsFactory == null ) return null;
		
		SqlSession clsSession = clsFactory.openSession( );
		
		String strSubject = clsSession.selectOne( "com.test.TestMyBatis.SelectSubject", iId );
		clsSession.close();
		
		return strSubject;
	}
	
	public static void main( String [] args )
	{
		TestMyBatisXml clsTest = new TestMyBatisXml();
		
		String strName = clsTest.SelectSubject( 3 );
		
		System.out.println( "SelectName(3) => (" + strName + ")" );
	}
}
