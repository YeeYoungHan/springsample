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
import org.apache.poi.ss.util.CellRangeAddress;

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
		clsCellStyle.setAlignment( HorizontalAlignment.LEFT );
		clsCellStyle.setVerticalAlignment( VerticalAlignment.CENTER );
		
		// multi-line
		//clsCellStyle.setWrapText( true );
		
		HSSFSheet clsSheet = clsWorkBook.createSheet( );
		
		// 10행 * 5열을 추가한다.
		for( int iRow = 0; iRow < 10; ++iRow )
		{
			HSSFRow clsRow = clsSheet.createRow( iRow );
			clsRow.setHeight( (short)350 );
			
			for( int iCol = 0; iCol < 5; ++iCol )
			{
				HSSFCell clsCell = clsRow.createCell( iCol );

				clsCell.setCellValue( "행(" + iRow + ") 열(" + iCol + ") 입니다." );
				clsCell.setCellStyle( clsCellStyle );
			}
		}
		
		// 마지막 행의 모든 열을 합친다.
		clsSheet.addMergedRegion( new CellRangeAddress( 9, 9, 0, 4 ) );
		
		HSSFRow clsRow = clsSheet.getRow( 9 );
		clsRow.setHeight( (short)700 );
		
		HSSFCell clsCell = clsRow.getCell( 0 );
		
		clsCell.setCellValue( "첫번째 라인입니다.\n두번째 라인입니다." );
		clsCellStyle.setWrapText( true );
		clsCell.setCellStyle( clsCellStyle );
		
		
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
