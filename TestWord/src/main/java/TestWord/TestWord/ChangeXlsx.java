package TestWord.TestWord;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ChangeXlsx
{
	public static void Change( XSSFCell cell )
	{
		try
		{
			StringBuffer clsBuf = new StringBuffer();
			int iStartPos = -1;
			boolean bChange = false;
			
			clsBuf.append( cell.getStringCellValue() );
			
			//System.out.println( clsBuf );
			
			for( int i = 0; i < clsBuf.length(); ++i )
			{
				char c = clsBuf.charAt(i);
				
				if( ( c >= '0' && c <= '9' ) || c == '-' )
				{
					if( iStartPos == -1 )
					{
						iStartPos = i;
					}
					else if( ( i - iStartPos ) >= 13 )
					{
						clsBuf.replace( iStartPos, i+1, "**************" );
						bChange = true;
						iStartPos = -1;
					}
				}
				else
				{
					iStartPos = -1;
				}
			}
			
			if( bChange )
			{
				cell.setCellValue( clsBuf.toString() );
			}
		}
		catch( Exception e )
		{
		}
	}
	
	public static void main( String [] args ) throws Exception
	{
		XSSFWorkbook workbook = new XSSFWorkbook( new FileInputStream( "c:/temp/1.xlsx" ) );
		
		int iSheetCount = workbook.getNumberOfSheets();
		for( int iSheet = 0; iSheet < iSheetCount; ++iSheet )
		{
			XSSFSheet sheet = workbook.getSheetAt(iSheet );
			int iRowCount = sheet.getLastRowNum();
			
			for( int iRow = 0; iRow <= iRowCount; ++iRow )
			{
				XSSFRow row = sheet.getRow( iRow );
				if( row == null ) continue;
				
				short iCellCount = row.getLastCellNum();
				
				for( short iCell = 0; iCell <= iCellCount; ++iCell )
				{
					XSSFCell cell = row.getCell( iCell );
					if( cell == null ) continue;
					
					Change( cell );
				}
			}
		}
		
		workbook.write( new FileOutputStream( "c:/temp/2.xlsx" ) );
		workbook.close();
	}
}
