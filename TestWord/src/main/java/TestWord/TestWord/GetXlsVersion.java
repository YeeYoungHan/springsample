package TestWord.TestWord;

import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class GetXlsVersion 
{
	public static void main( String [] args ) throws Exception
	{
		HSSFWorkbook workbook = new HSSFWorkbook( new FileInputStream( "c:/temp/1.xls" ) );
		
		int iVersion = workbook.getDocumentSummaryInformation().getApplicationVersion();
		
		// 엑셀 프로그램 버전을 출력한다.
		// 버전 정보는 https://blog.naver.com/websearch/222268534563 에 기술되어 있음
		System.out.println( ((iVersion&0xFF0000) >> 16 ) );
		
		workbook.close();
	}
}
