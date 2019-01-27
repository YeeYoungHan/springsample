package com.notice.board;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TiffConvertController
{
	BufferedImage CreateBufferedImage( Image clsImage )
	{
		BufferedImage clsBI = new BufferedImage( clsImage.getWidth( null ), clsImage.getHeight( null ), BufferedImage.TYPE_INT_RGB );
		Graphics2D cls2D = clsBI.createGraphics( );
		cls2D.drawImage( clsImage, 0, 0, null );
		cls2D.dispose( );
		
		return clsBI;
	}
	
	@RequestMapping(value = "tiff_convert", method = RequestMethod.GET)
	public String TiffConvert()
	{
		try
		{
			final BufferedImage tif = ImageIO.read(new File("C:\\UBFax\\Output\\2019011611385560136.tiff"));
			Image thumbnail = tif.getScaledInstance( 100, 141, BufferedImage.SCALE_SMOOTH );
			ImageIO.write( CreateBufferedImage(thumbnail), "jpg", new File("c:\\temp\\test.jpg") );
		}
		catch( Exception e )
		{
			
		}
		
		return "tiff_convert";
	}
	
	@RequestMapping(value = "get_img", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	//@RequestMapping(value = "get_img", method = RequestMethod.GET)
	public @ResponseBody byte[] GetImage() throws IOException
	{
		FileInputStream in = new FileInputStream( new File("c:\\temp\\test.jpg"));
		return IOUtils.toByteArray( in );
	}
	
	@RequestMapping(value = "test_img", method = RequestMethod.GET)
	public String TestImg()
	{
		return "test_img";
	}
}
