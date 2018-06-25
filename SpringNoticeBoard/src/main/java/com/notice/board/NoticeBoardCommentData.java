package com.notice.board;

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
