package TestWord.TestWord;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xslf.usermodel.XSLFTextShape;

/** 파워포인트 파일 (.pptx) 을 읽어서 14자리 숫자 또는 '-' 로 구성된 문자열을 * 문자열로 수정한다.
 * 
 * @author 이영한 ( http://blog.naver.com/websearch )
 *
 */
public class ChangePptx
{
	public static void Change( XSLFTextShape clsText )
	{
		try
		{
			StringBuffer clsBuf = new StringBuffer();
			int iStartPos = -1;
			boolean bChange = false;
			Color clsTextColor = new Color( 0, 0, 0 );
			
			clsBuf.append( clsText.getText() );
						
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
				clsText.setText( clsBuf.toString() );
				
				// 수정된 텍스트 색상이 하얀색이어서 이를 검정색으로 수정하기 위한 기능
				for( XSLFTextParagraph clsParagraph : clsText.getTextParagraphs() )
				{
					for( XSLFTextRun clsRun : clsParagraph.getTextRuns() )
					{
						clsRun.setFontColor( clsTextColor );
					}
				}
			}
		}
		catch( Exception e )
		{
		}
	}
	
	public static void main( String [] args ) throws Exception
	{
		XMLSlideShow clsShow = new XMLSlideShow( new FileInputStream( "c:/temp/1.pptx" ) );
		
		for( XSLFSlide clsSlide : clsShow.getSlides() )
		{
			for( XSLFShape clsShape : clsSlide.getShapes() )
			{
				if( clsShape instanceof XSLFTextShape )
				{
					XSLFTextShape clsText = (XSLFTextShape)clsShape;
					
					Change( clsText );
				}
			}
		}
		
		clsShow.write( new FileOutputStream( "c:/temp/2.pptx" ) );
		clsShow.close();
	}
}
