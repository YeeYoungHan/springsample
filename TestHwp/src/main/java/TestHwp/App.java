package TestHwp;

import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.object.bodytext.Section;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.tool.blankfilemaker.BlankFileMaker;
import kr.dogfoot.hwplib.writer.HWPWriter;

public class App
{
	public static void main( String[] args )
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
}
