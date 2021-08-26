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

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/** 워드 파일 (.docx) 을 읽어서 14자리 숫자 또는 '-' 로 구성된 문자열을 * 문자열로 수정한다.
 * 
 * @author 이영한 ( http://blog.naver.com/websearch )
 *
 */
public class ChangeWord 
{
	public static void Change( IBody clsBody )
	{
		for( XWPFParagraph paragraph: clsBody.getParagraphs() )
		{
			for( XWPFRun run: paragraph.getRuns() )
			{
				StringBuffer clsBuf = new StringBuffer();
				int iStartPos = -1;
				boolean bChange = false;
				
				clsBuf.append( "" + run );
				
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
					run.setText( clsBuf.toString(), 0 );
				}
			}
		}
	}
	
    public static void main( String[] args ) throws Exception
    {
		XWPFDocument document = new XWPFDocument( new FileInputStream( "c:/temp/1.docx" ) );
		
		Change( document );
		
		for( XWPFTable table : document.getTables() )
		{
			for( XWPFTableRow row : table.getRows() )
			{
				for( XWPFTableCell cell : row.getTableCells() )
				{
					Change( cell );
				}
			}
		}			
		
		document.write( new FileOutputStream( "c:/temp/2.docx" ) );
    }
}
