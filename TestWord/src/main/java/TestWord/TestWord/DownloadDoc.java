package TestWord.TestWord;

public interface DownloadDoc
{
	public void Open( String strFileName ) throws Exception;
	public void AddString( String strText, boolean bNewLine ) throws Exception;
	public void AddDotString( String strText, boolean bNewLine ) throws Exception;
	public void AddNewLine( ) throws Exception;
	public void CreateTable( int iColCount );
	public void AddCol( String strText );
	public void AddCol( String strText, int iColSpan );
	public void AddCol( int iInt );
	public void AddTable( ) throws Exception;
	public void Close() throws Exception;
}
