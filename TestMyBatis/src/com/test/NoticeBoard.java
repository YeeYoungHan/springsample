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

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

/** 애노테이션을 사용한 매핑 구문 저장 인터페이스
 * 
 * @author 이영한 ( http://blog.naver.com/websearch )
 *
 */
public interface NoticeBoard
{
	@Select({"SELECT nbSubject FROM noticeboard WHERE nbId = #{iId}"})
	String SelectSubject( int iId );
	
	@Select({"SELECT nbSubject AS m_strSubject, nbContent AS m_strContent, nbReadCount AS m_iReadCount, nbInsertDate AS m_clsInsertDate, nbCommentCount AS m_iCommentCount FROM noticeboard WHERE nbId = #{iId}"})
	NoticeRow SelectRow( int iId );
	
	@SelectProvider(type=NoticeBoardProvider.class, method="SelectRowCondition")
	NoticeRow SelectRowCondition( NoticeRow clsCondition );
	
	@Select({"SELECT nbSubject AS m_strSubject, nbContent AS m_strContent, nbReadCount AS m_iReadCount, nbInsertDate AS m_clsInsertDate, nbCommentCount AS m_iCommentCount " + 
		"FROM noticeboard " +
		"WHERE 1=1 " +
		"AND if(#{id}=0,1=1,nbId=#{id}) " +
		"AND if(#{subject} is null,1=1,nbSubject=#{subject})"
		})
	NoticeRow SelectArg( @Param("id") int iId, @Param("subject") String strSubject );
	
	@Select({"SELECT nbSubject AS m_strSubject, nbContent AS m_strContent, nbReadCount AS m_iReadCount, nbInsertDate AS m_clsInsertDate, nbCommentCount AS m_iCommentCount FROM noticeboard"})
	List<NoticeRow> SelectRowList( );
	
	@Insert({"INSERT INTO noticeboard( nbSubject, nbContent, nbReadCount, nbInsertDate, nbCommentCount ) VALUES( #{m_strSubject}, #{m_strContent}, #{m_iReadCount}, #{m_clsInsertDate}, #{m_iCommentCount} )"})
	int InsertRow( NoticeRow clsRow );
	
	@Update({"UPDATE noticeboard SET nbContent = #{m_strContent} WHERE nbId = #{m_iId}"})
	int UpdateContent( NoticeRow clsRow );
	
	@Delete({"DELETE FROM noticeboard WHERE nbId = #{iId}"})
	int DeleteRow( int iId );
}
