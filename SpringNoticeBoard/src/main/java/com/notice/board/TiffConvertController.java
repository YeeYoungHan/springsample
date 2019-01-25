package com.notice.board;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
}
