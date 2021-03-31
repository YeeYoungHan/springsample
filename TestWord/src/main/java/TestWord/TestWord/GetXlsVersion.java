package TestWord.TestWord;

import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class GetXlsVersion 
{
	public static void main( String [] args ) throws Exception
	{
		if( args.length != 1 )
		{
			System.out.println( "[Usage] java GetXlsVersion {excel file path}" );
			return;
		}
		
		HSSFWorkbook workbook = new HSSFWorkbook( new FileInputStream( args[0] ) );
		
		int iVersion = workbook.getDocumentSummaryInformation().getApplicationVersion();
		
		workbook.close();
		
		// 엑셀 프로그램 버전을 출력한다.
		// 버전 정보는 https://blog.naver.com/websearch/222268534563 에 기술되어 있음
		iVersion = ((iVersion&0xFF0000) >> 16 );
		System.out.println( iVersion );
		
		String strVersion = "";
		
		switch( iVersion )
		{
		case 2: strVersion = "Excel 2.0"; break;
		case 3: strVersion = "Excel 3.0"; break;
		case 4: strVersion = "Excel 4.0"; break;
		case 5: strVersion = "Excel 5.0"; break;
		case 7: strVersion = "Excel 95"; break;
		case 8: strVersion = "Excel 97"; break;
		case 9: strVersion = "Excel 2000"; break;
		case 10: strVersion = "Excel 2002"; break;
		case 11: strVersion = "Excel 2003"; break;
		case 12: strVersion = "Excel 2007"; break;
		case 14: strVersion = "Excel 2010"; break;
		case 15: strVersion = "Excel 2013"; break;
		case 16: strVersion = "Excel 2016"; break;
		}
		
		System.out.println( strVersion );
	}
}
