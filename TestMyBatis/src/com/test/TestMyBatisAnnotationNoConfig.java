package com.test;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mariadb.jdbc.MariaDbDataSource;

public class TestMyBatisAnnotationNoConfig
{
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
				MariaDbDataSource clsDataSource = new MariaDbDataSource();
				
				clsDataSource.setUrl( "jdbc:mysql://localhost:3306/spring" );
				clsDataSource.setUserName("spring");
				clsDataSource.setPassword( "spring" );
				
				TransactionFactory clsTrFactory = new JdbcTransactionFactory();
				Environment clsEnv = new Environment("test", clsTrFactory, clsDataSource );
				
				Configuration clsConfig = new Configuration();
				
				clsConfig.setEnvironment( clsEnv );
				clsConfig.addMapper( NoticeBoard.class );
				
				m_clsFactory = new SqlSessionFactoryBuilder().build( clsConfig );
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
	
	public static void main( String [] args )
	{
		TestMyBatisAnnotationNoConfig clsTest = new TestMyBatisAnnotationNoConfig();

		String strName = clsTest.SelectSubject( 3 );
		System.out.println( "SelectName(3) => (" + strName + ")" );
	}
}
