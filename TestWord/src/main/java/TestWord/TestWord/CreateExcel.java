package TestWord.TestWord;

import java.io.File;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

public class CreateExcel
{
	public static void main( String[] args ) throws Exception
	{
		HSSFWorkbook clsWorkBook = new HSSFWorkbook();
		
		CellStyle clsCellStyle = clsWorkBook.createCellStyle();
		
		// 박스 라인
		clsCellStyle.setBorderBottom( BorderStyle.THIN );
		clsCellStyle.setBottomBorderColor( IndexedColors.BLACK.getIndex() );
		clsCellStyle.setBorderLeft( BorderStyle.THIN );
		clsCellStyle.setLeftBorderColor( IndexedColors.BLACK.getIndex() );
		clsCellStyle.setBorderRight( BorderStyle.THIN );
		clsCellStyle.setRightBorderColor( IndexedColors.BLACK.getIndex() );
		clsCellStyle.setBorderTop( BorderStyle.THIN );
		clsCellStyle.setTopBorderColor( IndexedColors.BLACK.getIndex() );
		
		// 중앙 정렬
		clsCellStyle.setAlignment( HorizontalAlignment.CENTER );
		clsCellStyle.setVerticalAlignment( VerticalAlignment.CENTER );
		
		// multi-line
		//clsCellStyle.setWrapText( true );
		
		HSSFSheet clsSheet = clsWorkBook.createSheet( );
		
		clsSheet.setDefaultRowHeight( (short)350 );

		// 10행 * 5열을 추가한다.
		for( int iRow = 0; iRow < 10; ++iRow )
		{
			HSSFRow clsRow = clsSheet.createRow( iRow );
			
			for( int iCol = 0; iCol < 5; ++iCol )
			{
				HSSFCell clsCell = clsRow.createCell( iCol );

				clsCell.setCellValue( "행(" + iRow + ") 열(" + iCol + ") 입니다." );
				clsCell.setCellStyle( clsCellStyle );
			}
		}
		
		// 컬럼 폭이 자동으로 설정되도록 수정한다.
		for( int iCol = 0; iCol < 5; ++iCol )
		{
			clsSheet.autoSizeColumn( iCol );
		}
		
		// 자동으로 설정된 컬럼 폭을 약간 더 증가시킨다.
		for( int iCol = 0; iCol < 5; ++iCol )
		{
			int iWidth = clsSheet.getColumnWidth( iCol );
			clsSheet.setColumnWidth( iCol, iWidth + 400 );
		}

		clsWorkBook.write( new File( "c:/temp/1.xls" ) );
		clsWorkBook.close( );
	}
}
