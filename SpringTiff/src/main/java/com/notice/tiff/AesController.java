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

import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AesController
{
	@RequestMapping(value = "/aes", method = RequestMethod.GET)
	public String home()
	{
		return "aes";
	}

	public static byte[] GetKey( int iKeyLen, byte[] arrSalt, byte[] arrPassWord ) throws Exception
	{
		MessageDigest clsMd5 = MessageDigest.getInstance( "MD5" );

		int iDigestLen = clsMd5.getDigestLength( );
		int iDataSize = (iKeyLen + iDigestLen - 1) / iDigestLen * iDigestLen;
		byte[] arrData = new byte[iDataSize];
		int iDataLen = 0;

		clsMd5.reset( );

		while( iDataLen < iKeyLen )
		{
			if( iDataLen > 0 )
			{
				clsMd5.update( arrData, iDataLen - iDigestLen, iDigestLen );
			}
			
			clsMd5.update( arrPassWord );
			
			if( arrSalt != null )
			{
				clsMd5.update( arrSalt, 0, 8 );
			}
			
			clsMd5.digest( arrData, iDataLen, iDigestLen );

			iDataLen += iDigestLen;
		}

		return Arrays.copyOfRange( arrData, 0, iKeyLen );
	}

	// CryptoJS.AES.encrypt 로 암호화한 문자열을 복호화한다.
	public static void main( String[] args ) throws Exception
	{
		String strPassWord = "password";
		String strJson = "{\"ct\":\"Amb9v0hbQFMOW6jaGWFEy7YWbtCTCu8UHTVtLKSrI6mvwDajN2kGBZGaHzsdqm47\",\"iv\":\"e6b5ca11067fb52402a67df300e7b372\",\"s\":\"29407409b982d701\"}";

		JSONParser clsParser = new JSONParser( );
		JSONObject clsJson = (JSONObject)clsParser.parse( strJson );

		String strSalt = (String)clsJson.get( "s" );
		String strIv = (String)clsJson.get( "iv" );
		String strCt = (String)clsJson.get( "ct" );

		byte[] arrSalt = DatatypeConverter.parseHexBinary( strSalt );
		byte[] arrIv = DatatypeConverter.parseHexBinary( strIv );
		byte[] arrCt = Base64.decodeBase64( strCt.getBytes( "UTF-8" ) );

		final byte[] arrKey = GetKey( 32, arrSalt, strPassWord.getBytes( "UTF-8" ) );
		SecretKeySpec key = new SecretKeySpec( arrKey, "AES" );
		IvParameterSpec iv = new IvParameterSpec( arrIv );

		Cipher aesCBC = Cipher.getInstance( "AES/CBC/PKCS5Padding" );
		aesCBC.init( Cipher.DECRYPT_MODE, key, iv );
		byte[] arrOutput = aesCBC.doFinal( arrCt );

		System.out.println( new String( arrOutput, "UTF-8" ) );
	}
}
