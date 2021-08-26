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

public class NoticeBoardProvider
{
	String AddWhere( String strWhere, String strValue )
	{
		if( strWhere == null )
		{
			return " WHERE " + strValue;
		}
		else
		{
			return strWhere + " AND " + strValue;
		}
	}
	
	public String SelectRowCondition( NoticeRow clsCondition )
	{
		String strSQL = "SELECT nbSubject AS m_strSubject, nbContent AS m_strContent, nbReadCount AS m_iReadCount, nbInsertDate AS m_clsInsertDate, nbCommentCount AS m_iCommentCount FROM noticeboard";
		String strWhere = null;
		
		if( clsCondition.m_iId > 0 )
		{
			strWhere = AddWhere( strWhere, "nbId = #{m_iId}");
		}
		
		if( clsCondition.m_strSubject != null )
		{
			strWhere = AddWhere( strWhere, "nbSubject = #{m_strSubject}");
		}
		
		if( strWhere != null )
		{
			strSQL += strWhere;
		}
		
		return strSQL;
	}
}
