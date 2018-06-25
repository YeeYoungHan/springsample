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

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.notice.db.NoticeBoardRow;
import com.notice.db.NoticeBoardSql;

/** �Խ��� ��Ʈ�ѷ�
 * @author �̿��� ( http://blog.naver.com/websearch )
 */
@Controller
public class BoardController
{
	@Inject
	SqlSession m_clsSession;
	
	@PostConstruct
	public void Init()
	{
		m_clsSession.getConfiguration( ).addMapper( NoticeBoardSql.class );
	}
	
	/** �Խ��� ����Ʈ �����ֱ�
	 * @param model view �� ������ ��ü
	 * @return �Խ��� ����Ʈ JSP ���� �̸�
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list( Model model )
	{
		List<NoticeBoardRow> clsList = m_clsSession.selectList( "SelectList" );
		
		model.addAttribute( "list", clsList );
		
		return "list";
	}
	
	/** �Խñ� �߰� ȭ�� �����ֱ�
	 * @param model	view �� ������ ��ü
	 * @return �Խñ� �߰� JSP ���� �̸�
	 */
	@RequestMapping(value = "insert", method = RequestMethod.GET)
	public String insert( Model model )
	{
		return "insert";
	}
	
	/** �Խñ� �����ϱ�
	 * @param strSubject	����
	 * @param strContent	����
	 * @param model			view �� ������ ��ü
	 * @return �����ϸ� �Խñ� ����Ʈ JSP ���Ϸ� redirect �ϰ� �׷��� ������ �Խñ� �߰� JSP ������ �����Ѵ�.
	 */
	@RequestMapping(value = "insert", method = RequestMethod.POST)
	public String insertAction( @RequestParam("subject") String strSubject, @RequestParam("content") String strContent, Model model )
	{
		m_clsSession.insert( "Insert", new NoticeBoardRow( strSubject, strContent ) );
		
		return "redirect:list";
	}
	
	/** �Խñ� ���� ȭ�� �����ֱ�
	 * @param iId		�Խñ� ���̵�
	 * @param model	view �� ������ ��ü
	 * @return �Խñ� ���� JSP ���� �̸�
	 */
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String update( @RequestParam("id") int iId, Model model )
	{
		NoticeBoardRow clsRow = m_clsSession.selectOne( "Select", iId );
		model.addAttribute( "row", clsRow );
		
		return "update";
	}
	
	/** �Խñ� �����ϱ�
	 * @param strSubject	����
	 * @param strContent	����
	 * @param model			view �� ������ ��ü
	 * @return �����ϸ� �Խñ� ����Ʈ JSP ���Ϸ� redirect �ϰ� �׷��� ������ �Խñ� �߰� JSP ������ �����Ѵ�.
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String updateAction( @RequestParam("id") int iId, @RequestParam("subject") String strSubject, @RequestParam("content") String strContent, Model model )
	{
		m_clsSession.update( "Update", new NoticeBoardRow( iId, strSubject, strContent ) );
		
		return "redirect:list";
	}
	
	/** �Խñ� �����ϱ�
	 * @param iId		�Խñ� ���̵�
	 * @return �Խñ� ���� JSP ���� �̸�
	 */
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String delete( @RequestParam("id") int iId )
	{
		m_clsSession.delete( "Delete", iId );
		
		return "redirect:list";
	}
	
	/** �Խñ� �� �����ֱ�
	 * @param iId		�Խñ� ���̵�
	 * @param model	view �� ������ ��ü
	 * @return �Խñ� �� �����ֱ� JSP ���� �̸�
	 */
	@RequestMapping(value = "select", method = RequestMethod.GET)
	public String select( @RequestParam("id") int iId, Model model )
	{
		NoticeBoardRow clsRow = m_clsSession.selectOne( "Select", iId );
		
		if( clsRow != null )
		{
			m_clsSession.update( "UpdateReadCount", iId );
			model.addAttribute( "row", clsRow );
		}
		
		return "select";
	}
}
