package TestWord.TestWord;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/** 엑셀 파일 (.xls) 을 읽어서 14자리 숫자 또는 '-' 로 구성된 문자열을 * 문자열로 수정한다.
 * 
 * @author 이영한 ( http://blog.naver.com/websearch )
 *
 */
public class ChangeXls
{
	public static void Change( HSSFCell cell )
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
		HSSFWorkbook workbook = new HSSFWorkbook( new FileInputStream( "c:/temp/1.xls" ) );
		
		int iSheetCount = workbook.getNumberOfSheets();
		
		for( int iSheet = 0; iSheet < iSheetCount; ++iSheet )
		{
			HSSFSheet sheet = workbook.getSheetAt( iSheet );
			int iRowCount = sheet.getLastRowNum();
			
			for( int iRow = 0; iRow <= iRowCount; ++iRow )
			{
				HSSFRow row = sheet.getRow( iRow );
				if( row == null ) continue;
				
				short iCellCount = row.getLastCellNum();
				
				for( short iCell = 0; iCell <= iCellCount; ++iCell )
				{
					HSSFCell cell = row.getCell( iCell );
					if( cell == null ) continue;
					
					Change( cell );
				}
			}
		}
		
		workbook.write( new FileOutputStream( "c:/temp/2.xls" ) );
		workbook.close();
	}
}
