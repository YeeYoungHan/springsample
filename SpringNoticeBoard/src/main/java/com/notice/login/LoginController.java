package com.notice.login;

import javax.servlet.http.HttpSession;

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
		if( strUserId.equals( "admin" ) && strPassWord.equals( "spring" ) )
		{
			// 로그인 성공 => HTTP 세션에 로그인 정보를 저장한다.
			clsSession.setAttribute( LoginInterceptor.LOGIN, "ok" );
			
			return "redirect:html_a";
		}
		
		return "login";
	}
	
	@RequestMapping(value = "html_a", method = RequestMethod.GET)
	public String html_a( )
	{
		return "html_a";
	}
	
	@RequestMapping(value = "html_b", method = RequestMethod.GET)
	public String html_b( )
	{
		return "html_b";
	}
}
