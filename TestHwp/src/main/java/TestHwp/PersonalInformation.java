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

package TestHwp;

public class PersonalInformation
{
	public static boolean CheckDigit( String strBuf, int iStart, int iEnd )
	{
		char c;
		
		for( int i = iStart; i < iEnd; ++i )
		{
			c = strBuf.charAt( i );
			
			if( c < '0' || c > '9' ) return false;
		}
		
		return true;
	}
	
	public static boolean Check( String strBuf )
	{
		int iLen = strBuf.length( );
		
		if( iLen == 14 )
		{
			// 주민등록번호 검사
			if( CheckDigit( strBuf, 0, 6 ) == false ) return false;
			if( strBuf.charAt( 6 ) != '-' ) return false;
			if( CheckDigit( strBuf, 7, 14 ) == false ) return false;
			
			return true;
		}
		else if( iLen == 13 )
		{
			// 010-1234-5678 검사
			if( CheckDigit( strBuf, 0, 3 ) == false ) return false;
			if( strBuf.charAt( 3 ) != '-' ) return false;
			if( CheckDigit( strBuf, 4, 8 ) == false ) return false;
			if( strBuf.charAt( 8 ) != '-' ) return false;
			if( CheckDigit( strBuf, 9, 13 ) == false ) return false;
			
			return true;
		}
		else if( iLen == 12 )
		{
			if( strBuf.charAt( 2 ) == '-' )
			{
				// 02-1234-5678 검사
				if( CheckDigit( strBuf, 0, 2 ) == false ) return false;
				if( strBuf.charAt( 2 ) != '-' ) return false;
				if( CheckDigit( strBuf, 3, 7 ) == false ) return false;
				if( strBuf.charAt( 7 ) != '-' ) return false;
				if( CheckDigit( strBuf, 8, 12 ) == false ) return false;
				
				return true;
			}
			else if( strBuf.charAt( 3 ) == '-' )
			{
				// 032-123-4567 검사
				if( CheckDigit( strBuf, 0, 3 ) == false ) return false;
				if( strBuf.charAt( 3 ) != '-' ) return false;
				if( CheckDigit( strBuf, 4, 7 ) == false ) return false;
				if( strBuf.charAt( 7 ) != '-' ) return false;
				if( CheckDigit( strBuf, 8, 12 ) == false ) return false;
				
				return true;
			}
			else if( strBuf.charAt( 0 ) == '(' )
			{
				if( strBuf.charAt( 3 ) == ')' )
				{
					if( CheckDigit( strBuf, 1, 3 ) == false ) return false;
					
					if( strBuf.charAt( 7 ) == '-' )
					{
						if( CheckDigit( strBuf, 4, 7 ) == false ) return false;
						if( CheckDigit( strBuf, 8, 12 ) == false ) return false;
						
						return true;
					}
					else if( strBuf.charAt( 8 ) == '-' )
					{
						if( CheckDigit( strBuf, 4, 8 ) == false ) return false;
						if( CheckDigit( strBuf, 9, 12 ) == false ) return false;
						
						return true;
					}
				}
			}
		}
		else if( iLen == 11 )
		{
			// 02-123-4567 검사
			if( CheckDigit( strBuf, 0, 2 ) == false ) return false;
			if( strBuf.charAt( 2 ) != '-' ) return false;
			if( CheckDigit( strBuf, 3, 6 ) == false ) return false;
			if( strBuf.charAt( 6 ) != '-' ) return false;
			if( CheckDigit( strBuf, 7, 11 ) == false ) return false;
			
			return true;
		}
		
		return false;
	}
}
