package com.notice.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/** �α��� ���¿����� �� �� �ִ� �������� �α��ε��� ���� ���¿��� �����ϸ� �α��� ȭ���� �����ش�.
 * 
 * @author �̿��� ( http://blog.naver.com/websearch )
 */
public class LoginInterceptor extends HandlerInterceptorAdapter
{
	public static final String LOGIN = "login";

	@Override
	public boolean preHandle( HttpServletRequest request, 	HttpServletResponse response, Object handler ) throws Exception
	{
		String strUrl = request.getRequestURI( );
		
		// �α��� �������� �ƴϰ� �α��� ������ ����Ǿ� ���� ������ �α��� �������� �����ش�.
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
