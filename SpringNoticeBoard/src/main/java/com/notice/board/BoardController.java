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

import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.notice.db.NoticeBoardRow;
import com.notice.db.NoticeBoardSql;

/** 게시판 컨트롤러
 * @author 이영한 ( http://blog.naver.com/websearch )
 */
@Controller
public class BoardController
{
	@Inject
	SqlSession m_clsSession;
	
	@Inject
	Setup m_clsSetup;
	
	private static Logger LOG = LoggerFactory.getLogger( BoardController.class );
	
	@PostConstruct
	public void Init()
	{
		m_clsSession.getConfiguration( ).addMapper( NoticeBoardSql.class );
	}
	
	/** 게시판 리스트 보여주기
	 * @param model view 로 전달할 객체
	 * @return 게시판 리스트 JSP 파일 이름
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list( Model model )
	{
		List<NoticeBoardRow> clsList = m_clsSession.selectList( "SelectList" );
		
		model.addAttribute( "list", clsList );
		
		return "list";
	}
	
	/** 게시글 추가 화면 보여주기
	 * @param model	view 로 전달할 객체
	 * @return 게시글 추가 JSP 파일 이름
	 */
	@RequestMapping(value = "insert", method = RequestMethod.GET)
	public String insert( Model model )
	{
		return "insert";
	}
	
	/** 게시글 저장하기
	 * @param strSubject	제목
	 * @param strContent	내용
	 * @param model			view 로 전달할 객체
	 * @return 성공하면 게시글 리스트 JSP 파일로 redirect 하고 그렇지 않으면 게시글 추가 JSP 파일을 리턴한다.
	 */
	@RequestMapping(value = "insert", method = RequestMethod.POST)
	public String insertAction( @RequestParam("subject") String strSubject, @RequestParam("content") String strContent, Model model )
	{
		m_clsSession.insert( "Insert", new NoticeBoardRow( strSubject, strContent ) );
		
		return "redirect:list";
	}
	
	/** 게시글 수정 화면 보여주기
	 * @param iId		게시글 아이디
	 * @param model	view 로 전달할 객체
	 * @return 게시글 수정 JSP 파일 이름
	 */
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String update( @RequestParam("id") int iId, Model model )
	{
		NoticeBoardRow clsRow = m_clsSession.selectOne( "Select", iId );
		model.addAttribute( "row", clsRow );
		
		return "update";
	}
		
	/** 게시글 수정하기
	 * @param strSubject	제목
	 * @param strContent	내용
	 * @param model			view 로 전달할 객체
	 * @return 성공하면 게시글 리스트 JSP 파일로 redirect 하고 그렇지 않으면 게시글 추가 JSP 파일을 리턴한다.
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String updateAction( @RequestParam("id") int iId, @RequestParam("subject") String strSubject, @RequestParam("content") String strContent, Model model )
	{
		m_clsSession.update( "Update", new NoticeBoardRow( iId, strSubject, strContent ) );
		
		return "redirect:list";
	}
	
	/** 게시글 삭제하기
	 * @param iId		게시글 아이디
	 * @return 게시글 수정 JSP 파일 이름
	 */
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String delete( @RequestParam("id") int iId )
	{
		m_clsSession.delete( "Delete", iId );
		
		return "redirect:list";
	}
	
	/** 게시글 상세 보여주기
	 * @param iId		게시글 아이디
	 * @param model	view 로 전달할 객체
	 * @return 게시글 상세 보여주기 JSP 파일 이름
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

	// Model 객체에 저장된 값들이 HttpServletRequest 객체에 저장되는 기능을 확인한다.
	@RequestMapping(value = "update_test", method = RequestMethod.GET)
	public String updateTest( @RequestParam("id") int iId, Model model, HttpServletRequest clsRequest )
	{
		NoticeBoardRow clsRow = m_clsSession.selectOne( "Select", iId );
		model.addAttribute( "row", clsRow );
		
		NoticeBoardRow clsOutput = (NoticeBoardRow)clsRequest.getAttribute( "row" );
		if( clsOutput != null )
		{
			LOG.info( "row is found" );
		}
		else
		{
			LOG.info( "row is not found" );
		}
		
		LOG.info( "### Controller ###" );
		LOG.info( "Request = " + clsRequest );
		
		Enumeration<?> arrName = clsRequest.getAttributeNames( );
		
		while( arrName.hasMoreElements() )
		{
			String strName = (String)arrName.nextElement( );
			LOG.info( "[" + strName + "]" );
		}
		
		return "update_test";
	}

	// @ModelAttribute 기능을 확인한다.
	static class UpdateData
	{
		int id;
		String subject;
		String content;
		
		public void setId( int id )
		{
			this.id = id;
		}
		public void setSubject( String subject )
		{
			this.subject = subject;
		}
		public void setContent( String content )
		{
			this.content = content;
		}
	}
	
	@RequestMapping(value = "update_test", method = RequestMethod.POST)
	public String updateTestAction( @ModelAttribute UpdateData clsInput )
	{
		m_clsSession.update( "Update", new NoticeBoardRow( clsInput.id, clsInput.subject, clsInput.content ) );
		
		return "redirect:list";
	}
	
	// @RequestParam Map<String,String> clsMap 테스트
	@RequestMapping(value = "update_test2", method = RequestMethod.GET)
	public String updateTest2( @RequestParam("id") int iId, Model model )
	{
		NoticeBoardRow clsRow = m_clsSession.selectOne( "Select", iId );
		model.addAttribute( "row", clsRow );
		
		// ResourceBundle 로 설정 파일 읽기 테스트
		ResourceBundle clsBundle = ResourceBundle.getBundle( "properties.env", java.util.Locale.getDefault() );
		String strTest = clsBundle.getString( "TEST" );
		
		System.out.println( "ResourceBundle => strTest[" + strTest + "]" );
		
		// Setup bean 으로 설정 파일 읽기 테스트
		strTest = m_clsSetup.Get( "TEST" );
		
		System.out.println( "Setup => strTest[" + strTest + "]" );
		System.out.println( "Setup member => strTest[" + m_clsSetup.m_strTest + "]" );
		System.out.println( "Setup member => iIntTest[" + m_clsSetup.m_iIntTest + "]" );
		System.out.println( "Setup member => bBoolTest[" + m_clsSetup.m_bBoolTest + "]" );
		System.out.println( "Setup member => bBoolTest2[" + m_clsSetup.m_bBoolTest2 + "]" );
		System.out.println( "Setup member => strNull[" + m_clsSetup.m_strNull + "]" );
		System.out.println( "Setup member => iIntNull[" + m_clsSetup.m_iIntNull + "]" );
		System.out.println( "Setup member => bBoolNull[" + m_clsSetup.m_bBoolNull + "]" );
				
		return "update_test2";
	}
	
	@RequestMapping(value = "update_test2", method = RequestMethod.POST)
	public String updateTest2Action( @RequestParam Map<String,String> clsMap )
	{
		m_clsSession.update( "Update", new NoticeBoardRow( Integer.parseInt( clsMap.get( "id" ) ), clsMap.get( "subject" ), clsMap.get( "content" ) ) );
		
		return "redirect:list";
	}
}
