package com.notice.login;

import org.springframework.context.annotation.Scope;
//import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope( value = WebApplicationContext.SCOPE_SESSION )
public class LoginUser
{
	public String m_strPassWord;
}
