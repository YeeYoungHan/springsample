package com.test;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
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
	
	@Select({"SELECT nbSubject AS m_strSubject, nbContent AS m_strContent, nbReadCount AS m_iReadCount, nbInsertDate AS m_clsInsertDate, nbCommentCount AS m_iCommentCount FROM noticeboard"})
	List<NoticeRow> SelectRowList( );
	
	@Insert({"INSERT INTO noticeboard( nbSubject, nbContent, nbReadCount, nbInsertDate, nbCommentCount ) VALUES( #{m_strSubject}, #{m_strContent}, #{m_iReadCount}, #{m_clsInsertDate}, #{m_iCommentCount} )"})
	int InsertRow( NoticeRow clsRow );
	
	@Update({"UPDATE noticeboard SET nbContent = #{m_strContent} WHERE nbId = #{m_iId}"})
	int UpdateContent( NoticeRow clsRow );
	
	@Delete({"DELETE FROM noticeboard WHERE nbId = #{iId}"})
	int DeleteRow( int iId );
}
