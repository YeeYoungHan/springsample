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

public class TestPdf
{
	public static void main( String[] args ) throws Exception
	{
		//DownloadDoc clsPdf = new Pdf( "c:/temp/10.pdf" );
		DownloadDoc clsPdf = new Excel( "c:/temp/10.xls" );
		
		clsPdf.AddString( "제목", true );
		clsPdf.AddString( "소제목 : ", true );
		
		clsPdf.AddDotString( "도트 #1", false );
		clsPdf.AddDotString( "도트 #2", false );
		clsPdf.AddNewLine( );
	
		int iColCount = 10;
		
		clsPdf.CreateTable( 10 );
		
		for( int i = 0; i < iColCount; ++i )
		{
			clsPdf.AddCol( "헤더_" + ( i+1 ) );
		}
		
		for( int i = 0; i < iColCount; ++i )
		{
			clsPdf.AddCol( "컬럼_" + ( i+1 ) );
		}
		
		clsPdf.AddCol( "col_span\r\ncol_span_2\r\n", iColCount );
		
		int iBlankColCount = iColCount - 6;
		
		clsPdf.AddCol( "", iBlankColCount );
		
		for( int i = 0; i < ( iColCount - iBlankColCount ); ++i )
		{
			clsPdf.AddCol( "결론_" + ( i+1 ) );
		}
		
		clsPdf.Close( );
	}
}
