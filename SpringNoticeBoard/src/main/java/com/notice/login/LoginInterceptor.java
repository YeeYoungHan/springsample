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
