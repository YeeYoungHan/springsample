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

package com.test.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.PropertyNamingStrategyBase;

@Controller
public class JsonStrategyController
{

	@ResponseBody
	@RequestMapping(value = "/put_user3", method = RequestMethod.POST)
	public String PutUser( @RequestBody User3 clsUser )
	{
		String strHtml = "name = " + clsUser.m_strName + ", value = " + clsUser.m_iValue;
		
		return strHtml;
	}
	
	@ResponseBody
	@RequestMapping(value = "/get_user3", method = RequestMethod.GET)
	public User3 GetUser( )
	{
		User3 clsUser = new User3();
		
		clsUser.m_strName = "name@1";
		clsUser.m_iValue = 1234;
		
		return clsUser;
	}
	
	@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
	static class User3
	{
		public String m_strName;
		public int m_iValue;
	}

	@ResponseBody
	@RequestMapping(value = "/put_user4", method = RequestMethod.POST)
	public String PutUser4( @RequestBody User4 clsUser )
	{
		String strHtml = "name = " + clsUser.m_strName + ", value = " + clsUser.m_iValue;
		
		return strHtml;
	}
	
	@ResponseBody
	@RequestMapping(value = "/get_user4", method = RequestMethod.GET)
	public User4 GetUser4( )
	{
		User4 clsUser = new User4();
		
		clsUser.m_strName = "name@1";
		clsUser.m_iValue = 1234;
		
		return clsUser;
	}
	
	@JsonNaming(MyClassStrategy.class)
	static class User4
	{
		public String m_strName;
		public int m_iValue;
	}
	
	public static class MyClassStrategy extends PropertyNamingStrategyBase
  {
		private static final long serialVersionUID = 1L;

		@Override
    public String translate( String strInput )
    {
    	if( strInput.startsWith( "m_" ) )
    	{
    		return strInput.substring( 2 );
    	}
    	
    	return strInput;
    }
  }
}
