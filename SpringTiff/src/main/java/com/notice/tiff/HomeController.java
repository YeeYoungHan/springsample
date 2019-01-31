package com.notice.tiff;

import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.IndexColorModel;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Base64;
import java.util.Locale;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "home";
	}
	
	@RequestMapping(value = "home", method = RequestMethod.GET)
	public String home2(Locale locale, Model model) {
		return "home";
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
		    		    
		    ImageIO.write( clsOutput, "tiff", new File("c:\\temp\\1.tiff") );
		    
		    Runtime.getRuntime().exec("c:\\temp\\TiffSetPage.exe C:\\OpenProject\\SpringSample\\trunk\\SpringTiff\\src\\main\\webapp\\resources\\img\\5.tiff c:\\temp\\1.tiff " + iPage );
		}
		catch( Exception e )
		{
			
		}
		
		return "redirect:home";
	}
}
