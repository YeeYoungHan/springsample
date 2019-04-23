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
		
	@RequestMapping(value = "getaudiofile", method = RequestMethod.GET)
	public void GetAudioFile( HttpServletResponse response, @RequestParam("start") int iStartSecond, @RequestParam("second") int iPlaySecond ) throws Exception
	{
		response.setContentType("audio/wav");
		OutputStream out = response.getOutputStream();

		File clsFile = new File( GetAudioFileName() );
		InputStream in = new FileInputStream(clsFile);
		
		audioSend( in, out, iStartSecond, iPlaySecond );
		in.close();
	}
	
	@RequestMapping(value = "getaudiosecond", method = RequestMethod.GET)
	public void GetAudioSecond( HttpServletResponse response ) throws Exception
	{
		response.setContentType("application/json");
		OutputStream out = response.getOutputStream();
		
		byte[] arrBuf = new byte[50];
		int iDataSize, iDataSizePos = 42, iPlaySecond;
		
		File clsFile = new File( GetAudioFileName() );
		InputStream in = new FileInputStream(clsFile);
		
		in.read( arrBuf, 0, 46 );
		
		int iCodec = GetShort( arrBuf, 20 );
		if( iCodec == 49 )
		{
			// GSM 코덱인 경우 2byte 를 더 읽는다.
			in.read( arrBuf, 46, 2 );
			iDataSizePos += 2;
		}
		
		in.close();
		
		iDataSize = GetInt( arrBuf, iDataSizePos );
		iPlaySecond = GetPlaySecond( iCodec, iDataSize );
		
		String strBuf = "{ \"second\" : " + iPlaySecond + " }";
		
		out.write( strBuf.getBytes() );
	}
	
	private String GetAudioFileName()
	{
		if( IsWindow() )
		{
			return "C:\\temp\\sipcallapi\\record.wav";
		}
		
		return "/tmp/record.wav";
	}
	
	private void audioSend( InputStream in, OutputStream out, int iStartSecond, int iPlaySecond )
	{
		int iTotalSize, iDataSize, n, iDataSizePos = 42, iWantDataSize, iSkipSize, iReadSize;
		byte[] arrBuf = new byte[16000];
		
		try
		{
			in.read( arrBuf, 0, 46 );
			
			int iCodec = GetShort( arrBuf, 20 );
			
			if( iCodec == 49 )
			{
				// GSM 코덱인 경우 2byte 를 더 읽는다.
				in.read( arrBuf, 46, 2 );
				iDataSizePos += 2;
			}
			
			iDataSize = GetInt( arrBuf, iDataSizePos );
			
			iWantDataSize = GetDataSize( iCodec, iStartSecond + iPlaySecond );
						
			if( iWantDataSize > iDataSize )
			{
				iPlaySecond = 0;
			}
			
			iDataSize = GetDataSize( iCodec, iPlaySecond );
			iTotalSize = iDataSizePos + 4 + iDataSize;
						
			SetInt( iTotalSize, arrBuf, 4 );
			SetInt( iDataSize, arrBuf, iDataSizePos );
			
			out.write( arrBuf, 0, iDataSizePos + 4 );
			
			if( iPlaySecond == 0 ) return;
			
			iSkipSize = GetDataSize( iCodec, iStartSecond );
			in.skip( iSkipSize );
			
			iReadSize = GetDataSize( iCodec, 1 );
						
			for( int i = 0; i < iPlaySecond; ++i )
			{
				n = in.read( arrBuf, 0, iReadSize );
				out.write( arrBuf, 0, n );
			}

			out.flush();
		}
		catch( Exception e )
		{
			
		}
	}
	
	private static void SetInt( int iValue, byte [] arrBuf, int iStart )
	{
		for( int i = 0; i < 4; i++ )
		{
			arrBuf[iStart+i] = (byte)( iValue );
			iValue >>= 8;
		}
	}
	
	private static int GetInt( byte [] arrBuf, int iStart )
	{
		int iValue = 0;
		
		for( int i = 0; i < 4; i++ )
		{
			iValue += ( arrBuf[iStart+i] & 0xFF ) << ( i * 8 );
		}
		
		return iValue;
	}
	
	private static int GetShort( byte [] arrBuf, int iStart )
	{
		int iValue = 0;
		
		for( int i = 0; i < 2; i++ )
		{
			iValue += ( arrBuf[iStart+i] & 0xFF ) << ( i * 8 );
		}
		
		return iValue;
	}
	
	private static int GetDataSize( int iCodec, int iSecond )
	{
		if( iCodec == 0 )
		{
			// PCM
			return 16000 * iSecond;
		}
		else if( iCodec == 7 || iCodec == 6 )
		{
			// PCMU/PCMUA
			return 8000 * iSecond;
		}
		else if( iCodec == 49 )
		{
			// GSM
			return 1625 * iSecond;
		}

		return 0;
	}
	
	private static int GetPlaySecond( int iCodec, int iDataSize )
	{
		if( iCodec == 0 )
		{
			// PCM
			return iDataSize / 16000;
		}
		else if( iCodec == 7 || iCodec == 6 )
		{
			// PCMU/PCMUA
			return iDataSize / 8000;
		}
		else if( iCodec == 49 )
		{
			// GSM
			return iDataSize / 1625;
		}

		return 0;
	}
	
	public boolean IsWindow()
	{
		String strOS = System.getProperty("os.name");
		if( strOS.startsWith("Windows") )
		{
			return true;
		}
		
		return false;
	}
}
