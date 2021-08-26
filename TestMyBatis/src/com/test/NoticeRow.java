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

import java.util.Date;

public class NoticeRow
{
	int m_iId;
	String m_strSubject;
	String m_strContent;
	int m_iReadCount;
	Date m_clsInsertDate;
	int m_iCommentCount;
	
	public String toString()
	{
		return "subject(" + m_strSubject + ") content(" + m_strContent + ") readCount(" + m_iReadCount + ") insertDate(" + m_clsInsertDate + ") commentCount(" + m_iCommentCount + ")";
	}
}
