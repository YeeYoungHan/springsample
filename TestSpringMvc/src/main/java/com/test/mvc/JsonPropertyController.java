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
