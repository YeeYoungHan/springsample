package TestHwp2;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import kr.dogfoot.hwplib.reader.HWPReader;
import kr.dogfoot.hwplib.writer.HWPWriter;

public class TestHwpTable2
{
	HWPFile hwpFile;

	// 생성된 첫번째 테두리를 가지고 모든 테두리를 똑같이 적용합니다.
	private int borderFillIDForCell;
	private int zOrder = 0;

	// 이 예제는 폰트가 전역으로 설정 합니다.
	// 폰트 이름과, 스타일, pt 사이즈를 입력합니다.
	private String fontName = "굴림";
	private int fontSize = 7;

	private int faceNameIndexForBatang;

	public TestHwpTable2( HWPFile hwpFile )
	{
		this.hwpFile = hwpFile;
		faceNameIndexForBatang = createFaceNameForBatang( );
		charShapeIndexForNormal = createCharShape( false );
	}

	// 바탕 폰트를 위한 FaceName 객체를 생성한다.(create FaceName Object for 'Batang' font)
	// '한글' 프로그램에서는 폰트를 적용할 문자를 6개의 부분으로 나눈다.(In 'Hangul' programs, the characters
	// to be applied to the font are divided into six parts.)
	private int createFaceNameForBatang()
	{
		FaceName fn;

		// 한글 부분을 위한 FaceName 객체를 생성한다. (create FaceName Object for hangul part.)
		fn = hwpFile.getDocInfo( ).addNewHangulFaceName( );
		setFaceNameForBatang( fn );

		// 영어 부분을 위한 FaceName 객체를 생성한다. (create FaceName Object for english part.)
		fn = hwpFile.getDocInfo( ).addNewEnglishFaceName( );
		setFaceNameForBatang( fn );

		// 한자 부분을 위한 FaceName 객체를 생성한다. (create FaceName Object for hanja(Chinese)
		// part.)
		fn = hwpFile.getDocInfo( ).addNewHanjaFaceName( );
		setFaceNameForBatang( fn );

		// 일본어 부분을 위한 FaceName 객체를 생성한다.(create FaceName Object for japanse part.)
		fn = hwpFile.getDocInfo( ).addNewJapaneseFaceName( );
		setFaceNameForBatang( fn );

		// 기타 문자 부분을 위한 FaceName 객체를 생성한다.(create FaceName Object for etc part.)
		fn = hwpFile.getDocInfo( ).addNewEtcFaceName( );
		setFaceNameForBatang( fn );

		// 기호 문자 부분을 위한 FaceName 객체를 생성한다.(create FaceName Object for symbol part.)
		fn = hwpFile.getDocInfo( ).addNewSymbolFaceName( );
		setFaceNameForBatang( fn );

		// 사용자 정의 문자 부분을 위한 FaceName 객체를 생성한다.(create FaceName Object for user
		// part.)
		fn = hwpFile.getDocInfo( ).addNewUserFaceName( );
		setFaceNameForBatang( fn );

		return hwpFile.getDocInfo( ).getHangulFaceNameList( ).size( ) - 1;
	}

	private void setFaceNameForBatang( FaceName fn )
	{
		String fontName = "바탕";
		fn.getProperty( ).setHasBaseFont( false );
		fn.getProperty( ).setHasFontInfo( false );
		fn.getProperty( ).setHasSubstituteFont( false );
		fn.setName( fontName );
	}

	private int createCharShape( boolean bold )
	{
		CharShape cs = hwpFile.getDocInfo( ).addNewCharShape( );
		// 바탕 폰트를 위한 FaceName 객체를 링크한다. (link FaceName Object for 'Batang' font.)
		cs.getFaceNameIds( ).setForAll( faceNameIndexForBatang );

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

		return hwpFile.getDocInfo( ).getCharShapeList( ).size( ) - 1;
	}

	public void makeTable( List<List<TableCellDTO>> rowList )
	{
		int maxRow = rowList.size( );
		int maxCol = rowList.get( 0 ).size( );
		//ControlTable table = createTableControlAtFirstParagraph( ); // 첫 문단에 테이블을
		ControlTable table = CreateTable();
																																// 생성합니다.
		setCtrlHeaderRecord( table ); // 테이블의 헤더를 설정합니다.
		setTableRecord( table, maxRow, maxCol ); // 테이블 레코드 정보를 설정합니다.
		addCell( table, rowList ); // 테이블 정보를 등록합니다.
	}

	/**
	 * 첫 섹션, 첫 번째 문단에 테이블을 생성합니다. 동적으로 i번째 문단에 테이블을 생성하시려면 그 만큼 문단이 필요합니다. 이 예제는
	 * 하나의 테이블만 그리기 위해 첫 번째로 고정합니다.
	 * 
	 * @return
	 */
	/*
	private ControlTable createTableControlAtFirstParagraph()
	{
		Section firstSection = hwpFile.getBodyText( ).getSectionList( ).get( 0 );
		Paragraph firstParagraph = firstSection.getParagraph( 0 );

		// 문단에서 표 컨트롤의 위치를 표현하기 위한 확장 문자를 넣는다.
		firstParagraph.getText( ).addExtendCharForTable( );

		// 문단에 표 컨트롤 추가한다.
		return (ControlTable)firstParagraph.addNewControl( ControlType.Table );
	}
	*/
	
	ControlTable CreateTable()
	{
		Paragraph firstParagraph = createTestParagraph( "" );

		// 문단에서 표 컨트롤의 위치를 표현하기 위한 확장 문자를 넣는다.
		firstParagraph.getText( ).addExtendCharForTable( );

		// 문단에 표 컨트롤 추가한다.
		return (ControlTable)firstParagraph.addNewControl( ControlType.Table );
	}

	private void setCtrlHeaderRecord( ControlTable table )
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
		ctrlHeader.setWidth( mmToHwp( 60.0 ) ); // 테이블의 너비, 조정하여도 셀 크기만큼 동적으로 늘어
																						// 납니다.
		ctrlHeader.setHeight( mmToHwp( 60.0 ) ); // 테이블의 높이, 위와 같습니다.
		// overlap 인덱스를 설정합니다.
		ctrlHeader.setzOrder( zOrder++ );
		// 여백을 설정합니다. 좌, 우, 상, 하
		ctrlHeader.setOutterMarginLeft( 0 );
		ctrlHeader.setOutterMarginRight( 0 );
		ctrlHeader.setOutterMarginTop( 0 );
		ctrlHeader.setOutterMarginBottom( 0 );
	}

	private void setTableRecord( ControlTable table, int rowCount, int colCount )
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
		borderFillIDForCell = getBorderFillIDForCell( );
		tableRecord.setBorderFillId( borderFillIDForCell );
	}

	private void addCell( ControlTable table, List<List<TableCellDTO>> rowList )
	{
		for( int i = 0; i < rowList.size( ); i++ )
		{
			// ControlTable에 Row를 추가합니다.
			Row row = table.addNewRow( );

			// 이전에 입력한 셀 정보 리스트에서, Row 정보를 가져옵니다.
			List<TableCellDTO> cellList = rowList.get( i );
			for( int j = 0; j < cellList.size( ); j++ )
			{
				// Row의 Cell 리스트를 가져옵니다.
				TableCellDTO tableCell = cellList.get( j );

				// Hide된 Cell은 건너 뜁니다.
				if( tableCell.getType( ) == 0 )
					continue;

				// 위에서 생성한 Row에 Cell을 추가합니다.
				Cell cell = row.addNewCell( );
				// Cell Width, Height가 지정되어 있다면 그대로 가져오고, 없다면 Wrap + Padding 된 사이즈를
				// 구합니다.
				double cellWidth = tableCell.getWidth( ) != 0 ? pxTomm( tableCell.getWidth( ) ) : autoWidth( tableCell.getText( ) );
				double cellHeight = tableCell.getHeight( ) != 0 ? pxTomm( tableCell.getHeight( ) ) : autoHeight( );
				// Cell 정보를 입력합니다.
				setListHeaderForCell( cell, j, i, tableCell.getColspan( ), tableCell.getRowspan( ), cellWidth, cellHeight );
				// 셀에 텍스트를 지정합니다. ""만 입력하게 될 시, 셀이 그려지지 않습니다. 최소한 띄어쓰기를 넣어 줍니다.
				setParagraphForCell( cell, tableCell.getText( ) + " " );
				// 셀의 스타일 또한 지정할 수 있습니다.
				setCellStyle( hwpFile, cell, "000000", false );
				// 줄 바꿈이 포함된 셀은 한 줄입력으로 하지 않고 줄 바꿈이 포함된 텍스트로 입력합니다.
				setCellLineBreaker( hwpFile, cell );
			}
		}
	}

	private int getBorderFillIDForCell()
	{
		BorderFill bf = hwpFile.getDocInfo( ).addNewBorderFill( );
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

		return hwpFile.getDocInfo( ).getBorderFillList( ).size( );
	}

	private void setListHeaderForCell( Cell cell, int colIndex, int rowIndex, int colSpan, int rowSpan, double width, double height )
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
		lh.setBorderFillId( borderFillIDForCell );
		lh.setTextWidth( mmToHwp( width ) );
		lh.setFieldName( "" );
	}

	private void setParagraphForCell( Cell cell, String text )
	{
		Paragraph p = cell.getParagraphList( ).addNewParagraph( );
		setParaHeader( p );
		setParaText( p, text );
		// setParaCharShape(p);
		setParaLineSeg( p );
	}

	private void setParaHeader( Paragraph p )
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

	private void setParaText( Paragraph p, String text2 )
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

	private int autoWidth( String text )
	{
		return text.length( ) * 2;
	}

	private int autoHeight()
	{
		return 5;
	}

	private void setCellStyle( HWPFile hwpFile, Cell c, String color, boolean bold )
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
			pcs.addParaCharShape( 0, createCharShape( hwpFile, color, fontName, fontSize, bold ) );
		}
	}

	private void setParaLineSeg( Paragraph p )
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

	private void setCellLineBreaker( HWPFile hwpFile, Cell c )
	{
		ParagraphList paragraphs = c.getParagraphList( );
		for( int i = 0; i < paragraphs.getParagraphCount( ); i++ )
		{
			Paragraph p = paragraphs.getParagraph( i );
			ParaHeader ph = p.getHeader( );
			ParaShape paraShape = hwpFile.getDocInfo( ).addNewParaShape( );
			paraShape.getProperty1( ).setAlignment( Alignment.Center ); // 가운데정렬
			paraShape.setLineSpace( 100 ); // 줄바꿈이 일어날 위치 지정.
			int id = hwpFile.getDocInfo( ).getParaShapeList( ).size( ) - 1;
			ph.setParaShapeId( id );
		}
	}

	/**
	 * 폰트 pt 사이즈를 줄 높이 사이즈로 변환 합니다.
	 * 
	 * @param pt
	 * @return int
	 */
	private int ptToLineHeight( double pt )
	{
		return (int)(pt * 100.0f);
	}

	/**
	 * mm를 Hwp에 포맷에 맞게 변환 합니다.
	 * 
	 * @param mm
	 * @return long
	 */
	private long mmToHwp( double mm )
	{
		return (long)(mm * 72000.0f / 254.0f + 0.5f);
	}

	/**
	 * px값을 mm로 변환합니다.
	 * 
	 * @param px
	 * @return double
	 */
	private double pxTomm( double px )
	{
		return px * 0.264583333;
	}

	/**
	 * 기본 pt사이즈를 지정합니다.
	 * 
	 * @param pt
	 * @return int
	 */
	private int ptToBaseSize( int pt )
	{
		return pt * 100;
	}

	/**
	 * HTML의 HexColor값을 RGB로 변환하기 위한 메소드 입니다.
	 * 
	 * @param colorStr
	 * @return RGBColor
	 */
	private RGBColor hexToRgb( String colorStr )
	{
		return new RGBColor( Short.valueOf( colorStr.substring( 0, 2 ), 16 ), Short.valueOf( colorStr.substring( 2, 4 ), 16 ), Short.valueOf( colorStr.substring( 4, 6 ), 16 ) );
	}

	private int createCharShape( HWPFile hwpFile, String color, String fontName, int ptSize, boolean bold )
	{
		CharShape cs = hwpFile.getDocInfo( ).addNewCharShape( );
		// 지정 폰트를 위한 FaceName 객체를 링크
		cs.getFaceNameIds( ).setForAll( createFaceName( hwpFile, fontName ) );

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
		RGBColor rgbColor = hexToRgb( color );
		cs.getCharColor( ).setR( rgbColor.getR( ) );
		cs.getCharColor( ).setG( rgbColor.getG( ) );
		cs.getCharColor( ).setB( rgbColor.getB( ) );
		cs.getUnderLineColor( ).setValue( 0x00000000 );
		cs.getShadeColor( ).setValue( -1 );
		cs.getShadowColor( ).setValue( 0x00b2b2b2 );
		cs.setBorderFillId( 0 );

		return hwpFile.getDocInfo( ).getCharShapeList( ).size( ) - 1;
	}

	// 바탕 폰트를 위한 FaceName 객체를 생성한다.(create FaceName Object for 'Batang' font)
	// '한글' 프로그램에서는 폰트를 적용할 문자를 6개의 부분으로 나눈다.(In 'Hangul' programs, the characters
	// to be applied to the font are divided into six parts.)
	private int createFaceName( HWPFile hwpFile, String fontName )
	{
		FaceName fn;

		// 한글 부분을 위한 FaceName 객체를 생성한다. (create FaceName Object for hangul part.)
		fn = hwpFile.getDocInfo( ).addNewHangulFaceName( );
		setFaceName( fn, fontName );

		// 영어 부분을 위한 FaceName 객체를 생성한다. (create FaceName Object for english part.)
		fn = hwpFile.getDocInfo( ).addNewEnglishFaceName( );
		setFaceName( fn, fontName );

		// 한자 부분을 위한 FaceName 객체를 생성한다. (create FaceName Object for hanja(Chinese)
		// part.)
		fn = hwpFile.getDocInfo( ).addNewHanjaFaceName( );
		setFaceName( fn, fontName );

		// 일본어 부분을 위한 FaceName 객체를 생성한다.(create FaceName Object for japanse part.)
		fn = hwpFile.getDocInfo( ).addNewJapaneseFaceName( );
		setFaceName( fn, fontName );

		// 기타 문자 부분을 위한 FaceName 객체를 생성한다.(create FaceName Object for etc part.)
		fn = hwpFile.getDocInfo( ).addNewEtcFaceName( );
		setFaceName( fn, fontName );

		// 기호 문자 부분을 위한 FaceName 객체를 생성한다.(create FaceName Object for symbol part.)
		fn = hwpFile.getDocInfo( ).addNewSymbolFaceName( );
		setFaceName( fn, fontName );

		// 사용자 정의 문자 부분을 위한 FaceName 객체를 생성한다.(create FaceName Object for user
		// part.)
		fn = hwpFile.getDocInfo( ).addNewUserFaceName( );
		setFaceName( fn, fontName );

		return hwpFile.getDocInfo( ).getHangulFaceNameList( ).size( ) - 1;
	}

	private void setFaceName( FaceName fn, String fontName )
	{
		fn.getProperty( ).setHasBaseFont( false );
		fn.getProperty( ).setHasFontInfo( false );
		fn.getProperty( ).setHasSubstituteFont( false );
		fn.setName( fontName );
	}

	Paragraph createTestParagraph( String strText )
	{
		Paragraph p = hwpFile.getBodyText( ).getSectionList( ).get( 0 ).addNewParagraph( );
		setParaHeader2( p );
		setParaText( p, strText );
		setParaCharShape2( p );
		setParaLineSeg2( p );
		
		return p;
	}

	void setParaHeader2( Paragraph p )
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

	private int charShapeIndexForNormal;

	private void setParaCharShape2( Paragraph p )
	{
		int paragraphStartPos = 0;

		p.createCharShape( );

		ParaCharShape pcs = p.getCharShape( );
		pcs.addParaCharShape( paragraphStartPos, charShapeIndexForNormal );
	}

	private void setParaLineSeg2( Paragraph p )
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

	static int m_iPragraph = 0;

	static void PrintText( TestHwpTable2 clsTable, HWPFile hwpFile, String strText ) throws Exception
	{
		Section s = hwpFile.getBodyText( ).getSectionList( ).get( 0 );

		if( m_iPragraph != 0 )
		{
			clsTable.createTestParagraph( strText );
		}
		else
		{
			Paragraph firstParagraph = s.getParagraph( m_iPragraph );
			firstParagraph.getText( ).addString( strText );
		}

		++m_iPragraph;
	}

	public static void main( String[] args ) throws Exception
	{
		List<List<TableCellDTO>> arrRow = new ArrayList<List<TableCellDTO>>( );

		for( int iRow = 0; iRow < 5; ++iRow )
		{
			List<TableCellDTO> arrCol = new ArrayList<TableCellDTO>( );

			for( int iCol = 0; iCol < 8; ++iCol )
			{
				TableCellDTO clsCell = new TableCellDTO( );
				clsCell.setText( "행(" + iRow + ") 열(" + iCol + ")" );

				arrCol.add( clsCell );
			}

			arrRow.add( arrCol );
		}

		String strHwpPath = System.getProperty( "user.dir" ) + "/test-blank.hwp";
		HWPFile hwpFile = HWPReader.fromFile( strHwpPath );

		TestHwpTable2 clsTable = new TestHwpTable2( hwpFile );

		PrintText( clsTable, clsTable.hwpFile, "주제" );
		PrintText( clsTable, clsTable.hwpFile, "부주제" );
		PrintText( clsTable, clsTable.hwpFile, "* 날짜" );

		clsTable.makeTable( arrRow );

		HWPWriter.toFile( clsTable.hwpFile, "c:/temp/3.hwp" );
	}
}
