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

import java.io.FileOutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class CreatePdf
{
	public static int COL_COUNT = 4;
	
	public static void main( String[] args ) throws Exception
	{
		String strWorkFolder = System.getProperty( "user.dir" );
		BaseFont clsBaseFont = BaseFont.createFont( strWorkFolder + "/NanumBarunGothic-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED );
		Font clsFont = new Font( clsBaseFont, 10 );

		Document clsDoc = new Document( );

		// landscape
		clsDoc.setPageSize( PageSize.A4.rotate( ) );

		PdfWriter.getInstance( clsDoc, new FileOutputStream( "c:/temp/1.pdf" ) );
		clsDoc.open( );
		
		clsDoc.add( new Paragraph( "제목 : pdf 생성 방법", clsFont ) );
		clsDoc.add( Chunk.NEWLINE );
		
		Chunk clsLine = new Chunk( new LineSeparator( 1f, 100f, BaseColor.BLACK, Element.ALIGN_CENTER, -1));
		clsDoc.add( clsLine );
		
		Paragraph clsParagraph = new Paragraph( "\u2022 부제목 : 테이블 생성 방법", clsFont );
		clsParagraph.setIndentationLeft( 20 );
		
		clsDoc.add( clsParagraph );
		clsDoc.add( Chunk.NEWLINE );
		
		PdfPTable clsTable = new PdfPTable( COL_COUNT );
		clsTable.setWidthPercentage( 100 );

		for( int iRow = 0; iRow < 3; ++iRow )
		{
			for( int iCol = 0; iCol < COL_COUNT; ++iCol )
			{
				clsTable.addCell( new Phrase( "행" + iRow + ", 열" + iCol, clsFont ) );
			}
		}

		// colspan
		PdfPCell clsCell = new PdfPCell( new Phrase( "한 컬럼 행", clsFont ) );
		clsCell.setColspan( COL_COUNT );
		clsTable.addCell( clsCell );

		clsCell = new PdfPCell( new Phrase( "두 컬럼 행(1)", clsFont ) );
		clsCell.setColspan( COL_COUNT / 2 );
		clsTable.addCell( clsCell );

		clsCell = new PdfPCell( new Phrase( "두 컬럼 행(2)", clsFont ) );
		clsCell.setColspan( COL_COUNT / 2 );
		clsTable.addCell( clsCell );

		clsDoc.add( clsTable );
		clsDoc.close( );
	}
}
