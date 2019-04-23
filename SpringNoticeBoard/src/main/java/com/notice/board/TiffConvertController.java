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

package com.notice.board;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.IndexColorModel;
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
	
	@RequestMapping(value = "1bit", method = RequestMethod.GET)
	public String Convert1bitImage()
	{
		try
		{
			final BufferedImage clsInput = ImageIO.read(new File("C:\\temp\\1.jpg"));
			
			IndexColorModel clsICM = new IndexColorModel(1, 2, new byte[] { (byte) 0, (byte) 0xFF }, new byte[] { (byte) 0, (byte) 0xFF }, new byte[] { (byte) 0, (byte) 0xFF });
	    BufferedImage clsOutput = new BufferedImage( clsInput.getWidth(), clsInput.getHeight(), BufferedImage.TYPE_BYTE_BINARY, clsICM );
	    ColorConvertOp cco = new ColorConvertOp( clsInput.getColorModel().getColorSpace(), clsOutput.getColorModel().getColorSpace(), null );

	    cco.filter( clsInput, clsOutput );
	    
	    ImageIO.write( clsOutput, "tiff", new File("c:\\temp\\1.tiff") );
		}
		catch( Exception e )
		{
			
		}
		
		return "tiff_convert";
	}
}
