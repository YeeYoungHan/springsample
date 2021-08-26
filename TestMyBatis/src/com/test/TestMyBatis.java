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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class TestMyBatis
{
	String m_strConfigPath = "com/test/Config.xml";
	SqlSessionFactory m_clsFactory = null;

	// key = SQL, value = statement
	Map<String,String> m_clsMap = new HashMap<String,String>();
	
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

	/** SQL 문을 입력받아서 쿼리 실행한 후, 한개의 row 객체를 리턴한다.
	 * 
	 * @param strSQL			SQL 문
	 * @param resultType	리턴 클래스 타입
	 * @return 성공하면 한개의 row 객체를 리턴하고 그렇지 않으면 null 을 리턴한다.
	 */
	public <T> T SelectOne( String strSQL, Class<T> resultType )
	{
		SqlSessionFactory clsFactory = CreateSqlSessionFactory();
		if( clsFactory == null ) return null;
		
		try( SqlSession clsSession = clsFactory.openSession( ) )
		{
			String strName = m_clsMap.get( strSQL );
	
			// SQL 과 관련된 statement 이름이 Configuration 에 저장되어 있지 않은 경우에 statement 를 생성한 후, Configuration 에 저장한다.
			if( strName == null )
			{
				// 새로운 statement 이름을 생성한다.
				int iMapCount = m_clsMap.size( );
				strName = "" + iMapCount;
	 
				Configuration clsConfig = clsSession.getConfiguration( );
				SqlSource clsSS = new StaticSqlSource( clsConfig, strSQL );
				List<ResultMap> clsRML = new ArrayList<ResultMap>();
				clsRML.add( new ResultMap.Builder( clsConfig, "", resultType, new ArrayList<ResultMapping>() ).build( ) );
				MappedStatement clsMs = new MappedStatement.Builder( clsConfig, strName, clsSS, SqlCommandType.SELECT ).resultMaps( clsRML ).build( );
				clsConfig.addMappedStatement( clsMs );
	   
				m_clsMap.put( strSQL, strName );
			}
	  
			return clsSession.selectOne( strName );
		}
	}
	
	public static void main( String [] args )
	{
		TestMyBatis clsTest = new TestMyBatis();
		
		int iCount = clsTest.SelectOne( "SELECT count(*) FROM noticeboard", Integer.class );
		System.out.println( "count=" + iCount );
		
		NoticeRow clsRow = clsTest.SelectOne( "SELECT nbSubject AS m_strSubject, nbContent AS m_strContent, nbReadCount AS m_iReadCount, nbInsertDate AS m_clsInsertDate, nbCommentCount AS m_iCommentCount FROM noticeboard WHERE nbId = 1", NoticeRow.class );
		System.out.println( clsRow.toString() );
	}
}
