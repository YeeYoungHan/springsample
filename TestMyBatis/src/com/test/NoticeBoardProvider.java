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
