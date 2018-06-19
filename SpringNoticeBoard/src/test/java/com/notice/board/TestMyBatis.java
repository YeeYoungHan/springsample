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

package com.notice.board;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.notice.db.NoticeBoardRow;
import com.notice.db.NoticeBoardSql;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" } )
public class TestMyBatis
{
	@Inject
	SqlSession m_clsSession;
	
	@Before
	public void Before()
	{
		// XML 파일에 SQL 문을 저장하지 않고 annotation interface 로 SQL 문을 저장한다.
		m_clsSession.getConfiguration( ).addMapper( NoticeBoardSql.class );
		
		
	}
	
	@Test
	public void TestInsert()
	{
		Date clsDate = new Date();
		SimpleDateFormat clsFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String strDate = clsFormat.format(clsDate);
		
		int iRet = m_clsSession.insert( "Insert", new NoticeBoardRow( "subject-" + strDate, "content-" + strDate ) );
		System.out.println( "ret = " + iRet );
	}
}
