package com.notice.tiff;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WaveController {

	@RequestMapping(value = "play", method = RequestMethod.GET)
	public String play(Locale locale, Model model) {
		return "play";
	}
	
	@RequestMapping(value = "play2", method = RequestMethod.GET)
	public String play2(Locale locale, Model model) {
		return "play2";
	}
	
	@RequestMapping(value = "playaudio", method = RequestMethod.GET)
	public void playAudio( HttpServletResponse response, @RequestParam("second") int iSecond ) throws Exception
	{
		response.setContentType("audio/wav");
		OutputStream out = response.getOutputStream();

		String strFileName = "C:\\temp\\sipcallapi\\record.wav";
		
		File clsFile = new File( strFileName );
		InputStream in = new FileInputStream(clsFile);
		
		audioSend( in, out, iSecond );
		in.close();
	}
	
	private void audioSend( InputStream in, OutputStream out, int iSecond )
	{
		int iLoopCount = 10;	// 오디오 play 시간 (초단위)
		int iDataSize = iLoopCount * 8000; // 오디오 payload 크기
		int iTotalSize = iDataSize + 12 + 8 * 2 + 18 - 8; // wave file header 에 저장하는 body 크기
		byte[] arrBuf = new byte[8000];
		int n;
		
		try
		{
			// Chunk ID 전송
			in.read( arrBuf, 0, 4 );
			out.write( arrBuf, 0, 4 );
			
			// Chunk Data Size 전송 - 위의 iTotalSize 로 변경하여서 전송한다.
			in.read( arrBuf, 0, 4 );
			SetByte( iTotalSize, arrBuf );
			out.write( arrBuf, 0, 4 );
			
			in.read( arrBuf, 0, 34 );
			out.write( arrBuf, 0, 34 );
			
			// Chunk Size 전송 - 위의 iDataSize 로 변경하여서 전송한다.
			in.read( arrBuf, 0, 4 );
			
			int iValue = GetInt( arrBuf );
			SetByte( iDataSize, arrBuf );
			out.write( arrBuf, 0, 4 );
			
			if( iValue > ( iSecond * 80000 ) )
			{
				in.skip( 8000 * iSecond );
			}
			else
			{
				iValue -= 8000 * 10;
				if( iValue > 0 )
				{
					in.skip( iValue );
				}
			}
			
			for( int i = 0; i < iLoopCount; ++i )
			{
				n = in.read( arrBuf, 0, 8000 );
				out.write( arrBuf, 0, n );
			}

			out.flush();
		}
		catch( Exception e )
		{
			
		}
	}
	
	private static void SetByte( int iValue, byte [] arrBuf )
	{
		for( int i = 0; i < 4; i++ )
		{
			arrBuf[i] = (byte)( iValue );
			iValue >>= 8;
		}
	}
	
	private static int GetInt( byte [] arrBuf )
	{
		int iValue = 0;
		
		for( int i = 0; i < 4; i++ )
		{
			iValue += ( arrBuf[i] & 0xFF ) << ( i * 8 );
		}
		
		return iValue;
	}
}
