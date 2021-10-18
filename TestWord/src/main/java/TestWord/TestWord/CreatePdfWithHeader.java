package TestWord.TestWord;

import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class CreatePdfWithHeader
{
public static int COL_COUNT = 4;
	
	public static void main( String[] args ) throws Exception
	{
		String strWorkFolder = System.getProperty( "user.dir" );
		BaseFont clsBaseFont = BaseFont.createFont( strWorkFolder + "/NanumBarunGothic-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED );
		Font clsFont = new Font( clsBaseFont, 10 );

		Document clsDoc = new Document( );

		PdfWriter clsWriter = PdfWriter.getInstance( clsDoc, new FileOutputStream( "c:/temp/1.pdf" ) );
		MyPageEvent2 clsPageEvent = new MyPageEvent2();
		
		clsWriter.setPageEvent( clsPageEvent );
		
		clsDoc.open( );
		clsDoc.close( );
	}
	
	static class MyPageEvent2 extends PdfPageEventHelper
	{

		@Override
		public void onEndPage( PdfWriter writer, Document document )
		{
			super.onEndPage( writer, document );
			
			PdfContentByte cb = writer.getDirectContent();
			Phrase header = new Phrase("this is a header");
			Phrase footer = new Phrase("this is a footer");
			ColumnText.showTextAligned( cb, Element.ALIGN_CENTER, header, (document.right() - document.left()) / 2 + document.leftMargin(), document.top() + 10, 0);
			ColumnText.showTextAligned( cb, Element.ALIGN_CENTER, footer, (document.right() - document.left()) / 2 + document.leftMargin(), document.bottom() - 10, 0);
		}
	}
}
