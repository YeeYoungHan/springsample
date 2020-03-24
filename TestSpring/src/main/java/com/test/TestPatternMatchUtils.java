package com.test;

import org.springframework.util.PatternMatchUtils;

public class TestPatternMatchUtils
{
	public static void Test( String strPattern, String strInput, boolean bResult )
	{
		if( PatternMatchUtils.simpleMatch( strPattern, strInput ) != bResult )
		{
			System.out.println( "pattern[" + strPattern + "] input[" + strInput + "] result[" + bResult + "] error" );
		}
	}
	
	public static void main( String [] args )
	{
		Test( "*test", "Mytest", true );
		Test( "*test", "Mytestis", false );
		Test( "*test*", "Mytestis", true );
		Test( "*test", "MyTest", false );
	}
}
