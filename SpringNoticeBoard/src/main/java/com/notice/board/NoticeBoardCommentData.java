package com.notice.board;

/** ��� ���� ��û ������ �����ϴ� Ŭ����
 *  NoticeBoardCommentRow Ŭ������ ����� ���� ������ NoticeBoardCommentRow Ŭ������ ����� ���, NoticeBoardCommentRow �⺻ �����ڰ� �ʿ��ϰ� NoticeBoardCommentRow �⺻ �����ڰ� �����ϸ� MyBatis ���� 
 *  ��� ����Ʈ�� ������ ���� �⺻ ������ ������ ���������� �������� �����Ƿ� MyBatis ���� ��� ����Ʈ�� ���������� �������� ���ؼ� ���ο� Ŭ������ �����Ͽ���.
 * @author �̿��� ( http://blog.naver.com/websearch )
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
