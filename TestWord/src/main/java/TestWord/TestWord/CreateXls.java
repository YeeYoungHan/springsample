package TestWord.TestWord;

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
		
		for( int iRowIndex = 0; iRowIndex < 1050000; ++iRowIndex )
		{
			HSSFRow clsRow = clsSheet.createRow( iRowIndex );
			
			HSSFCell clsCell = clsRow.createCell( 0 );
			clsCell.setCellValue( Integer.toString( iRowIndex ) );
		}
	}
}
