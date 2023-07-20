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

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Inject
	Setup m_clsSetup;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance( DateFormat.LONG, DateFormat.LONG );
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		System.out.println( "Setup member => strTest[" + m_clsSetup.m_strTest + "]" );
		System.out.println( "Setup member => strNull[" + m_clsSetup.m_strNull + "]" );
		System.out.println( "Setup member => iIntTest[" + m_clsSetup.m_iIntTest + "]" );
		System.out.println( "Setup member => bBoolTest[" + m_clsSetup.m_bBoolTest + "]" );
		System.out.println( "Setup member => bBoolTest2[" + m_clsSetup.m_bBoolTest2 + "]" );
		System.out.println( "Setup member => iIntNull[" + m_clsSetup.m_iIntNull + "]" );
		System.out.println( "Setup member => bBoolNull[" + m_clsSetup.m_bBoolNull + "]" );
		
		return "home";
	}
	
	@RequestMapping(value = "/get_user", method = RequestMethod.GET)
	@ResponseBody
	public User GetUser()
	{
		User clsUser = new User();
		
		clsUser.m_strName = "Kim";
		clsUser.m_strValue = "Test";
		
		return clsUser;
	}
	
	class User
	{
		public String m_strName;
		public String m_strValue;
	}
}
