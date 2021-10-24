package TestWord.TestWord;

import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;

public class PdfToDoc
{
	public static void main(String[] args)
	{
    PdfDocument doc = new PdfDocument();

    doc.loadFromFile("c:/Temp/1.pdf");
    doc.saveToFile("c:/Temp/1-out.doc", FileFormat.DOC );

    doc.close();
	}
}
