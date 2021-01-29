package TestHwp;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;

import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.object.bodytext.ParagraphListInterface;
import kr.dogfoot.hwplib.object.bodytext.Section;
import kr.dogfoot.hwplib.object.bodytext.control.Control;
import kr.dogfoot.hwplib.object.bodytext.control.ControlEndnote;
import kr.dogfoot.hwplib.object.bodytext.control.ControlFooter;
import kr.dogfoot.hwplib.object.bodytext.control.ControlFootnote;
import kr.dogfoot.hwplib.object.bodytext.control.ControlHeader;
import kr.dogfoot.hwplib.object.bodytext.control.ControlTable;
import kr.dogfoot.hwplib.object.bodytext.control.gso.ControlArc;
import kr.dogfoot.hwplib.object.bodytext.control.gso.ControlContainer;
import kr.dogfoot.hwplib.object.bodytext.control.gso.ControlCurve;
import kr.dogfoot.hwplib.object.bodytext.control.gso.ControlEllipse;
import kr.dogfoot.hwplib.object.bodytext.control.gso.ControlPolygon;
import kr.dogfoot.hwplib.object.bodytext.control.gso.ControlRectangle;
import kr.dogfoot.hwplib.object.bodytext.control.gso.GsoControl;
import kr.dogfoot.hwplib.object.bodytext.control.gso.textbox.TextBox;
import kr.dogfoot.hwplib.object.bodytext.control.table.Cell;
import kr.dogfoot.hwplib.object.bodytext.control.table.Row;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.HWPChar;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.HWPCharNormal;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.ParaText;
import kr.dogfoot.hwplib.reader.HWPReader;
import kr.dogfoot.hwplib.tool.textextractor.TextExtractMethod;
import kr.dogfoot.hwplib.writer.HWPWriter;

public class ChangeHwp
{
	LinkedList<HWPChar> m_arrCharList = new LinkedList<HWPChar>();
	String m_strNum = "";
	String m_strMask = "*";
	static boolean m_bDebug = false;
	int m_iChangeCount = 0;
	
	void paragraph( Paragraph p, TextExtractMethod tem, StringBuffer sb ) throws UnsupportedEncodingException
	{
		ParaText pt = p.getText();
		if( pt != null )
		{
			int iControlIndex = 0;
			
			for( HWPChar ch : pt.getCharList() )
			{
				switch( ch.getType() )
				{
				case Normal:
					normalText(ch);
					break;
				case ControlExtend:
					if( tem == TextExtractMethod.InsertControlTextBetweenParagraphText )
					{
						extract( p.getControlList().get(iControlIndex), tem, sb );
						++iControlIndex;
					}
					break;
				default:
					break;
				}
			}
		}
		
		if( tem == TextExtractMethod.AppendControlTextAfterParagraphText ) 
		{
			controls( p.getControlList(), tem, sb );
		}
	}

	void normalText( HWPChar ch ) throws UnsupportedEncodingException
	{
		String s = ((HWPCharNormal) ch).getCh();

		if( ( s.compareTo( "0" ) >= 0 && s.compareTo( "9" ) <= 0 ) || s.equals( "-" ) || s.equals( "(" ) || s.equals( ")" ) )
		{
			m_arrCharList.addLast( ch );
			m_strNum += s;
			
			if( PersonalInformation.Check( m_strNum ) )
			{
				if( m_bDebug )
				{
					System.out.println( "[" + m_strNum + "]" );
				}
				
				for( HWPChar c : m_arrCharList )
				{
					c.setCode( (short) m_strMask.codePointAt(0) );
				}

				m_arrCharList.clear( );
				m_strNum = "";
				++m_iChangeCount;
			}

			if( m_strNum.length( ) >= 14 )
			{
				m_arrCharList.clear( );
				m_strNum = "";
			}
		}
		else
		{
			m_arrCharList.clear( );
			m_strNum = "";
		}
	}
	
  void controls( ArrayList<Control> controlList, TextExtractMethod tem, StringBuffer sb ) throws UnsupportedEncodingException
  {
		if( controlList != null )
		{
			for( Control c : controlList )
			{
				extract( c, tem, sb );
		  }
		}
  }
  
  public void extractGso( GsoControl c, TextExtractMethod tem, StringBuffer sb ) throws UnsupportedEncodingException
  {
  	switch( c.getGsoType() )
  	{
  	case Rectangle:
      textBox( ((ControlRectangle)c).getTextBox(), tem, sb );
      break;
    case Ellipse:
    	textBox( ((ControlEllipse)c).getTextBox( ), tem, sb );
      break;
    case Arc:
    	textBox( ((ControlArc)c).getTextBox( ), tem, sb );
      break;
    case Polygon:
    	textBox( ((ControlPolygon)c).getTextBox( ), tem, sb );
      break;
    case Curve:
    	textBox( ((ControlCurve)c).getTextBox( ), tem, sb );
      break;
    case Container:
    	for( GsoControl child : ((ControlContainer)c).getChildControlList())
    	{
    		extractGso( child, tem, sb );
    	}
      break;
    default:
      break;
  	}
  }
  
  public void extract( Control c, TextExtractMethod tem, StringBuffer sb) throws UnsupportedEncodingException
  {
    if( c.isField() )
    {
    }
    else
    {
      switch (c.getType())
      {
      case Table:
        table( (ControlTable)c, tem, sb );
        break;
      case Gso:
      	extractGso( (GsoControl)c, tem, sb );
        break;
      case Header:
        Change( ((ControlHeader)c).getParagraphList(), tem, sb );
        break;
      case Footer:
        Change( ((ControlFooter)c).getParagraphList( ), tem, sb );
        break;
      case Footnote:
        Change( ((ControlFootnote)c).getParagraphList( ), tem, sb );
        break;
      case Endnote:
        Change( ((ControlEndnote)c).getParagraphList( ), tem, sb );
        break;
      default:
        break;
      }
    }
  }
  
  void table( ControlTable table, TextExtractMethod tem, StringBuffer sb ) throws UnsupportedEncodingException
  {
  	for( Row r : table.getRowList() )
  	{
  		for( Cell c : r.getCellList() )
  		{
  			Change( c.getParagraphList(), tem, sb );
  		}
  	}
  }
  
  void Change( ParagraphListInterface paragraphList, TextExtractMethod tem, StringBuffer sb ) throws UnsupportedEncodingException
  {
  	if( paragraphList == null )
  	{
      return;
  	}
  	
	  for( Paragraph p : paragraphList )
	  {
	  	paragraph( p, tem, sb );
	  }
  }
  
  void textBox( TextBox textBox, TextExtractMethod tem, StringBuffer sb) throws UnsupportedEncodingException
  {
  	if( textBox != null )
  	{
  		Change( textBox.getParagraphList(), tem, sb );
  	}
  }
	
	static boolean Change( HWPFile hwpFile, TextExtractMethod tem ) throws Exception
	{
		ChangeHwp clsChangeHwp = new ChangeHwp();
		StringBuffer clsBuf = new StringBuffer();
		
    for( Section clsSection : hwpFile.getBodyText().getSectionList() )
    {
    	clsChangeHwp.Change( clsSection, tem, clsBuf );
    }
    
    if( clsChangeHwp.m_iChangeCount > 0 ) return true;
    
    return false;
	}
	
	static void Change( String strInputFile, String strOutputFile ) throws Exception
	{
		TextExtractMethod tem = TextExtractMethod.InsertControlTextBetweenParagraphText;
		
    HWPFile hwpFile = HWPReader.fromFile( strInputFile );
    if( Change( hwpFile, tem ) )
    {
    	HWPWriter.toFile( hwpFile, strOutputFile );
    }
	}
	
	public static void main( String[] args ) throws Exception
	{
		TextExtractMethod tem = TextExtractMethod.InsertControlTextBetweenParagraphText;
		
		ChangeHwp.m_bDebug = true;
    HWPFile hwpFile = HWPReader.fromFile( "c:/temp/1.hwp" );
    Change( hwpFile, tem );
    
    HWPWriter.toFile( hwpFile, "c:/temp/2.hwp" );
  }
}
