package TestWord.TestWord;

import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CreateXlsx
{
	public static void main( String [] args ) throws Exception
	{
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet( );
		
		for( int i = 0; i < 1048577; ++i )
		{
			XSSFRow row = sheet.createRow( i );
			XSSFCell cell = row.createCell( 0 );
			cell.setCellValue( Integer.toString( i + 1 ) );
		}
		
		workbook.write( new FileOutputStream("c:/temp/1.xlsx") );
		workbook.close( );
		
		/*

		1048576 까지는 정상적으로 생성되고 1048577 이상이면 아래와 같은 Exception 이 발생한다.

	  Exception in thread "main" java.lang.IllegalArgumentException: Invalid row number (1048576) outside allowable range (0..1048575)
				at org.apache.poi.xssf.usermodel.XSSFRow.setRowNum(XSSFRow.java:407)
				at org.apache.poi.xssf.usermodel.XSSFSheet.createRow(XSSFSheet.java:782)
				at TestWord.TestWord.CreateXlsx.main(CreateXlsx.java:19)
		 */
	}
}
