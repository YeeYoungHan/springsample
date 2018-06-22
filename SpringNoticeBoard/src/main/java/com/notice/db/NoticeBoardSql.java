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

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

/** annotation 기반 MyBatis 기능을 이용한 테이블 INSERT, SELECT 등의 기능을 수행하는 인터페이스 
 * @author 이영한 ( http://blog.naver.com/websearch )
 *
 */
public interface NoticeBoardSql
{
	@Insert("INSERT INTO NoticeBoard( nbSubject, nbContent, nbInsertDate ) VALUES( #{m_strSubject}, #{m_strContent}, now() )")
	public void Insert( NoticeBoardRow clsRow );
	
	@Update("UPDATE NoticeBoard SET nbSubject = #{m_strSubject}, nbContent = #{m_strContent} WHERE nbId = #{m_iId}" )
	public void Update( NoticeBoardRow clsRow );
	
	@Update("UPDATE NoticeBoard SET nbReadCount = nbReadCount + 1 WHERE nbId = #{iId}")
	public void UpdateReadCount( int iId );
	
	@Delete("DELETE FROM NoticeBoard WHERE nbId = #{iId}")
	public void Delete( int iId );
	
	@Select("SELECT nbId, nbSubject, nbInsertDate, nbReadCount FROM NoticeBoard ORDER BY nbId DESC")
	public List<NoticeBoardRow> SelectList( int iPage );
	
	@Select("SELECT nbId, nbSubject, nbContent, nbInsertDate FROM NoticeBoard WHERE nbId = #{iId}")
	public List<NoticeBoardRow> Select( int iId );
}
