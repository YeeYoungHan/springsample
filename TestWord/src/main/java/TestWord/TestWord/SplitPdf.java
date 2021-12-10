package TestWord.TestWord;

import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;

/** 입력된 PDF 파일을 페이지별로 PDF 파일을 생성한다.
 * 
 * @author 이영한 ( http://blog.naver.com/websearch )
 *
 */
public class SplitPdf
{
	public static void SplitOnePage()
	{
		try
		{
			PdfReader clsReader = new PdfReader( "c:/temp/asterisk.pdf" );
			int iPageCount = clsReader.getNumberOfPages( );
			System.out.println( "iPageCount = " + iPageCount );
			int iPage = 0;

			while( iPage < iPageCount )
			{
				String strFileName = "c:/temp/pdf/asterisk-" + String.format( "%03d", iPage + 1 ) + ".pdf";
				
				Document clsDoc = new Document( clsReader.getPageSizeWithRotation( 1 ) );
				PdfCopy clsOutput = new PdfCopy( clsDoc, new FileOutputStream( strFileName ) );
				clsDoc.open( );
				PdfImportedPage clsPage = clsOutput.getImportedPage( clsReader, ++iPage );
				clsOutput.addPage( clsPage );
				clsDoc.close( );
				clsOutput.close( );
			}
		}
		catch( Exception e )
		{
			e.printStackTrace( );
		}
	}
	
	public static void Split100Page()
	{
		try
		{
			PdfReader clsReader = new PdfReader( "c:/temp/asterisk.pdf" );
			int iPageCount = clsReader.getNumberOfPages( );
			System.out.println( "iPageCount = " + iPageCount );
			int iPage = 0;

			while( iPage < iPageCount )
			{
				String strFileName = "c:/temp/pdf100/asterisk-" + String.format( "%03d", iPage + 1 ) + ".pdf";
				
				Document clsDoc = new Document( clsReader.getPageSizeWithRotation( 1 ) );
				PdfCopy clsOutput = new PdfCopy( clsDoc, new FileOutputStream( strFileName ) );
				clsDoc.open( );
				
				for( int i = 0; i < 100 && iPage < iPageCount; ++i )
				{
					PdfImportedPage clsPage = clsOutput.getImportedPage( clsReader, ++iPage );
					clsOutput.addPage( clsPage );
				}
				
				clsDoc.close( );
				clsOutput.close( );
			}
		}
		catch( Exception e )
		{
			e.printStackTrace( );
		}
	}
	
	public static void main( String[] args )
	{
		Split100Page();
	}
}
