package com.notice.tiff;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Json
{
	public static void main( String[] args ) throws Exception
	{
		JSONParser clsParser = new JSONParser();
		JSONObject clsJson = (JSONObject)clsParser.parse( "{	\"name\" : \"value\", \"array\" : [ \"1\", \"2\", \"3\" ], \"int\" : 120 }" );
		
		System.out.println( clsJson.toString( ) );
	}
}
