package com.test.mvc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JsonController
{
	@ResponseBody
	@RequestMapping(value = "/get_user", method = RequestMethod.GET)
	public User GetUser()
	{
		User clsUser = new User( "Kim", "Test ");
				
		return clsUser;
	}
	
	@ResponseBody
	@RequestMapping(value = "/get_user_list", method = RequestMethod.GET)
	public List<User> GetUserList()
	{
		List<User> arrList = new ArrayList<User>();
		
		arrList.add( new User( "Kim1", "Test1" ) );
		arrList.add( new User( "Kim2", "Test2" ) );
		
		return arrList;
	}
	
	@ResponseBody
	@RequestMapping(value = "/put_user", method = RequestMethod.POST)
	public String PutUser( @RequestBody User clsUser )
	{
		String strHtml = "name = " + clsUser.m_strName + ", value = " + clsUser.m_strValue;
		
		return strHtml;
	}
	
	static class User
	{
		public String m_strName;
		public String m_strValue;
		
		public User()
		{
		}
		
		public User( String strName, String strValue )
		{
			m_strName = strName;
			m_strValue = strValue;
		}
	}
}
