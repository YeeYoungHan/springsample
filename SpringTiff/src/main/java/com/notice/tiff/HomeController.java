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

package com.notice.tiff;

import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.IndexColorModel;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "home";
	}
	
	public boolean IsWindow()
	{
		String strOS = System.getProperty("os.name");
		if( strOS.startsWith("Windows") )
		{
			return true;
		}
		
		return false;
	}
	
	@RequestMapping(value = "get_img", method = RequestMethod.GET)
	public @ResponseBody byte[] GetImage( final HttpServletResponse response ) throws IOException
	{
		response.setHeader("Cache-Control", "no-cache");
		
		String strFileName = "c:\\temp\\5.tiff";
		
		if( IsWindow() == false )
	    {
			strFileName = "/tmp/5.tiff";
	    }
		
		FileInputStream in = new FileInputStream( new File(strFileName));
		return IOUtils.toByteArray( in );
	}
	
	@RequestMapping(value = "modify_tiff", method = RequestMethod.POST)
	public String modifyTiff( @RequestParam("data") String strData, @RequestParam("page") int iPage, Model model)
	{
		try
		{
			byte [] arrInput = strData.substring(22).getBytes();
			Base64.Decoder clsDecoder = Base64.getDecoder();
			byte [] arrOutput = clsDecoder.decode(arrInput);
			
			final BufferedImage clsInput = ImageIO.read(new ByteArrayInputStream(arrOutput));
			
			IndexColorModel clsICM = new IndexColorModel(1, 2, new byte[] { (byte) 0, (byte) 0xFF }, new byte[] { (byte) 0, (byte) 0xFF }, new byte[] { (byte) 0, (byte) 0xFF });
		    BufferedImage clsOutput = new BufferedImage( clsInput.getWidth(), clsInput.getHeight(), BufferedImage.TYPE_BYTE_BINARY, clsICM );
		    ColorConvertOp cco = new ColorConvertOp( clsInput.getColorModel().getColorSpace(), clsOutput.getColorModel().getColorSpace(), null );
	
		    cco.filter( clsInput, clsOutput );
		    
		    if( IsWindow() )
		    {
		    	ImageIO.write( clsOutput, "tiff", new File("c:\\temp\\1.tiff") );
			    Runtime.getRuntime().exec("c:\\temp\\TiffSetPage.exe C:\\temp\\5.tiff c:\\temp\\1.tiff " + iPage );
		    }
		    else
		    {
		    	ImageIO.write( clsOutput, "tiff", new File("/tmp/1.tiff") );
			    Runtime.getRuntime().exec("/tmp/TiffSetPage.exe /tmp/5.tiff /tmp/1.tiff " + iPage );
		    }
		}
		catch( Exception e )
		{
			
		}
		
		return "redirect:/";
	}
}
