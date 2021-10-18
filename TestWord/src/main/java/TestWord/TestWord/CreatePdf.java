package TestWord.TestWord;

import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class CreatePdf
{
	public static void main( String [] args ) throws Exception
	{
		String strWorkFolder = System.getProperty("user.dir");
		BaseFont clsBaseFont = BaseFont.createFont( strWorkFolder + "/NanumBarunGothic-Regular.ttf" , BaseFont.IDENTITY_H, BaseFont.EMBEDDED );
		Font clsFont = new Font( clsBaseFont, 10 );
		
		Document clsDoc = new Document();
    PdfWriter.getInstance( clsDoc, new FileOutputStream("c:/temp/1.pdf") );
      
    clsDoc.open();
     PdfPTable table = new PdfPTable(4);

     for( int iRow = 0; iRow < 3; ++iRow )
     {
    	 for( int iCol = 0; iCol < 4; ++iCol )
    	 {
    		 //table.addCell( "row_" + iRow + ", col_" + iCol );
    		 table.addCell( new Phrase( "행" + iRow + ", 열" + iCol, clsFont ) );
    	 }
     }

     clsDoc.add(table);
     clsDoc.close();
	}
}
