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
