package TestWord.TestWord;

import java.io.File;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class CreateExcel
{
	// HSSFWorkbook, HSSFSheet, HSSFRow, and HSSFCell 
	public static void main( String[] args ) throws Exception
	{
		HSSFWorkbook clsWorkBook = new HSSFWorkbook();
		
		HSSFSheet clsSheet = clsWorkBook.createSheet( );
		
		for( int iRow = 0; iRow < 10; ++iRow )
		{
			HSSFRow clsRow = clsSheet.createRow( iRow );
			
			for( int iCol = 0; iCol < 5; ++iCol )
			{
				HSSFCell clsCell = clsRow.createCell( iCol );
				
				
				
				//clsCell.setCellValue( "row(" + iRow + ") col(" + iCol + ")" );
				clsCell.setCellValue( "행(" + iRow + ") 열(" + iCol + ") 입니다." );
			}
		}
		
		clsWorkBook.write( new File( "c:/temp/1.xls" ) );
		clsWorkBook.close( );
	}
}
