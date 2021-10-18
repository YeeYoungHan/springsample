package TestWord.TestWord;

import java.io.FileOutputStream;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class CreatePdf
{
	public static int COL_COUNT = 4;
	
	public static void main( String[] args ) throws Exception
	{
		String strWorkFolder = System.getProperty( "user.dir" );
		BaseFont clsBaseFont = BaseFont.createFont( strWorkFolder + "/NanumBarunGothic-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED );
		Font clsFont = new Font( clsBaseFont, 10 );

		Document clsDoc = new Document( );

		// landscape
		// clsDoc.setPageSize( PageSize.A4.rotate( ) );

		PdfWriter.getInstance( clsDoc, new FileOutputStream( "c:/temp/1.pdf" ) );
		clsDoc.open( );
		
		clsDoc.add( new Paragraph( "제목 : pdf 생성 방법", clsFont ) );
		clsDoc.add( Chunk.NEWLINE );
		
		Paragraph clsParagraph = new Paragraph( "\u2022 부제목 : 테이블 생성 방법", clsFont );
		clsParagraph.setIndentationLeft( 20 );
		
		clsDoc.add( clsParagraph );
		clsDoc.add( Chunk.NEWLINE );
		
		PdfPTable clsTable = new PdfPTable( COL_COUNT );
		clsTable.setWidthPercentage( 100 );

		for( int iRow = 0; iRow < 3; ++iRow )
		{
			for( int iCol = 0; iCol < COL_COUNT; ++iCol )
			{
				clsTable.addCell( new Phrase( "행" + iRow + ", 열" + iCol, clsFont ) );
			}
		}

		// colspan
		PdfPCell clsCell = new PdfPCell( new Phrase( "한 컬럼 행", clsFont ) );
		clsCell.setColspan( COL_COUNT );
		clsTable.addCell( clsCell );

		clsCell = new PdfPCell( new Phrase( "두 컬럼 행(1)", clsFont ) );
		clsCell.setColspan( COL_COUNT / 2 );
		clsTable.addCell( clsCell );

		clsCell = new PdfPCell( new Phrase( "두 컬럼 행(2)", clsFont ) );
		clsCell.setColspan( COL_COUNT / 2 );
		clsTable.addCell( clsCell );

		clsDoc.add( clsTable );
		clsDoc.close( );
	}
}
