package TestWord.TestWord;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class CreateDocTable
{
	public static void main( String [] args ) throws Exception
	{
		XWPFDocument clsDoc = new XWPFDocument();
		FileOutputStream clsOut = new FileOutputStream(new File("c:/temp/1.docx"));
		
		XWPFTable clsTable = clsDoc.createTable();
		XWPFTableRow clsRow = clsTable.getRow(0);
		clsRow.getCell(0).setText("첫번째컬럼");
		clsRow.addNewTableCell( ).setText( "두번째컬럼" );
		clsRow.addNewTableCell( ).setText( "세번째컬럼" );
		
		for( int iRow = 0; iRow < 3; ++iRow )
		{
			clsRow = clsTable.createRow( );
			
			for( int iCol = 0; iCol < 3; ++iCol )
			{
				clsRow.getCell(iCol).setText( "row_" + iRow + " col_" + iCol );
			}
		}
		
		clsDoc.write( clsOut );
		clsOut.close( );
		clsDoc.close( );
	}
}
