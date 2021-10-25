package TestWord.TestWord;

import java.io.FileOutputStream;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class Pdf implements DownloadDoc
{
	Document m_clsDoc;
	Font m_clsFont;
	PdfPTable m_clsTable = null;
	
	public Pdf( String strFileName ) throws Exception
	{
		Open( strFileName );
	}
	
	@Override
	public void Open( String strFileName ) throws Exception
	{
		String strFontPath = System.getProperty( "user.dir" ) + "/NanumBarunGothic-Regular.ttf";
		BaseFont clsBaseFont = BaseFont.createFont( strFontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED );
		m_clsFont = new Font( clsBaseFont, 10 );
		
		m_clsDoc = new Document( );

		// landscape
		m_clsDoc.setPageSize( PageSize.A4.rotate( ) );
		
		PdfWriter clsWriter = PdfWriter.getInstance( m_clsDoc, new FileOutputStream( strFileName ) );
		PdfPageEvent clsPageEvent = new PdfPageEvent();
		clsWriter.setPageEvent( clsPageEvent );
		
		m_clsDoc.open( );
	}
	
	public void AddString( String strText, boolean bNewLine ) throws Exception
	{
		m_clsDoc.add( new Paragraph( strText, m_clsFont ) );
		
		if( bNewLine ) m_clsDoc.add( Chunk.NEWLINE );
	}
	
	public void AddDotString( String strText, boolean bNewLine ) throws Exception
	{
		Paragraph clsParagraph = new Paragraph( "\u2022 " + strText, m_clsFont );
		clsParagraph.setIndentationLeft( 20 );
		m_clsDoc.add( clsParagraph );
		
		if( bNewLine ) m_clsDoc.add( Chunk.NEWLINE );
	}
	
	public void AddNewLine( ) throws Exception
	{
		m_clsDoc.add( Chunk.NEWLINE );
	}
	
	public void CreateTable( int iColCount )
	{
		m_clsTable = new PdfPTable( iColCount );
		m_clsTable.setWidthPercentage( 100 );
	}
	
	public void AddCol( String strText )
	{
		Paragraph clsParagraph = new Paragraph( strText, m_clsFont );
		clsParagraph.setLeading( 1.4f, 1.2f );
		
		PdfPCell clsCell = new PdfPCell();
		clsCell.addElement( clsParagraph );
		
		m_clsTable.addCell( clsCell );
		
		//m_clsTable.addCell( new Phrase( strText, m_clsFont ) );
	}
	
	public void AddCol( String strText, int iColSpan )
	{
		// PdfPCell clsCell = new PdfPCell( new Phrase( strText, m_clsFont ) );
		
		Paragraph clsParagraph = new Paragraph( strText, m_clsFont );
		clsParagraph.setLeading( 1.2f, 1.2f );
		
		PdfPCell clsCell = new PdfPCell();
		clsCell.addElement( clsParagraph );
		
		clsCell.setColspan( iColSpan );
		m_clsTable.addCell( clsCell );
	}
	
	public void AddCol( int iInt )
	{
		AddCol( "" + iInt );
	}
	
	public void AddTable( ) throws Exception
	{
		if( m_clsTable != null )
		{
			m_clsDoc.add( m_clsTable );
			m_clsTable = null;
		}
	}
	
	public void Close() throws Exception
	{
		AddTable();
		m_clsDoc.close( );
	}
	
	static class PdfPageEvent extends PdfPageEventHelper
	{
		@Override
		public void onEndPage( PdfWriter writer, Document document )
		{
			super.onEndPage( writer, document );
			
			PdfContentByte clsContentByte = writer.getDirectContent();
			
			//Phrase clsPhrase = new Phrase( "" + document.getPageNumber( ) );
			Phrase clsPhrase = new Phrase( "100" );
			//ColumnText.showTextAligned( clsContentByte, Element.ALIGN_RIGHT, clsPhrase, (document.right() - document.left()) / 2 + document.leftMargin(), document.bottom() - 20, 0);
			ColumnText.showTextAligned( clsContentByte, Element.ALIGN_RIGHT, clsPhrase, document.right(), document.bottom() - 20, 0);
						
			clsContentByte.moveTo( document.left(), document.bottom() - 5 );
			clsContentByte.lineTo( document.right(), document.bottom() - 5 );
			clsContentByte.stroke( );
		}
	}
}
