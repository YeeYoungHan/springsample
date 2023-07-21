package com.test.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonProperty;

@Controller
public class JsonPropertyController
{
	
	@ResponseBody
	@RequestMapping(value = "/put_user2", method = RequestMethod.POST)
	public String PutUser2( @RequestBody User2 clsUser )
	{
		String strHtml = "name = " + clsUser.m_strName + ", value = " + clsUser.m_iValue;
		
		return strHtml;
	}
	
	@ResponseBody
	@RequestMapping(value = "/get_user2", method = RequestMethod.GET)
	public User2 GetUser2( )
	{
		User2 clsUser = new User2();
		
		clsUser.m_strName = "name@1";
		clsUser.m_iValue = 1234;
		
		return clsUser;
	}
	
	static class User2
	{
		@JsonProperty("name")
		public String m_strName;
		
		@JsonProperty("value")
		public int m_iValue;
	}

}
