package TestWord.TestWord;

import java.io.File;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class CreateXls
{
	public static void main( String [] args ) throws Exception
	{
		HSSFWorkbook clsWorkBook = new HSSFWorkbook();
		HSSFSheet clsSheet = clsWorkBook.createSheet( );
		int iRowIndex = 0;
		
		for( int i = 0; i < 1050000; ++i )
		{
			HSSFRow clsRow = clsSheet.createRow( iRowIndex );
			
			HSSFCell clsCell = clsRow.createCell( 0 );
			clsCell.setCellValue( Integer.toString( iRowIndex ) );
			
			++iRowIndex;
			
			if( iRowIndex == 60000 )
			{
				clsSheet = clsWorkBook.createSheet( );
				iRowIndex = 0;
			}
		}
		
		clsWorkBook.write( new File("c:/temp/1.xls") );
		
		/* 아래와 같이 실행하면 Invalid row number (65536) outside allowable range (0..65535) Exception 이 발생한다.
		for( int iRowIndex = 0; iRowIndex < 1050000; ++iRowIndex )
		{
			HSSFRow clsRow = clsSheet.createRow( iRowIndex );
			
			HSSFCell clsCell = clsRow.createCell( 0 );
			clsCell.setCellValue( Integer.toString( iRowIndex ) );
		}
		*/
	}
}
