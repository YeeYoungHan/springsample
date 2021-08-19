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

package com.notice.db;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class NoticeBoardRow
{
	public long m_iId;
	public String m_strSubject;
	public String m_strContent;
	public long m_iReadCount;
	public String m_strInsertDate;
	public long m_iCommentCount;
	
	private static SimpleDateFormat m_clsDateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
	
	// INSERT 용 생성자
	public NoticeBoardRow( String strSubject, String strContent )
	{
		m_strSubject = strSubject;
		m_strContent = strContent;
	}
	
	// UPDATE 용 생성자
	public NoticeBoardRow( long iId, String strSubject, String strContent )
	{
		m_iId = iId;
		m_strSubject = strSubject;
		m_strContent = strContent;
	}
	
	// SELECT LIST 용 생성자
	public NoticeBoardRow( long iId, String strSubject, Timestamp clsInsertDate, long iReadCount, long iCommentCount )
	{
		m_iId = iId;
		m_strSubject = strSubject;
		m_strInsertDate = m_clsDateFormat.format( clsInsertDate );
		m_iReadCount = iReadCount;
		m_iCommentCount = iCommentCount;
	}
	
	// SELECT 용 생성자
	public NoticeBoardRow( long iId, String strSubject, String strContent, Timestamp clsInsertDate )
	{
		m_iId = iId;
		m_strSubject = strSubject;
		m_strContent = strContent;
		m_strInsertDate = m_clsDateFormat.format( clsInsertDate );
	}
	
	public long getId()
	{
		return m_iId;
	}
	
	public String getSubject()
	{
		return m_strSubject;
	}
	
	public String getContent()
	{
		return m_strContent;
	}
	
	public String getInsertDate()
	{
		return m_strInsertDate;
	}
	
	public long getReadCount()
	{
		return m_iReadCount;
	}
	
	public long getCommentCount()
	{
		return m_iCommentCount;
	}
}
