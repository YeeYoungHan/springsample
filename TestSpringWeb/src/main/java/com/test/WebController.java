package com.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController 
{
	@RequestMapping(value="/showMessage")
	public String showMessage( Model model )
	{
		model.addAttribute( "message", "test" );
		return "showMessage";
	}
}
