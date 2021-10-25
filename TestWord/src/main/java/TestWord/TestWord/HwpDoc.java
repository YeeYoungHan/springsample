package TestWord.TestWord;

import java.io.UnsupportedEncodingException;
import java.util.List;

import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.object.bodytext.Section;
import kr.dogfoot.hwplib.object.bodytext.control.ControlTable;
import kr.dogfoot.hwplib.object.bodytext.control.ControlType;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.CtrlHeaderGso;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.HeightCriterion;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.HorzRelTo;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.ObjectNumberSort;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.RelativeArrange;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.TextFlowMethod;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.TextHorzArrange;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.VertRelTo;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.WidthCriterion;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.sectiondefine.TextDirection;
import kr.dogfoot.hwplib.object.bodytext.control.gso.textbox.LineChange;
import kr.dogfoot.hwplib.object.bodytext.control.gso.textbox.TextVerticalAlignment;
import kr.dogfoot.hwplib.object.bodytext.control.table.Cell;
import kr.dogfoot.hwplib.object.bodytext.control.table.DivideAtPageBoundary;
import kr.dogfoot.hwplib.object.bodytext.control.table.ListHeaderForCell;
import kr.dogfoot.hwplib.object.bodytext.control.table.Row;
import kr.dogfoot.hwplib.object.bodytext.control.table.Table;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.object.bodytext.paragraph.ParagraphList;
import kr.dogfoot.hwplib.object.bodytext.paragraph.charshape.ParaCharShape;
import kr.dogfoot.hwplib.object.bodytext.paragraph.header.ParaHeader;
import kr.dogfoot.hwplib.object.bodytext.paragraph.lineseg.LineSegItem;
import kr.dogfoot.hwplib.object.bodytext.paragraph.lineseg.ParaLineSeg;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.ParaText;
import kr.dogfoot.hwplib.object.docinfo.BorderFill;
import kr.dogfoot.hwplib.object.docinfo.CharShape;
import kr.dogfoot.hwplib.object.docinfo.FaceName;
import kr.dogfoot.hwplib.object.docinfo.ParaShape;
import kr.dogfoot.hwplib.object.docinfo.borderfill.BackSlashDiagonalShape;
import kr.dogfoot.hwplib.object.docinfo.borderfill.BorderThickness;
import kr.dogfoot.hwplib.object.docinfo.borderfill.BorderType;
import kr.dogfoot.hwplib.object.docinfo.borderfill.SlashDiagonalShape;
import kr.dogfoot.hwplib.object.docinfo.borderfill.fillinfo.PatternFill;
import kr.dogfoot.hwplib.object.docinfo.borderfill.fillinfo.PatternType;
import kr.dogfoot.hwplib.object.docinfo.charshape.EmphasisSort;
import kr.dogfoot.hwplib.object.docinfo.charshape.OutterLineSort;
import kr.dogfoot.hwplib.object.docinfo.charshape.ShadowSort;
import kr.dogfoot.hwplib.object.docinfo.charshape.UnderLineSort;
import kr.dogfoot.hwplib.object.docinfo.parashape.Alignment;

public class HwpDoc
{
	HWPFile m_clsHwpFile;
	
	int m_iBorderFillID;
	int zOrder = 0;
	
	String m_strFontName = "굴림";
	int m_iFontSize = 7;
	
	int m_iFaceName;
	int m_iCharShape;
	
	int m_iPragraph = 0;
	
	public HwpDoc( HWPFile clsHwpFile )
	{
		m_clsHwpFile = clsHwpFile;
		m_iFaceName = CreateFaceName( );
		m_iCharShape = CreateCharShape( false );
	}
	
	public void AddTable( List<List<HwpCell>> arrRow )
	{
		int maxRow = arrRow.size( );
		int maxCol = arrRow.get( 0 ).size( );
		
		Paragraph clsParagraph = AddStringParagraph( "" );

		// 문단에서 표 컨트롤의 위치를 표현하기 위한 확장 문자를 넣는다.
		clsParagraph.getText( ).addExtendCharForTable( );

		// 문단에 표 컨트롤 추가한다.
		ControlTable clsTable = (ControlTable)clsParagraph.addNewControl( ControlType.Table );
																																// 생성합니다.
		SetCtrlHeaderRecord( clsTable ); // 테이블의 헤더를 설정합니다.
		SetTableRecord( clsTable, maxRow, maxCol ); // 테이블 레코드 정보를 설정합니다.
		AddCell( clsTable, arrRow ); // 테이블 정보를 등록합니다.
	}
	
	void AddString( String strText ) throws Exception
	{
		Section s = m_clsHwpFile.getBodyText( ).getSectionList( ).get( 0 );

		if( m_iPragraph != 0 )
		{
			AddStringParagraph( strText );
		}
		else
		{
			Paragraph firstParagraph = s.getParagraph( m_iPragraph );
			firstParagraph.getText( ).addString( strText );
		}

		++m_iPragraph;
	}
	
	private Paragraph AddStringParagraph( String strText )
	{
		Paragraph p = m_clsHwpFile.getBodyText( ).getSectionList( ).get( 0 ).addNewParagraph( );
		SetParaTextHeader( p );
		SetParaText( p, strText );
		SetParaTextCharShape( p );
		SetParaTextLineSeg( p );
		
		return p;
	}
	
	private void AddCell( ControlTable table, List<List<HwpCell>> arrRow )
	{
		for( int i = 0; i < arrRow.size( ); i++ )
		{
			// ControlTable에 Row를 추가합니다.
			Row row = table.addNewRow( );

			// 이전에 입력한 셀 정보 리스트에서, Row 정보를 가져옵니다.
			List<HwpCell> cellList = arrRow.get( i );
			for( int j = 0; j < cellList.size( ); j++ )
			{
				// Row의 Cell 리스트를 가져옵니다.
				HwpCell tableCell = cellList.get( j );

				// Hide된 Cell은 건너 뜁니다.
				if( tableCell.m_iType == 0 )
					continue;

				// 위에서 생성한 Row에 Cell을 추가합니다.
				Cell cell = row.addNewCell( );
				
				// Cell Width, Height가 지정되어 있다면 그대로 가져오고, 없다면 Wrap + Padding 된 사이즈를 구합니다.
				double cellWidth = tableCell.m_dbWidth != 0 ? pxTomm( tableCell.m_dbWidth ) : AutoWidth( tableCell.m_strText );
				double cellHeight = tableCell.m_dbHeight != 0 ? pxTomm( tableCell.m_dbHeight ) : AutoHeight( );
				
				// Cell 정보를 입력합니다.
				SetListHeaderForCell( cell, j, i, tableCell.m_iColSpan, tableCell.m_iRowSpan, cellWidth, cellHeight );
				
				// 셀에 텍스트를 지정합니다. ""만 입력하게 될 시, 셀이 그려지지 않습니다. 최소한 띄어쓰기를 넣어 줍니다.
				SetParagraphForCell( cell, tableCell.m_strText + " " );
				
				// 셀의 스타일 또한 지정할 수 있습니다.
				SetCellStyle( m_clsHwpFile, cell, "000000", false );
				
				// 줄 바꿈이 포함된 셀은 한 줄입력으로 하지 않고 줄 바꿈이 포함된 텍스트로 입력합니다.
				SetCellLineBreaker( m_clsHwpFile, cell );
			}
		}
	}
	
	private void SetListHeaderForCell( Cell cell, int colIndex, int rowIndex, int colSpan, int rowSpan, double width, double height )
	{
		ListHeaderForCell lh = cell.getListHeader( );
		lh.setParaCount( 1 );
		lh.getProperty( ).setTextDirection( TextDirection.Horizontal );
		lh.getProperty( ).setLineChange( LineChange.Normal );
		lh.getProperty( ).setTextVerticalAlignment( TextVerticalAlignment.Center );
		lh.getProperty( ).setProtectCell( false );
		lh.getProperty( ).setEditableAtFormMode( false );
		lh.setColIndex( colIndex );
		lh.setRowIndex( rowIndex );
		lh.setColSpan( colSpan );
		lh.setRowSpan( rowSpan );
		lh.setWidth( mmToHwp( width ) );
		lh.setHeight( mmToHwp( height ) );
		lh.setLeftMargin( 0 );
		lh.setRightMargin( 0 );
		lh.setTopMargin( 0 );
		lh.setBottomMargin( 0 );
		lh.setBorderFillId( m_iBorderFillID );
		lh.setTextWidth( mmToHwp( width ) );
		lh.setFieldName( "" );
	}

	private void SetParagraphForCell( Cell cell, String text )
	{
		Paragraph p = cell.getParagraphList( ).addNewParagraph( );
		SetParaHeader( p );
		SetParaText( p, text );
		SetParaLineSeg( p );
	}
	
	private void SetCellStyle( HWPFile m_clsHwpFile, Cell c, String color, boolean bold )
	{
		ParagraphList paragraphs = c.getParagraphList( );
		for( int i = 0; i < paragraphs.getParagraphCount( ); i++ )
		{
			Paragraph p = paragraphs.getParagraph( i );
			ParaCharShape pcs = p.getCharShape( );
			if( pcs == null )
			{
				p.createCharShape( ); // 글자 모양이 없다면, 글자 모양을 담을 공간을 생성하여야 합니다.
				pcs = p.getCharShape( );
			}
			
			// 글자 모양에 지정한 폰트, 크기, bold를 지정합니다.
			pcs.addParaCharShape( 0, CreateCharShape( m_clsHwpFile, color, m_strFontName, m_iFontSize, bold ) );
		}
	}
	
	private void SetCellLineBreaker( HWPFile m_clsHwpFile, Cell c )
	{
		ParagraphList paragraphs = c.getParagraphList( );
		for( int i = 0; i < paragraphs.getParagraphCount( ); i++ )
		{
			Paragraph p = paragraphs.getParagraph( i );
			ParaHeader ph = p.getHeader( );
			ParaShape paraShape = m_clsHwpFile.getDocInfo( ).addNewParaShape( );
			paraShape.getProperty1( ).setAlignment( Alignment.Center ); // 가운데정렬
			paraShape.setLineSpace( 100 ); // 줄바꿈이 일어날 위치 지정.
			int id = m_clsHwpFile.getDocInfo( ).getParaShapeList( ).size( ) - 1;
			ph.setParaShapeId( id );
		}
	}
	
	private void SetParaHeader( Paragraph p )
	{
		ParaHeader ph = p.getHeader( );
		ph.setLastInList( true );
		
		// 셀의 문단 모양을 이미 만들어진 문단 모양으로 사용함
		ph.setParaShapeId( -1 );
		
		// 셀의 스타일을 이미 만들어진 스타일으로 사용하려면 스타일 ID를 넣어주세요.
		// ph.setStyleId((short) 1);
		ph.getDivideSort( ).setDivideSection( false );
		ph.getDivideSort( ).setDivideMultiColumn( false );
		ph.getDivideSort( ).setDividePage( false );
		ph.getDivideSort( ).setDivideColumn( false );
		ph.setCharShapeCount( 1 );
		ph.setRangeTagCount( 0 );
		ph.setLineAlignCount( 1 );
		ph.setInstanceID( 0 );
		ph.setIsMergedByTrack( 0 );
	}

	private void SetParaText( Paragraph p, String text2 )
	{
		p.createText( );
		ParaText pt = p.getText( );
		try
		{
			pt.addString( text2 );
		}
		catch( UnsupportedEncodingException e )
		{
			e.printStackTrace( );
		}
	}
	
	private void SetParaLineSeg( Paragraph p )
	{
		p.createLineSeg( );

		ParaLineSeg pls = p.getLineSeg( );
		LineSegItem lsi = pls.addNewLineSegItem( );

		lsi.setTextStartPosition( 0 );
		lsi.setLineVerticalPosition( 0 );
		lsi.setLineHeight( ptToLineHeight( 10.0 ) );
		lsi.setTextPartHeight( ptToLineHeight( 10.0 ) );
		lsi.setDistanceBaseLineToLineVerticalPosition( ptToLineHeight( 10.0 * 0.85 ) );
		lsi.setLineSpace( ptToLineHeight( 3.0 ) );
		lsi.setStartPositionFromColumn( 0 );
		lsi.setSegmentWidth( (int)mmToHwp( 50.0 ) );
		lsi.getTag( ).setFirstSegmentAtLine( true );
		lsi.getTag( ).setLastSegmentAtLine( true );
	}
	
	private void SetCtrlHeaderRecord( ControlTable table )
	{
		CtrlHeaderGso ctrlHeader = table.getHeader( );
		ctrlHeader.getProperty( ).setLikeWord( false );
		ctrlHeader.getProperty( ).setApplyLineSpace( false );
		ctrlHeader.getProperty( ).setVertRelTo( VertRelTo.Para );
		ctrlHeader.getProperty( ).setVertRelativeArrange( RelativeArrange.TopOrLeft );
		ctrlHeader.getProperty( ).setHorzRelTo( HorzRelTo.Para );
		ctrlHeader.getProperty( ).setHorzRelativeArrange( RelativeArrange.TopOrLeft );
		ctrlHeader.getProperty( ).setVertRelToParaLimit( false );
		ctrlHeader.getProperty( ).setAllowOverlap( false );
		ctrlHeader.getProperty( ).setWidthCriterion( WidthCriterion.Absolute );
		ctrlHeader.getProperty( ).setHeightCriterion( HeightCriterion.Absolute );
		ctrlHeader.getProperty( ).setProtectSize( false );
		ctrlHeader.getProperty( ).setTextFlowMethod( TextFlowMethod.Tight );
		ctrlHeader.getProperty( ).setTextHorzArrange( TextHorzArrange.BothSides );
		ctrlHeader.getProperty( ).setObjectNumberSort( ObjectNumberSort.Table );
		ctrlHeader.setxOffset( mmToHwp( 0.0 ) ); // 좌표 X 위치
		ctrlHeader.setyOffset( mmToHwp( 10.0 ) ); // 좌표 Y 위치
		ctrlHeader.setWidth( mmToHwp( 60.0 ) ); // 테이블의 너비, 조정하여도 셀 크기만큼 동적으로 늘어 납니다.
		ctrlHeader.setHeight( mmToHwp( 60.0 ) ); // 테이블의 높이, 위와 같습니다.
		
		// overlap 인덱스를 설정합니다.
		ctrlHeader.setzOrder( zOrder++ );
		
		// 여백을 설정합니다. 좌, 우, 상, 하
		ctrlHeader.setOutterMarginLeft( 0 );
		ctrlHeader.setOutterMarginRight( 0 );
		ctrlHeader.setOutterMarginTop( 0 );
		ctrlHeader.setOutterMarginBottom( 0 );
	}

	private void SetTableRecord( ControlTable table, int rowCount, int colCount )
	{
		Table tableRecord = table.getTable( );
		tableRecord.getProperty( ).setDivideAtPageBoundary( DivideAtPageBoundary.DivideByCell );
		tableRecord.getProperty( ).setAutoRepeatTitleRow( false );
		tableRecord.setRowCount( rowCount ); // 총 행의 개수
		tableRecord.setColumnCount( colCount ); // 총 열의 개수
		tableRecord.setCellSpacing( 0 );
		tableRecord.setLeftInnerMargin( 0 );
		tableRecord.setRightInnerMargin( 0 );
		tableRecord.setTopInnerMargin( 0 );
		tableRecord.setBottomInnerMargin( 0 );

		// 테이블의 테두리를 설정합니다.
		// 여기서 테두리를 설정하셔도, 맞닫는 각 셀의 테두리도 설정하셔야 적용됩니다.
		m_iBorderFillID = GetBorderFillIDForCell( );
		tableRecord.setBorderFillId( m_iBorderFillID );
	}
	
	private int GetBorderFillIDForCell()
	{
		BorderFill bf = m_clsHwpFile.getDocInfo( ).addNewBorderFill( );
		bf.getProperty( ).set3DEffect( false );
		bf.getProperty( ).setShadowEffect( false );
		bf.getProperty( ).setSlashDiagonalShape( SlashDiagonalShape.None );
		bf.getProperty( ).setBackSlashDiagonalShape( BackSlashDiagonalShape.None );
		bf.getLeftBorder( ).setType( BorderType.Solid ); // BorderType.Solid = 실선
		bf.getLeftBorder( ).setThickness( BorderThickness.MM0_12 ); // 기본 두께
		bf.getLeftBorder( ).getColor( ).setValue( 0x0 );
		bf.getRightBorder( ).setType( BorderType.Solid );
		bf.getRightBorder( ).setThickness( BorderThickness.MM0_12 );
		bf.getRightBorder( ).getColor( ).setValue( 0x0 );
		bf.getTopBorder( ).setType( BorderType.Solid );
		bf.getTopBorder( ).setThickness( BorderThickness.MM0_12 );
		bf.getTopBorder( ).getColor( ).setValue( 0x0 );
		bf.getBottomBorder( ).setType( BorderType.Solid );
		bf.getBottomBorder( ).setThickness( BorderThickness.MM0_12 );
		bf.getBottomBorder( ).getColor( ).setValue( 0x0 );
		bf.setDiagonalSort( BorderType.Solid );
		bf.setDiagonalThickness( BorderThickness.MM0_12 );
		bf.getDiagonalColor( ).setValue( 0x0 );

		bf.getFillInfo( ).getType( ).setPatternFill( true );
		bf.getFillInfo( ).createPatternFill( );
		PatternFill pf = bf.getFillInfo( ).getPatternFill( );
		pf.setPatternType( PatternType.None );
		pf.getBackColor( ).setValue( -1 );
		pf.getPatternColor( ).setValue( 0 );

		return m_clsHwpFile.getDocInfo( ).getBorderFillList( ).size( );
	}
	
	private void SetParaTextHeader( Paragraph p )
	{
		ParaHeader ph = p.getHeader( );
		ph.setLastInList( true );
		// 문단 모양을 이미 만들어진 문단 모양으로 사용함
		ph.setParaShapeId( 1 );
		// 이미 만들어진 스타일으로 사용함
		ph.setStyleId( (short)1 );
		ph.getDivideSort( ).setDivideSection( false );
		ph.getDivideSort( ).setDivideMultiColumn( false );
		ph.getDivideSort( ).setDividePage( false );
		ph.getDivideSort( ).setDivideColumn( false );
		ph.setCharShapeCount( 1 );
		ph.setRangeTagCount( 0 );
		ph.setLineAlignCount( 1 );
		ph.setInstanceID( 0 );
		ph.setIsMergedByTrack( 0 );
	}
	
	private void SetParaTextCharShape( Paragraph p )
	{
		int paragraphStartPos = 0;

		p.createCharShape( );

		ParaCharShape pcs = p.getCharShape( );
		pcs.addParaCharShape( paragraphStartPos, m_iCharShape );
	}
	
	private void SetParaTextLineSeg( Paragraph p )
	{
		p.createLineSeg( );

		ParaLineSeg pls = p.getLineSeg( );
		LineSegItem lsi = pls.addNewLineSegItem( );

		lsi.setTextStartPosition( 0 );
		lsi.setLineVerticalPosition( 0 );
		lsi.setLineHeight( ptToLineHeight( 11.0 ) );
		lsi.setTextPartHeight( ptToLineHeight( 11.0 ) );
		lsi.setDistanceBaseLineToLineVerticalPosition( ptToLineHeight( 11.0 * 0.85 ) );
		lsi.setLineSpace( ptToLineHeight( 4.0 ) );
		lsi.setStartPositionFromColumn( 0 );
		lsi.setSegmentWidth( (int)mmToHwp( 50.0 ) );
		lsi.getTag( ).setFirstSegmentAtLine( true );
		lsi.getTag( ).setLastSegmentAtLine( true );
	}
	
	// 바탕 폰트를 위한 FaceName 객체를 생성한다.(create FaceName Object for 'Batang' font)
	// '한글' 프로그램에서는 폰트를 적용할 문자를 6개의 부분으로 나눈다.(In 'Hangul' programs, the characters to be applied to the font are divided into six parts.)
	private int CreateFaceName()
	{
		FaceName fn;

		// 한글 부분을 위한 FaceName 객체를 생성한다. (create FaceName Object for hangul part.)
		fn = m_clsHwpFile.getDocInfo( ).addNewHangulFaceName( );
		SetFaceName( fn );

		// 영어 부분을 위한 FaceName 객체를 생성한다. (create FaceName Object for english part.)
		fn = m_clsHwpFile.getDocInfo( ).addNewEnglishFaceName( );
		SetFaceName( fn );

		// 한자 부분을 위한 FaceName 객체를 생성한다. (create FaceName Object for hanja(Chinese)
		// part.)
		fn = m_clsHwpFile.getDocInfo( ).addNewHanjaFaceName( );
		SetFaceName( fn );

		// 일본어 부분을 위한 FaceName 객체를 생성한다.(create FaceName Object for japanse part.)
		fn = m_clsHwpFile.getDocInfo( ).addNewJapaneseFaceName( );
		SetFaceName( fn );

		// 기타 문자 부분을 위한 FaceName 객체를 생성한다.(create FaceName Object for etc part.)
		fn = m_clsHwpFile.getDocInfo( ).addNewEtcFaceName( );
		SetFaceName( fn );

		// 기호 문자 부분을 위한 FaceName 객체를 생성한다.(create FaceName Object for symbol part.)
		fn = m_clsHwpFile.getDocInfo( ).addNewSymbolFaceName( );
		SetFaceName( fn );

		// 사용자 정의 문자 부분을 위한 FaceName 객체를 생성한다.(create FaceName Object for user part.)
		fn = m_clsHwpFile.getDocInfo( ).addNewUserFaceName( );
		SetFaceName( fn );

		return m_clsHwpFile.getDocInfo( ).getHangulFaceNameList( ).size( ) - 1;
	}
	
	private void SetFaceName( FaceName fn )
	{
		String fontName = "바탕";
		fn.getProperty( ).setHasBaseFont( false );
		fn.getProperty( ).setHasFontInfo( false );
		fn.getProperty( ).setHasSubstituteFont( false );
		fn.setName( fontName );
	}
	
	private int CreateCharShape( boolean bold )
	{
		CharShape cs = m_clsHwpFile.getDocInfo( ).addNewCharShape( );
		// 바탕 폰트를 위한 FaceName 객체를 링크한다. (link FaceName Object for 'Batang' font.)
		cs.getFaceNameIds( ).setForAll( m_iFaceName );

		cs.getRatios( ).setForAll( (short)100 );
		cs.getCharSpaces( ).setForAll( (byte)0 );
		cs.getRelativeSizes( ).setForAll( (short)100 );
		cs.getCharOffsets( ).setForAll( (byte)0 );
		cs.setBaseSize( ptToBaseSize( 11 ) );

		cs.getProperty( ).setItalic( false );
		cs.getProperty( ).setBold( bold );
		cs.getProperty( ).setUnderLineSort( UnderLineSort.None );
		cs.getProperty( ).setOutterLineSort( OutterLineSort.None );
		cs.getProperty( ).setShadowSort( ShadowSort.None );
		cs.getProperty( ).setEmboss( false );
		cs.getProperty( ).setEngrave( false );
		cs.getProperty( ).setSuperScript( false );
		cs.getProperty( ).setSubScript( false );
		cs.getProperty( ).setStrikeLine( false );
		cs.getProperty( ).setEmphasisSort( EmphasisSort.None );
		cs.getProperty( ).setUsingSpaceAppropriateForFont( false );
		cs.getProperty( ).setStrikeLineShape( BorderType.None );
		cs.getProperty( ).setKerning( false );

		cs.setShadowGap1( (byte)0 );
		cs.setShadowGap2( (byte)0 );
		cs.getCharColor( ).setValue( 0x00000000 );
		cs.getUnderLineColor( ).setValue( 0x00000000 );
		cs.getShadeColor( ).setValue( -1 );
		cs.getShadowColor( ).setValue( 0x00b2b2b2 );
		cs.setBorderFillId( 0 );

		return m_clsHwpFile.getDocInfo( ).getCharShapeList( ).size( ) - 1;
	}
	
	private int CreateCharShape( HWPFile m_clsHwpFile, String color, String fontName, int ptSize, boolean bold )
	{
		CharShape cs = m_clsHwpFile.getDocInfo( ).addNewCharShape( );
		// 지정 폰트를 위한 FaceName 객체를 링크
		cs.getFaceNameIds( ).setForAll( CreateFaceName( ) );

		cs.getRatios( ).setForAll( (short)100 );
		cs.getCharSpaces( ).setForAll( (byte)0 );
		cs.getRelativeSizes( ).setForAll( (short)100 );
		cs.getCharOffsets( ).setForAll( (byte)0 );
		cs.setBaseSize( ptToBaseSize( ptSize ) );

		cs.getProperty( ).setItalic( false );
		cs.getProperty( ).setBold( bold );
		cs.getProperty( ).setUnderLineSort( UnderLineSort.None );
		cs.getProperty( ).setOutterLineSort( OutterLineSort.None );
		cs.getProperty( ).setShadowSort( ShadowSort.None );
		cs.getProperty( ).setEmboss( false );
		cs.getProperty( ).setEngrave( false );
		cs.getProperty( ).setSuperScript( false );
		cs.getProperty( ).setSubScript( false );
		cs.getProperty( ).setStrikeLine( false );
		cs.getProperty( ).setEmphasisSort( EmphasisSort.None );
		cs.getProperty( ).setUsingSpaceAppropriateForFont( false );
		cs.getProperty( ).setStrikeLineShape( BorderType.None );
		cs.getProperty( ).setKerning( false );

		cs.setShadowGap1( (byte)0 );
		cs.setShadowGap2( (byte)0 );
		RGBColor rgbColor = RGBColor.HexToRgb( color );
		cs.getCharColor( ).setR( rgbColor.getR( ) );
		cs.getCharColor( ).setG( rgbColor.getG( ) );
		cs.getCharColor( ).setB( rgbColor.getB( ) );
		cs.getUnderLineColor( ).setValue( 0x00000000 );
		cs.getShadeColor( ).setValue( -1 );
		cs.getShadowColor( ).setValue( 0x00b2b2b2 );
		cs.setBorderFillId( 0 );

		return m_clsHwpFile.getDocInfo( ).getCharShapeList( ).size( ) - 1;
	}
	
	private int AutoWidth( String text )
	{
		return text.length( ) * 3;
	}

	private int AutoHeight()
	{
		return 5;
	}
	
	private long mmToHwp( double mm )
	{
		return (long)(mm * 72000.0f / 254.0f + 0.5f);
	}
	
	private int ptToLineHeight( double pt )
	{
		return (int)(pt * 100.0f);
	}
	
	private int ptToBaseSize( int pt )
	{
		return pt * 100;
	}
	
	private double pxTomm( double px )
	{
		return px * 0.264583333;
	}
}
