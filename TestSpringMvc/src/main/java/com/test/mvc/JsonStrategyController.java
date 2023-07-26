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
import java.util.HashMap;

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
	
	/* 아래와 같이 응답한다.
	 * {"strName":"name@1","iValue":1234,"arrInt":null,"arrString":null,"lstInt":null,"lstString":null}
	 */
	@ResponseBody
	@RequestMapping(value = "/get_user4", method = RequestMethod.GET)
	public User4 GetUser4( )
	{
		User4 clsUser = new User4();
		
		clsUser.m_strName = "name@1";
		clsUser.m_iValue = 1234;
		
		return clsUser;
	}
	
	/* 아래와 같이 응답한다.
	 * {"strName":"name@1","iValue":1234,"arrInt":[0,0,0],"arrString":[null,null,null],"lstInt":[],"lstString":[]}
	 */
	@ResponseBody
	@RequestMapping(value = "/get_user41", method = RequestMethod.GET)
	public User4 GetUser41( )
	{
		User4 clsUser = new User4();
		
		clsUser.m_strName = "name@1";
		clsUser.m_iValue = 1234;
		clsUser.m_arrInt = new int[3];
		clsUser.m_arrString = new String[3];
		clsUser.m_lstInt = new ArrayList<Integer>();
		clsUser.m_lstString = new ArrayList<String>();
		
		return clsUser;
	}
	
	/* 아래와 같이 응답한다.
	 * {"strName":"name@1","iValue":1234,"arrInt":[2,0,0],"arrString":["first",null,null],"lstInt":[3],"lstString":["first-list"]}
	 */
	@ResponseBody
	@RequestMapping(value = "/get_user42", method = RequestMethod.GET)
	public User4 GetUser42( )
	{
		User4 clsUser = new User4();
		
		clsUser.m_strName = "name@1";
		clsUser.m_iValue = 1234;
		clsUser.m_arrInt = new int[3];
		clsUser.m_arrString = new String[3];
		clsUser.m_lstInt = new ArrayList<Integer>();
		clsUser.m_lstString = new ArrayList<String>();
		
		clsUser.m_arrInt[0] = 2;
		clsUser.m_arrString[0] = "first";
		clsUser.m_lstInt.add( 3 );
		clsUser.m_lstString.add( "first-list" );
		
		return clsUser;
	}

	/* 아래와 같이 응답한다.
	 * {"mapInt":null,"mapString":null}
	 */
	@ResponseBody
	@RequestMapping(value = "/get_user5", method = RequestMethod.GET)
	public User5 GetUser5( )
	{
		User5 clsUser = new User5();
		
		return clsUser;
	}
	
	/* 아래와 같이 응답한다.
	 * {"mapInt":{},"mapString":{}}
	 */
	@ResponseBody
	@RequestMapping(value = "/get_user51", method = RequestMethod.GET)
	public User5 GetUser51( )
	{
		User5 clsUser = new User5();
		
		clsUser.m_mapInt = new HashMap<Integer,Integer>();
		clsUser.m_mapString = new HashMap<String,String>();
		
		return clsUser;
	}
	
	/* 아래와 같이 응답한다.
	 * {"mapInt":{"1":2},"mapString":{"key":"value"}}
	 */
	@ResponseBody
	@RequestMapping(value = "/get_user52", method = RequestMethod.GET)
	public User5 GetUser52( )
	{
		User5 clsUser = new User5();
		
		clsUser.m_mapInt = new HashMap<Integer,Integer>();
		clsUser.m_mapString = new HashMap<String,String>();
		
		clsUser.m_mapInt.put( 1, 2 );
		clsUser.m_mapString.put( "key", "value" );
		
		return clsUser;
	}
	
	/* 아래와 같이 응답한다.
	 * {"arrUser":null}
	 */
	@ResponseBody
	@RequestMapping(value = "/get_user6", method = RequestMethod.GET)
	public User6 GetUser6( )
	{
		User6 clsUser = new User6();
		
		return clsUser;
	}
	
	/* 아래와 같이 응답한다.
	 * {"arrUser":[]}
	 */
	@ResponseBody
	@RequestMapping(value = "/get_user61", method = RequestMethod.GET)
	public User6 GetUser61( )
	{
		User6 clsUser = new User6();
		
		clsUser.m_arrUser = new ArrayList<User4>();
		
		return clsUser;
	}
	
	/* 아래와 같이 응답한다.
	 * {"arrUser":[{"strName":"name","iValue":1,"arrInt":null,"arrString":null,"lstInt":null,"lstString":null}]}
	 */
	@ResponseBody
	@RequestMapping(value = "/get_user62", method = RequestMethod.GET)
	public User6 GetUser62( )
	{
		User6 clsUser = new User6();
		
		clsUser.m_arrUser = new ArrayList<User4>();
		
		User4 clsTemp = new User4();
		
		clsTemp.m_strName = "name";
		clsTemp.m_iValue = 1;
		
		clsUser.m_arrUser.add( clsTemp );
		
		return clsUser;
	}
	
	@JsonNaming(MyClassStrategy.class)
	static class User4
	{
		public String m_strName;
		public int m_iValue;
		public int [] m_arrInt;
		public String [] m_arrString;
		public ArrayList<Integer> m_lstInt;
		public ArrayList<String> m_lstString;
	}
	
	@JsonNaming(MyClassStrategy.class)
	static class User5
	{
		public HashMap<Integer,Integer> m_mapInt;
		public HashMap<String,String> m_mapString;
	}
	
	@JsonNaming(MyClassStrategy.class)
	static class User6
	{
		public ArrayList<User4> m_arrUser;
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
