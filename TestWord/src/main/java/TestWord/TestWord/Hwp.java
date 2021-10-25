package TestWord.TestWord;

import java.util.ArrayList;
import java.util.List;

import kr.dogfoot.hwplib.reader.HWPReader;
import kr.dogfoot.hwplib.writer.HWPWriter;

public class Hwp implements DownloadDoc
{
	HwpDoc m_clsHwpDoc;
	int m_iColCount = 0;
	int m_iColIndex = 0;
	List<List<HwpCell>> m_arrRow = new ArrayList<List<HwpCell>>();
	List<HwpCell> m_arrCol = null;
	String m_strFileName;
	
	public Hwp( String strFileName ) throws Exception
	{
		Open( strFileName );
	}

	@Override
	public void Open( String strFileName ) throws Exception
	{
		String strHwpPath = System.getProperty( "user.dir" ) + "/test-blank.hwp";
		m_clsHwpDoc = new HwpDoc( HWPReader.fromFile( strHwpPath ) );
		m_strFileName = strFileName;
	}

	@Override
	public void AddString( String strText, boolean bNewLine ) throws Exception
	{
		m_clsHwpDoc.AddString( strText );
		
		if( bNewLine ) m_clsHwpDoc.AddString( "" );
	}

	@Override
	public void AddDotString( String strText, boolean bNewLine ) throws Exception
	{
		m_clsHwpDoc.AddString( "  * " + strText );
		
		if( bNewLine ) m_clsHwpDoc.AddString( "" );
	}

	@Override
	public void AddNewLine() throws Exception
	{
		m_clsHwpDoc.AddString( "" );
	}

	@Override
	public void CreateTable( int iColCount )
	{
		m_iColCount = iColCount;
	}

	@Override
	public void AddCol( String strText )
	{
		AddCol( strText, 1 );
	}

	@Override
	public void AddCol( String strText, int iColSpan )
	{
		if( m_arrCol == null )
		{
			m_arrCol = new ArrayList<HwpCell>();
		}
		
		strText = strText.trim( );
		
		if( strText.isEmpty( ) && iColSpan >= 2 )
		{
			for( int i = 0; i < iColSpan; ++i )
			{
				HwpCell clsCell = new HwpCell();
				clsCell.m_strText = strText;
				clsCell.m_iColSpan = 1;
				
				m_arrCol.add( clsCell );
			}
		}
		else
		{
			HwpCell clsCell = new HwpCell();
			clsCell.m_strText = strText;
			clsCell.m_iColSpan = iColSpan;
			
			m_arrCol.add( clsCell );
		}
		
		m_iColIndex += iColSpan;
		
		if( m_iColIndex >= m_iColCount )
		{
			m_arrRow.add( m_arrCol );
			m_arrCol = null;
			m_iColIndex = 0;
		}			
	}

	@Override
	public void AddCol( int iInt )
	{
		AddCol( "" + iInt );
	}

	@Override
	public void AddTable() throws Exception
	{
		if( m_arrRow.isEmpty( ) == false )
		{
			m_clsHwpDoc.AddTable( m_arrRow );
			m_arrRow = new ArrayList<List<HwpCell>>();
		}
	}

	@Override
	public void Close() throws Exception
	{
		AddTable();
		
		HWPWriter.toFile( m_clsHwpDoc.m_clsHwpFile, m_strFileName );
	}
}
