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

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController
{
	@RequestMapping(value = "file_upload", method = RequestMethod.GET)
	public String FileUpload()
	{
		return "file_upload";
	}
	
	@RequestMapping(value = "file_upload_ajax", method = RequestMethod.GET)
	public String FileUploadAjax()
	{
		return "file_upload_ajax";
	}
	
	@RequestMapping(value = "file_upload", method = RequestMethod.POST)
	public String FileUploadAction( @RequestParam(value = "file", required = false) MultipartFile clsUpload, Model model ) throws Exception
	{
		System.out.println( "getOriginalFilename[" + clsUpload.getOriginalFilename( ) + "]" );
		System.out.println( "getName[" + clsUpload.getName( ) + "]" );
		System.out.println( "getContentType[" + clsUpload.getContentType( ) + "]" );
		
		String strFileName = "c:/temp/" + clsUpload.getOriginalFilename( );
		
		File clsFile = new File( strFileName );
		FileCopyUtils.copy( clsUpload.getBytes( ), clsFile );
		
		return "file_upload";
	}
}
