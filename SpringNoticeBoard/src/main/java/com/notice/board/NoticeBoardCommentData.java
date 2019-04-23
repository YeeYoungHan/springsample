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

/** 댓글 저장 요청 정보를 저장하는 클래스
 *  NoticeBoardCommentRow 클래스을 사용할 수도 있지만 NoticeBoardCommentRow 클래스를 사용할 경우, NoticeBoardCommentRow 기본 생성자가 필요하고 NoticeBoardCommentRow 기본 생성자가 존재하면 MyBatis 에서 
 *  댓글 리스트를 가져올 때에 기본 생성자 때문에 정상적으로 동작하지 않으므로 MyBatis 에서 댓글 리스트를 정상적으로 가져오기 위해서 새로운 클래스를 생성하였다.
 * @author 이영한 ( http://blog.naver.com/websearch )
 */
public class NoticeBoardCommentData
{
	public long m_iParentId;
	public String m_strComment;
	
	public void setParentId( int iParentId )
	{
		m_iParentId = iParentId;
	}
	
	public void setComment( String strComment )
	{
		m_strComment = strComment;
	}
}
