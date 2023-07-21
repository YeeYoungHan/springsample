/* 
 * Copyright (C) 2018 Yee Young Han <websearch@naver.com> (http://blog.naver.com/websearch)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
 */

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
