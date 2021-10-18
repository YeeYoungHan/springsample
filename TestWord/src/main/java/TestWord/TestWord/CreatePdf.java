package TestWord.TestWord;

import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class CreatePdf
{
	public static void main( String [] args ) throws Exception
	{
		Document clsDoc = new Document();
    PdfWriter.getInstance( clsDoc, new FileOutputStream("c:/temp/1.pdf") );
      
    clsDoc.open();
     PdfPTable table = new PdfPTable(4);

     for( int iRow = 0; iRow < 3; ++iRow )
     {
    	 for( int iCol = 0; iCol < 4; ++iCol )
    	 {
    		 table.addCell( "row_" + iRow + ", col_" + iCol );
    	 }
     }

     clsDoc.add(table);
     clsDoc.close();
	}
}
