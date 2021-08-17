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
			// ��ȣȭ�� xlsx �����̸� �Ʒ��� ���� ���� �޽����� ��µȴ�.
			// org.apache.poi.EncryptedDocumentException: The supplied spreadsheet seems to be an Encrypted .xlsx file. It must be decrypted before use by XSSF, it cannot be used by HSSF
			
			// ��ȣȭ�Ǿ� ���� �ʴ� xlsx �����̸� �Ʒ��� ���� ���� �޽����� ��µȴ�.
			// org.apache.poi.poifs.filesystem.OfficeXmlFileException: The supplied data appears to be in the Office 2007+ XML. You are calling the part of POI that deals with OLE2 Office Documents. You need to call a different part of POI to process this data (eg XSSF instead of HSSF)
			
			// ��ȣȭ�� xls �����̸� �Ʒ��� ���� ���� �޽����� ��µȴ�.
			// org.apache.poi.EncryptedDocumentException: Default password is invalid for salt/verifier/verifierHash
			String strError = e.toString();
			
			logger.error( strError );
		}
		
		return "home";
	}
}
