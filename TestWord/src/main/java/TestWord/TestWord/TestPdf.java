package TestWord.TestWord;

public class TestPdf
{
	public static void main( String[] args ) throws Exception
	{
		Pdf clsPdf = new Pdf( "1234" );
		
		clsPdf.AddString( "제목", true );
		clsPdf.AddString( "소제목 : ", true );
		
		clsPdf.AddDotString( "도트 #1", false );
		clsPdf.AddDotString( "도트 #2", false );
		clsPdf.AddNewLine( );
	
		int iColCount = 10;
		
		clsPdf.CreateTable( 10 );
		
		for( int i = 0; i < iColCount; ++i )
		{
			clsPdf.AddCol( "헤더_" + ( i+1 ) );
		}
		
		for( int i = 0; i < iColCount; ++i )
		{
			clsPdf.AddCol( "컬럼_" + ( i+1 ) );
		}
		
		clsPdf.AddCol( "col_span\r\ncol_span_2\r\n", iColCount );
		
		int iBlankColCount = iColCount - 6;
		
		clsPdf.AddCol( "", iBlankColCount );
		
		for( int i = 0; i < ( iColCount - iBlankColCount ); ++i )
		{
			clsPdf.AddCol( "결론_" + ( i+1 ) );
		}
		
		clsPdf.Close( );
	}
}
