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

package com.notice.login;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/** 사용자 로그인 테스트
 * 
 * http://localhost:8080/board/html_a 로 연결하면 http://localhost:8080/board/login 페이지가 보여지고 로그인할 수 있다.
 * 로그인에 성공하면 http://localhost:8080/board/html_a 에 접근할 수 있다.
 * 
 * @author 이영한 ( http://blog.naver.com/websearch )
 */
@Controller
public class LoginController
{
	/*
	@Inject
	Provider<LoginUser> m_clsUser;
	*/
	
	@Inject
	LoginUserProvider m_clsUserProvider;
	
	private static Logger LOG = LoggerFactory.getLogger( LoginController.class );
	
	/** 로그인 화면을 보여준다. 
	 * @return 로그인 화면 JSP 페이지 이름
	 */
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login( )
	{
		return "login";
	}
	
	/** 로그인 입력값을 분석하여서 로그인 성공/실패 처리한다. 
	 * @return 로그인 성공하면 최초 보여줄 JSP 페이지 이름을 리턴하고 그렇지 않으면 로그인 화면 JSP 페이지 이름을 리턴한다.
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login( @RequestParam("id") String strUserId, @RequestParam("pw") String strPassWord, HttpSession clsSession )
	{
		if( strUserId.equals( "admin" ) )
		{
			// 로그인 성공 => HTTP 세션에 로그인 정보를 저장한다.
			clsSession.setAttribute( LoginInterceptor.LOGIN, "ok" );
			clsSession.setMaxInactiveInterval( 30 );
						
			m_clsUserProvider.GetUser( ).m_strPassWord = strPassWord;
			
			return "redirect:html_a";
		}
		
		return "login";
	}
	
	@RequestMapping(value = "html_a", method = RequestMethod.GET)
	public String html_a( )
	{
		LOG.error( "password[" + m_clsUserProvider.GetUser( ).m_strPassWord + "]" );
		
		return "html_a";
	}
	
	@RequestMapping(value = "html_b", method = RequestMethod.GET)
	public String html_b( )
	{
		return "html_b";
	}
}
