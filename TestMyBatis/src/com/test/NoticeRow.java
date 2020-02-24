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
