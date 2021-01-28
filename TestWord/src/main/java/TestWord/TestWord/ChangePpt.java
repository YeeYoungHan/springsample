package TestWord.TestWord;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFTextParagraph;

/** 파워포인트 파일 (.ppt) 을 읽어서 14자리 숫자 또는 '-' 로 구성된 문자열을 * 문자열로 수정한다.
 * 
 * @author 이영한 ( http://blog.naver.com/websearch )
 *
 */
public class ChangePpt
{
	public static void Change( List<HSLFTextParagraph> clsText )
	{
		try
		{
			StringBuffer clsBuf = new StringBuffer();
			int iStartPos = -1;
			boolean bChange = false;
			
			clsBuf.append( HSLFTextParagraph.getText( clsText ) );
						
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
				HSLFTextParagraph.setText( clsText, clsBuf.toString() );
			}
		}
		catch( Exception e )
		{
		}
	}
	
	public static void main( String [] args ) throws Exception
	{
		HSLFSlideShow clsShow = new HSLFSlideShow( new FileInputStream( "c:/temp/1.ppt" ) );
		
		for( HSLFSlide clsSlide : clsShow.getSlides() )
		{
			for( List<HSLFTextParagraph> clsList : clsSlide.getTextParagraphs() )
			{
				Change( clsList );
			}
		}
		
		clsShow.write( new FileOutputStream( "c:/temp/2.ppt" ) );
		
		clsShow.close();
	}
}
