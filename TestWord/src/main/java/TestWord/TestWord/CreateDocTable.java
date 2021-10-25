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
import java.io.FileOutputStream;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class CreateDocTable
{
	public static void main( String [] args ) throws Exception
	{
		XWPFDocument clsDoc = new XWPFDocument();
		FileOutputStream clsOut = new FileOutputStream(new File("c:/temp/1.docx"));
		
		XWPFTable clsTable = clsDoc.createTable();
		XWPFTableRow clsRow = clsTable.getRow(0);
		clsRow.getCell(0).setText("첫번째컬럼");
		clsRow.addNewTableCell( ).setText( "두번째컬럼" );
		clsRow.addNewTableCell( ).setText( "세번째컬럼" );
		
		for( int iRow = 0; iRow < 3; ++iRow )
		{
			clsRow = clsTable.createRow( );
			
			for( int iCol = 0; iCol < 3; ++iCol )
			{
				clsRow.getCell(iCol).setText( "row_" + iRow + " col_" + iCol );
			}
		}
		
		clsDoc.write( clsOut );
		clsOut.close( );
		clsDoc.close( );
	}
}
