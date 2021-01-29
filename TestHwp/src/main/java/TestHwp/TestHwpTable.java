package TestHwp;

import java.io.UnsupportedEncodingException;

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
import kr.dogfoot.hwplib.object.bodytext.paragraph.charshape.ParaCharShape;
import kr.dogfoot.hwplib.object.bodytext.paragraph.header.ParaHeader;
import kr.dogfoot.hwplib.object.bodytext.paragraph.lineseg.LineSegItem;
import kr.dogfoot.hwplib.object.bodytext.paragraph.lineseg.ParaLineSeg;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.ParaText;
import kr.dogfoot.hwplib.object.docinfo.BorderFill;
import kr.dogfoot.hwplib.object.docinfo.borderfill.BackSlashDiagonalShape;
import kr.dogfoot.hwplib.object.docinfo.borderfill.BorderThickness;
import kr.dogfoot.hwplib.object.docinfo.borderfill.BorderType;
import kr.dogfoot.hwplib.object.docinfo.borderfill.SlashDiagonalShape;
import kr.dogfoot.hwplib.object.docinfo.borderfill.fillinfo.PatternFill;
import kr.dogfoot.hwplib.object.docinfo.borderfill.fillinfo.PatternType;
import kr.dogfoot.hwplib.tool.blankfilemaker.BlankFileMaker;
import kr.dogfoot.hwplib.writer.HWPWriter;

public class TestHwpTable
{
	static long mmToHwp(double mm) 
	{
    return (long) (mm * 72000.0f / 254.0f + 0.5f);
	}
	
	static int getBorderFillIDForTableOutterLine( HWPFile hwpFile )
	{
    BorderFill bf = hwpFile.getDocInfo().addNewBorderFill();
    bf.getProperty().set3DEffect(false);
    bf.getProperty().setShadowEffect(false);
    bf.getProperty().setSlashDiagonalShape(SlashDiagonalShape.None);
    bf.getProperty().setBackSlashDiagonalShape(BackSlashDiagonalShape.None);
    bf.getLeftBorder().setType(BorderType.None);
    bf.getLeftBorder().setThickness(BorderThickness.MM0_5);
    bf.getLeftBorder().getColor().setValue(0x0);
    bf.getRightBorder().setType(BorderType.None);
    bf.getRightBorder().setThickness(BorderThickness.MM0_5);
    bf.getRightBorder().getColor().setValue(0x0);
    bf.getTopBorder().setType(BorderType.None);
    bf.getTopBorder().setThickness(BorderThickness.MM0_5);
    bf.getTopBorder().getColor().setValue(0x0);
    bf.getBottomBorder().setType(BorderType.None);
    bf.getBottomBorder().setThickness(BorderThickness.MM0_5);
    bf.getBottomBorder().getColor().setValue(0x0);
    bf.setDiagonalSort(BorderType.None);
    bf.setDiagonalThickness(BorderThickness.MM0_5);
    bf.getDiagonalColor().setValue(0x0);

    bf.getFillInfo().getType().setPatternFill(true);
    bf.getFillInfo().createPatternFill();
    PatternFill pf = bf.getFillInfo().getPatternFill();
    pf.setPatternType(PatternType.None);
    pf.getBackColor().setValue(-1);
    pf.getPatternColor().setValue(0);

    return hwpFile.getDocInfo().getBorderFillList().size();
	}
	
	static int getBorderFillIDForCell( HWPFile hwpFile ) {
    BorderFill bf = hwpFile.getDocInfo().addNewBorderFill();
    bf.getProperty().set3DEffect(false);
    bf.getProperty().setShadowEffect(false);
    bf.getProperty().setSlashDiagonalShape(SlashDiagonalShape.None);
    bf.getProperty().setBackSlashDiagonalShape(BackSlashDiagonalShape.None);
    bf.getLeftBorder().setType(BorderType.Solid);
    bf.getLeftBorder().setThickness(BorderThickness.MM0_5);
    bf.getLeftBorder().getColor().setValue(0x0);
    bf.getRightBorder().setType(BorderType.Solid);
    bf.getRightBorder().setThickness(BorderThickness.MM0_5);
    bf.getRightBorder().getColor().setValue(0x0);
    bf.getTopBorder().setType(BorderType.Solid);
    bf.getTopBorder().setThickness(BorderThickness.MM0_5);
    bf.getTopBorder().getColor().setValue(0x0);
    bf.getBottomBorder().setType(BorderType.Solid);
    bf.getBottomBorder().setThickness(BorderThickness.MM0_5);
    bf.getBottomBorder().getColor().setValue(0x0);
    bf.setDiagonalSort(BorderType.None);
    bf.setDiagonalThickness(BorderThickness.MM0_5);
    bf.getDiagonalColor().setValue(0x0);

    bf.getFillInfo().getType().setPatternFill(true);
    bf.getFillInfo().createPatternFill();
    PatternFill pf = bf.getFillInfo().getPatternFill();
    pf.setPatternType(PatternType.None);
    pf.getBackColor().setValue(-1);
    pf.getPatternColor().setValue(0);

    return hwpFile.getDocInfo().getBorderFillList().size();
	}

	static void setListHeaderForCell( Cell cell, int borderFillIDForCell, int colIndex, int rowIndex)
	{
    ListHeaderForCell lh = cell.getListHeader();
    lh.setParaCount(1);
    lh.getProperty().setTextDirection(TextDirection.Horizontal);
    lh.getProperty().setLineChange(LineChange.Normal);
    lh.getProperty().setTextVerticalAlignment(TextVerticalAlignment.Center);
    lh.getProperty().setProtectCell(false);
    lh.getProperty().setEditableAtFormMode(false);
    lh.setColIndex(colIndex);
    lh.setRowIndex(rowIndex);
    lh.setColSpan(1);
    lh.setRowSpan(1);
    lh.setWidth(mmToHwp(50.0));
    lh.setHeight(mmToHwp(30.0));
    lh.setLeftMargin(0);
    lh.setRightMargin(0);
    lh.setTopMargin(0);
    lh.setBottomMargin(0);
    lh.setBorderFillId( borderFillIDForCell );
    lh.setTextWidth(mmToHwp(50.0));
    lh.setFieldName("");
	}
	
	static void setParaHeader(Paragraph p)
	{
    ParaHeader ph = p.getHeader();
    ph.setLastInList(true);
    // 셀의 문단 모양을 이미 만들어진 문단 모양으로 사용함
    ph.setParaShapeId(1);
    // 셀의 스타일을이미 만들어진 스타일으로 사용함
    ph.setStyleId((short) 1);
    ph.getDivideSort().setDivideSection(false);
    ph.getDivideSort().setDivideMultiColumn(false);
    ph.getDivideSort().setDividePage(false);
    ph.getDivideSort().setDivideColumn(false);
    ph.setCharShapeCount(1);
    ph.setRangeTagCount(0);
    ph.setLineAlignCount(1);
    ph.setInstanceID(0);
    ph.setIsMergedByTrack(0);
	}

	static void setParaText(Paragraph p, String text2)
	{
    p.createText();
    ParaText pt = p.getText();
    try {
        pt.addString(text2);
    } catch (UnsupportedEncodingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
	}

	static void setParaCharShape(Paragraph p)
	{
    p.createCharShape();

    ParaCharShape pcs = p.getCharShape();
    // 셀의 글자 모양을 이미 만들어진 글자 모양으로 사용함
    pcs.addParaCharShape(0, 1);
	}

	static int ptToLineHeight(double pt) {
    return (int) (pt * 100.0f);
	}

	static void setParaLineSeg(Paragraph p) {
    p.createLineSeg();

    ParaLineSeg pls = p.getLineSeg();
    LineSegItem lsi = pls.addNewLineSegItem();

    lsi.setTextStartPosition(0);
    lsi.setLineVerticalPosition(0);
    lsi.setLineHeight(ptToLineHeight(10.0));
    lsi.setTextPartHeight(ptToLineHeight(10.0));
    lsi.setDistanceBaseLineToLineVerticalPosition(ptToLineHeight(10.0 * 0.85));
    lsi.setLineSpace(ptToLineHeight(3.0));
    lsi.setStartPositionFromColumn(0);
    lsi.setSegmentWidth((int) mmToHwp(50.0));
    lsi.getTag().setFirstSegmentAtLine(true);
    lsi.getTag().setLastSegmentAtLine(true);
}
	
	static void setParagraphForCell( Cell cell, String text)
	{
    Paragraph p = cell.getParagraphList().addNewParagraph();
    setParaHeader(p);
    setParaText(p, text);
    setParaCharShape(p);
    setParaLineSeg(p);
	}
	
	static void WriteTableHwp()
	{
		try
		{
			HWPFile hwpFile = BlankFileMaker.make( );

			Section firstSection = hwpFile.getBodyText().getSectionList().get(0);
      Paragraph firstParagraph = firstSection.getParagraph(0);

      // 문단에서 표 컨트롤의 위치를 표현하기 위한 확장 문자를 넣는다.
      firstParagraph.getText().addExtendCharForTable();

      // 문단에 표 컨트롤 추가한다.
      ControlTable table = (ControlTable) firstParagraph.addNewControl(ControlType.Table);
      
      int zOrder = 0;
      
      CtrlHeaderGso ctrlHeader = table.getHeader();
      ctrlHeader.getProperty().setLikeWord(false);
      ctrlHeader.getProperty().setApplyLineSpace(false);
      ctrlHeader.getProperty().setVertRelTo(VertRelTo.Para);
      ctrlHeader.getProperty().setVertRelativeArrange(RelativeArrange.TopOrLeft);
      ctrlHeader.getProperty().setHorzRelTo(HorzRelTo.Para);
      ctrlHeader.getProperty().setHorzRelativeArrange(RelativeArrange.TopOrLeft);
      ctrlHeader.getProperty().setVertRelToParaLimit(false);
      ctrlHeader.getProperty().setAllowOverlap(false);
      ctrlHeader.getProperty().setWidthCriterion(WidthCriterion.Absolute);
      ctrlHeader.getProperty().setHeightCriterion(HeightCriterion.Absolute);
      ctrlHeader.getProperty().setProtectSize(false);
      ctrlHeader.getProperty().setTextFlowMethod(TextFlowMethod.Tight);
      ctrlHeader.getProperty().setTextHorzArrange(TextHorzArrange.BothSides);
      ctrlHeader.getProperty().setObjectNumberSort(ObjectNumberSort.Table);
      ctrlHeader.setxOffset(mmToHwp(20.0));
      ctrlHeader.setyOffset(mmToHwp(20.0));
      ctrlHeader.setWidth(mmToHwp(100.0));
      ctrlHeader.setHeight(mmToHwp(60.0));
      ctrlHeader.setzOrder(zOrder++);
      ctrlHeader.setOutterMarginLeft(0);
      ctrlHeader.setOutterMarginRight(0);
      ctrlHeader.setOutterMarginTop(0);
      ctrlHeader.setOutterMarginBottom(0);
      
      // 2 x 2 테이블을 생성한다.
      Table tableRecord = table.getTable();
      tableRecord.getProperty().setDivideAtPageBoundary(DivideAtPageBoundary.DivideByCell);
      tableRecord.getProperty().setAutoRepeatTitleRow(false);
      tableRecord.setRowCount(2);
      tableRecord.setColumnCount(2);
      tableRecord.setCellSpacing(0);
      tableRecord.setLeftInnerMargin(0);
      tableRecord.setRightInnerMargin(0);
      tableRecord.setTopInnerMargin(0);
      tableRecord.setBottomInnerMargin(0);
      tableRecord.setBorderFillId(getBorderFillIDForTableOutterLine(hwpFile));
      tableRecord.getCellCountOfRowList().add(2);
      tableRecord.getCellCountOfRowList().add(2);
			
      // 
      int borderFillIDForCell = getBorderFillIDForCell( hwpFile );
      
      // 첫번째 줄을 추가한다.
      Row row = table.addNewRow();
      
      Cell cell = row.addNewCell();
      setListHeaderForCell( cell, borderFillIDForCell, 0, 0 );
      setParagraphForCell( cell, "한글 #1" );
      
      cell = row.addNewCell();
      setListHeaderForCell( cell, borderFillIDForCell, 1, 0 );
      setParagraphForCell( cell, "한글 #2" );
      
      // 두번째 줄을 추가한다.
      row = table.addNewRow();
      
      cell = row.addNewCell();
      setListHeaderForCell( cell, borderFillIDForCell, 0, 1 );
      setParagraphForCell( cell, "한글 #3" );
      
      cell = row.addNewCell();
      setListHeaderForCell( cell, borderFillIDForCell, 1, 1 );
      setParagraphForCell( cell, "한글 #4" );
			
			HWPWriter.toFile(hwpFile, "c:/temp/2.hwp" );
		}
		catch( Exception e )
		{

		}
	}
	
	public static void main( String[] args )
	{
		WriteTableHwp();
	}
}
