package com.test;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/** �ֳ����̼��� ����� ���� ���� ���� �������̽�
 * 
 * @author �̿��� ( http://blog.naver.com/websearch )
 *
 */
public interface NoticeBoard
{
	@Select({"SELECT nbSubject FROM noticeboard WHERE nbId = #{iId}"})
	String SelectSubject( int iId );
	
	@Select({"SELECT nbSubject AS m_strSubject, nbContent AS m_strContent, nbReadCount AS m_iReadCount, nbInsertDate AS m_clsInsertDate, nbCommentCount AS m_iCommentCount FROM noticeboard WHERE nbId = #{iId}"})
	NoticeRow SelectRow( int iId );
	
	@Insert({"INSERT INTO noticeboard( nbSubject, nbContent, nbReadCount, nbInsertDate, nbCommentCount ) VALUES( #{m_strSubject}, #{m_strContent}, #{m_iReadCount}, #{m_clsInsertDate}, #{m_iCommentCount} )"})
	int InsertRow( NoticeRow clsRow );
	
	@Update({"UPDATE noticeboard SET nbContent = #{m_strContent} WHERE nbId = #{m_iId}"})
	int UpdateContent( NoticeRow clsRow );
	
	@Delete({"DELETE FROM noticeboard WHERE nbId = #{iId}"})
	int DeleteRow( int iId );
}
