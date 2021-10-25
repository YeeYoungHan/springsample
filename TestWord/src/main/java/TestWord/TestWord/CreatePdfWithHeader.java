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

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class CreatePdfWithHeader
{
	public static void main( String[] args ) throws Exception
	{
		String strWorkFolder = System.getProperty( "user.dir" );
		BaseFont clsBaseFont = BaseFont.createFont( strWorkFolder + "/NanumBarunGothic-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED );
		Font clsFont = new Font( clsBaseFont, 10 );

		Document clsDoc = new Document( );

		PdfWriter clsWriter = PdfWriter.getInstance( clsDoc, new FileOutputStream( "c:/temp/1.pdf" ) );
		MyPageEvent2 clsPageEvent = new MyPageEvent2();
		
		clsWriter.setPageEvent( clsPageEvent );
		
		clsDoc.setPageSize( PageSize.A4.rotate( ) );
		clsDoc.open( );
		clsDoc.add( new Paragraph( "제목 : pdf 생성 방법", clsFont ) );
				
		for( int i = 0; i < 100; ++i )
		{
			clsDoc.add( Chunk.NEWLINE );
		}
		
		clsDoc.close( );
	}
	
	static class MyPageEvent2 extends PdfPageEventHelper
	{
		@Override
		public void onEndPage( PdfWriter writer, Document document )
		{
			super.onEndPage( writer, document );
			
			PdfContentByte cb = writer.getDirectContent();
			
			Phrase header = new Phrase("this is a header");
			
			
			Phrase footer = new Phrase("this is a footer (" + document.getPageNumber( ) + ")" );
			ColumnText.showTextAligned( cb, Element.ALIGN_CENTER, header, (document.right() - document.left()) / 2 + document.leftMargin(), document.top() + 10, 0);
			ColumnText.showTextAligned( cb, Element.ALIGN_CENTER, footer, (document.right() - document.left()) / 2 + document.leftMargin(), document.bottom() - 10, 0);
			
			cb.setLineWidth( 0.4 );
			cb.moveTo( document.left(), document.top() + 5 );
			cb.lineTo( document.right(), document.top() + 5 );
			cb.stroke( );
			
			cb.moveTo( document.left(), document.bottom() + 5 );
			cb.lineTo( document.right(), document.bottom() + 5 );
			cb.stroke( );
		}
	}
}
