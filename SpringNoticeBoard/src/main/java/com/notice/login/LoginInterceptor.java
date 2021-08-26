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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/** 로그인 상태에서만 볼 수 있는 페이지에 로그인되지 않은 상태에서 접근하면 로그인 화면을 보여준다.
 * 
 * @author 이영한 ( http://blog.naver.com/websearch )
 */
public class LoginInterceptor extends HandlerInterceptorAdapter
{
	public static final String LOGIN = "login";

	@Override
	public boolean preHandle( HttpServletRequest request, 	HttpServletResponse response, Object handler ) throws Exception
	{
		String strUrl = request.getRequestURI( );
		
		// 로그인 페이지가 아니고 로그인 세션이 저장되어 있지 않으면 로그인 페이지를 보여준다.
		if( strUrl.equals( "login" ) == false )
		{
			HttpSession clsSession = request.getSession( );
			
			if( clsSession.getAttribute( LOGIN ) == null )
			{
				response.sendRedirect( "login" );
				return false;
			}
		}
		
		return super.preHandle( request, response, handler );
	}
}
