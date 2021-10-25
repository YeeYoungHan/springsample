package TestWord.TestWord;

import java.io.File;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

public class Excel implements DownloadDoc
{
	HSSFWorkbook m_clsWorkBook;
	CellStyle m_clsCellStyle;
	CellStyle m_clsCellStyleWrap;
	HSSFSheet m_clsSheet;
	HSSFRow m_clsRow = null;
	
	int m_iColCount = 0;
	int m_iColIndex = 0;
	int m_iRowIndex = 0;
	int m_iRowLineCount = 0;
	short m_sRowHeight = 350;
	
	String m_strFileName;
	
	public Excel( String strFileName ) throws Exception
	{
		Open( strFileName );
	}
	
	@Override
	public void Open( String strFileName ) throws Exception
	{
		m_strFileName = strFileName;
		
		m_clsWorkBook = new HSSFWorkbook();
		m_clsSheet = m_clsWorkBook.createSheet( );
		
		m_clsCellStyle = m_clsWorkBook.createCellStyle();
		
		short sBlackIndex = IndexedColors.BLACK.getIndex();
		
		// 박스 라인
		m_clsCellStyle.setBorderBottom( BorderStyle.THIN );
		m_clsCellStyle.setBottomBorderColor( sBlackIndex );
		m_clsCellStyle.setBorderLeft( BorderStyle.THIN );
		m_clsCellStyle.setLeftBorderColor( sBlackIndex );
		m_clsCellStyle.setBorderRight( BorderStyle.THIN );
		m_clsCellStyle.setRightBorderColor( sBlackIndex );
		m_clsCellStyle.setBorderTop( BorderStyle.THIN );
		m_clsCellStyle.setTopBorderColor( sBlackIndex );
		
		// 수평 왼쪽 정렬 & 수직 중앙 정렬
		m_clsCellStyle.setAlignment( HorizontalAlignment.LEFT );
		m_clsCellStyle.setVerticalAlignment( VerticalAlignment.CENTER );
		
		// 멀티라인 스타일
		m_clsCellStyleWrap = m_clsWorkBook.createCellStyle();
		m_clsCellStyleWrap.cloneStyleFrom( m_clsCellStyle );
		m_clsCellStyleWrap.setWrapText( true );
	}

	@Override
	public void AddString( String strText, boolean bNewLine ) throws Exception
	{
		HSSFRow clsRow = m_clsSheet.createRow( m_iRowIndex );
		clsRow.setHeight( m_sRowHeight );
		
		HSSFCell clsCell = clsRow.createCell( 0 );
		clsCell.setCellValue( strText );
		
		m_clsSheet.addMergedRegion( new CellRangeAddress( m_iRowIndex, m_iRowIndex, 0, 4 ) );
		
		++m_iRowIndex;
		
		if( bNewLine ) ++m_iRowIndex;
	}

	@Override
	public void AddDotString( String strText, boolean bNewLine ) throws Exception
	{
		AddString( "    \u2022 " + strText, bNewLine );
	}

	@Override
	public void AddNewLine() throws Exception
	{
		++m_iRowIndex;
	}

	@Override
	public void CreateTable( int iColCount )
	{
		m_iColCount = iColCount;
	}

	@Override
	public void AddCol( String strText )
	{
		if( m_clsRow == null )
		{
			m_clsRow = m_clsSheet.createRow( m_iRowIndex );
			m_clsRow.setHeight( m_sRowHeight );
		}
		else if( m_iColIndex >= m_iColCount )
		{
			++m_iRowIndex;
			m_clsRow = m_clsSheet.createRow( m_iRowIndex );
			m_clsRow.setHeight( m_sRowHeight );
			m_iColIndex = 0;
			m_iRowLineCount = 0;
		}
		
		HSSFCell clsCell = m_clsRow.createCell( m_iColIndex );
		clsCell.setCellValue( strText );
		clsCell.setCellStyle( m_clsCellStyle );
		++m_iColIndex;
	}

	@Override
	public void AddCol( String strText, int iColSpan )
	{
		int iColIndex = m_iColIndex;
		if( iColIndex == m_iColCount ) iColIndex = 0;
		
		AddCol( strText );
		
		for( int i = 1; i < iColSpan; ++i )
		{
			AddCol( "" );
		}
		
		m_clsSheet.addMergedRegion( new CellRangeAddress( m_iRowIndex, m_iRowIndex, iColIndex, iColIndex + iColSpan - 1 ) );
		HSSFCell clsCell = m_clsRow.getCell( iColIndex );
		
		int iPos = 0;
		short iLineCount = 0;
		
		while( ( iPos = strText.indexOf( "\n", iPos ) ) >= 0 )
		{
			iPos += 1;
			++iLineCount;
		}
		
		if( iLineCount >= 2 )
		{
			if( iLineCount > m_iRowLineCount )
			{
				m_clsRow.setHeight( (short)(m_sRowHeight * iLineCount) );
				m_iRowLineCount = iLineCount;
			}
			
			clsCell.setCellStyle( m_clsCellStyleWrap );
		}
		
		m_iColIndex = iColIndex + iColSpan;
	}

	@Override
	public void AddCol( int iInt )
	{
		AddCol( "" + iInt );
	}

	@Override
	public void AddTable() throws Exception
	{
	}

	@Override
	public void Close() throws Exception
	{
		// 컬럼 폭이 자동으로 설정되도록 수정한다.
		for( int iCol = 0; iCol < m_iColCount; ++iCol )
		{
			m_clsSheet.autoSizeColumn( iCol );
		}
		
		// 자동으로 설정된 컬럼 폭을 약간 더 증가시킨다.
		for( int iCol = 0; iCol < m_iColCount; ++iCol )
		{
			int iWidth = m_clsSheet.getColumnWidth( iCol );
			m_clsSheet.setColumnWidth( iCol, iWidth + 400 );
		}
		
		m_clsWorkBook.write( new File( m_strFileName ) );
		m_clsWorkBook.close( );
	}

}
