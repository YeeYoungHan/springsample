package kr.dogfoot.hwplib.sample;

import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.reader.HWPReader;
import kr.dogfoot.hwplib.tool.textextractor.TextExtractMethod;
import kr.dogfoot.hwplib.tool.textextractor.TextExtractor;
import kr.dogfoot.hwplib.writer.HWPWriter;

import java.io.File;


/**
 * 파일에 전체 텍스트를 추출하는 샘플 프로그램
 */
public class Extracting_Text {
    public static void main(String[] args) throws Exception {
    	
    	/*
        test("blank.hwp");
        test("etc.hwp");
        test("ole.hwp");
        test("각주미주.hwp");
        test("구버전(5.0.2.2) Picture 컨트롤.hwp");
        test("그림.hwp");
        test("글상자.hwp");
        test("글상자-압축.hwp");
        test("글자겹침.hwp");
        test("다각형.hwp");
        test("덧말.hwp");
        test("머리글꼬리글.hwp");
        test("묶음.hwp");
        test("문단번호 1-10 수준.hwp");
        test("바탕쪽.hwp");
        test("새번호지정.hwp");
        test("선-사각형-타원.hwp");
        test("수식.hwp");
        test("숨은설명.hwp");
        test("이미지추가.hwp");
        test("차트.hwp");
        test("책갈피.hwp");
        test("페이지숨김.hwp");
        test("표.hwp");
        test("필드.hwp");
        test("필드-누름틀.hwp");
        test("호-곡선.hwp");
      */
    	
    	test( "sample_hwp" + File.separator + "a.hwp" );
    }

    private static void test(String filename)
            throws Exception {
        TextExtractMethod tem = TextExtractMethod.InsertControlTextBetweenParagraphText;
        HWPFile hwpFile = HWPReader.fromFile(filename );
        System.out.println(filename + "  읽기 성공 !!");
        System.out.println();
        String hwpText = TextExtractor.extract(hwpFile, tem);
        System.out.println(hwpText);
        System.out.println("========================================================");
        
        HWPWriter.toFile(hwpFile, "sample_hwp" + File.separator + "b.hwp" );
    }

    /*
    private static String fullPath(String filename) {
        return "sample_hwp" + File.separator + "basic" + File.separator + filename;
    }
		*/

}
