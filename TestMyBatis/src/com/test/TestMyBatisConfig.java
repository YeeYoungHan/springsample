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

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/** MyBatis 설정 파일 테스트 클래스
 * 
 * @author 이영한 ( http://blog.naver.com/websearch )
 *
 */
public class TestMyBatisConfig
{
	String m_strConfigPath = "com/test/ConfigProperties.xml";
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
			return clsSession.selectOne( "com.test.TestMyBatis.SelectSubject", iId );
		}
	}
	
	public static void main( String [] args )
	{
		TestMyBatisConfig clsTest = new TestMyBatisConfig();

		String strName = clsTest.SelectSubject( 3 );
		System.out.println( "SelectName(3) => (" + strName + ")" );
		
		strName = clsTest.SelectSubject( 3 );
		System.out.println( "SelectName(3) => (" + strName + ")" );
	}
}
