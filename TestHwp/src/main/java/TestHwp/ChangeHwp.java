package TestHwp;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;

import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.object.bodytext.Section;
import kr.dogfoot.hwplib.object.bodytext.control.Control;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.HWPChar;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.HWPCharNormal;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.ParaText;
import kr.dogfoot.hwplib.reader.HWPReader;
import kr.dogfoot.hwplib.tool.textextractor.ForControl;
import kr.dogfoot.hwplib.tool.textextractor.TextExtractMethod;
import kr.dogfoot.hwplib.writer.HWPWriter;

public class ChangeHwp
{
	public static void paragraph(Paragraph p, TextExtractMethod tem, StringBuffer sb) throws UnsupportedEncodingException
	{
		ParaText pt = p.getText();
		if( pt != null )
		{
			int controlIndex = 0;
			for( HWPChar ch : pt.getCharList() )
			{
				switch( ch.getType() )
				{
				case Normal:
					normalText(ch, sb);
					break;
				case ControlExtend:
					if( tem == TextExtractMethod.InsertControlTextBetweenParagraphText )
					{
						sb.append("\n");
						ForControl.extract( p.getControlList().get(controlIndex), tem, sb );
						controlIndex++;
					}
					break;
				default:
					break;
				}
			}
			sb.append("\n");
		}
		
		if( tem == TextExtractMethod.AppendControlTextAfterParagraphText ) 
		{
			controls( p.getControlList(), tem, sb );
		}
	}

	static LinkedList<HWPChar> garrCharList = new LinkedList<HWPChar>();
	static String gstrNum = "";
	static String gstrMask = "*";

	/**
	 * 일반 문자에서 문자를 추출한다.
	 *
	 * @param ch 한글 문자
	 * @param sb 추출된 텍스트를 저정할 StringBuffer 객체
	 * @throws UnsupportedEncodingException
	 */
	private static void normalText(HWPChar ch, StringBuffer sb) throws UnsupportedEncodingException
	{
		String s = ((HWPCharNormal) ch).getCh();
		sb.append( s );

		if( ( s.compareTo( "0" ) >= 0 && s.compareTo( "9" ) <= 0 ) || s.equals( "-" ) )
		{
			garrCharList.addLast( ch );
			gstrNum += s;

			if( gstrNum.length( ) == 14 )
			{
				for( HWPChar c : garrCharList )
				{
					c.setCode( (short) gstrMask.codePointAt(0) );
				}

				garrCharList.clear( );
				gstrNum = "";
			}
		}
		else
		{
			garrCharList.clear( );
			gstrNum = "";
		}
	}
	
  /**
   * 컨트롤 리스트에 포함된 컨트롤에서 텍스트를 추출한다.
   *
   * @param controlList 컨트롤 리스트
   * @param tem         텍스트 추출 방법
   * @param sb          추출된 텍스트를 저정할 StringBuffer 객체
   * @throws UnsupportedEncodingException
   */
  private static void controls(ArrayList<Control> controlList, TextExtractMethod tem, StringBuffer sb) throws UnsupportedEncodingException
  {
		if( controlList != null )
		{
			for( Control c : controlList )
			{
				ForControl.extract( c, tem, sb );
		  }
		}
  }	
	
	static void Change( HWPFile hwpFile, TextExtractMethod tem ) throws Exception
	{
		StringBuffer sb = new StringBuffer();
    for( Section s : hwpFile.getBodyText().getSectionList() )
    {
    	for( Paragraph p : s ) 
    	{
        paragraph( p, tem, sb );
    	}
    }
	}
	
	public static void main( String[] args ) throws Exception
	{
		TextExtractMethod tem = TextExtractMethod.InsertControlTextBetweenParagraphText;
		
    HWPFile hwpFile = HWPReader.fromFile( "c:/temp/1.hwp" );
    Change( hwpFile, tem );
    
    HWPWriter.toFile( hwpFile, "c:/temp/2.hwp" );
  }
}
