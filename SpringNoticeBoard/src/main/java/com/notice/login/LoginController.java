package com.notice.login;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/** ����� �α��� �׽�Ʈ
 * 
 * http://localhost:8080/board/html_a �� �����ϸ� http://localhost:8080/board/login �������� �������� �α����� �� �ִ�.
 * �α��ο� �����ϸ� http://localhost:8080/board/html_a �� ������ �� �ִ�.
 * 
 * @author �̿��� ( http://blog.naver.com/websearch )
 */
@Controller
public class LoginController
{
	/** �α��� ȭ���� �����ش�. 
	 * @return �α��� ȭ�� JSP ������ �̸�
	 */
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login( )
	{
		return "login";
	}
	
	/** �α��� �Է°��� �м��Ͽ��� �α��� ����/���� ó���Ѵ�. 
	 * @return �α��� �����ϸ� ���� ������ JSP ������ �̸��� �����ϰ� �׷��� ������ �α��� ȭ�� JSP ������ �̸��� �����Ѵ�.
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login( @RequestParam("id") String strUserId, @RequestParam("pw") String strPassWord, HttpSession clsSession )
	{
		if( strUserId.equals( "admin" ) && strPassWord.equals( "spring" ) )
		{
			// �α��� ���� => HTTP ���ǿ� �α��� ������ �����Ѵ�.
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
