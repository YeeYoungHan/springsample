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

import java.io.FileInputStream;
import java.util.Locale;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ExcelController{
	
	private static final Logger logger = LoggerFactory.getLogger(ExcelController.class);
	
	@RequestMapping(value = "excel", method = RequestMethod.GET)
	public String play(Locale locale, Model model){
		
		try
		{
			HSSFWorkbook clsBook = new HSSFWorkbook( new FileInputStream( "c:\\temp\\1.xlsx" ) );
			Sheet clsSheet = clsBook.getSheetAt(0);
			int iNum = clsSheet.getLastRowNum();
			logger.debug( "lastRowNum=" + iNum );
			clsBook.close();
		}
		catch( Exception e )
		{
			// 암호화된 xlsx 파일이면 아래와 같은 에러 메시지가 출력된다.
			// org.apache.poi.EncryptedDocumentException: The supplied spreadsheet seems to be an Encrypted .xlsx file. It must be decrypted before use by XSSF, it cannot be used by HSSF
			
			// 암호화되어 있지 않는 xlsx 파일이면 아래와 같은 에러 메시지가 출력된다.
			// org.apache.poi.poifs.filesystem.OfficeXmlFileException: The supplied data appears to be in the Office 2007+ XML. You are calling the part of POI that deals with OLE2 Office Documents. You need to call a different part of POI to process this data (eg XSSF instead of HSSF)
			
			// 암호화된 xls 파일이면 아래와 같은 에러 메시지가 출력된다.
			// org.apache.poi.EncryptedDocumentException: Default password is invalid for salt/verifier/verifierHash
			String strError = e.toString();
			
			logger.error( strError );
		}
		
		return "home";
	}
}
