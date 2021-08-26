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

package TestHwp;

import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.object.bodytext.Section;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.tool.blankfilemaker.BlankFileMaker;
import kr.dogfoot.hwplib.writer.HWPWriter;

public class TestSimpleHwp
{
	static void WriteSimpleHwp()
	{
		try
		{
			HWPFile hwpFile = BlankFileMaker.make( );

			Section s = hwpFile.getBodyText( ).getSectionList( ).get( 0 );
			Paragraph firstParagraph = s.getParagraph( 0 );
			firstParagraph.getText( ).addString( "이것은 추가된 문자열입니다." );
			HWPWriter.toFile(hwpFile, "c:/temp/1.hwp" );
		}
		catch( Exception e )
		{

		}
	}
	
	public static void main( String[] args )
	{
		WriteSimpleHwp();
	}
}
