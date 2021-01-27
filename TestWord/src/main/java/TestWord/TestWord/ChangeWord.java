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
	
    public static void main( String[] args )
    {
		try 
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
		catch (Exception e1) 
		{
			System.err.println(e1);
		}
    }
}
