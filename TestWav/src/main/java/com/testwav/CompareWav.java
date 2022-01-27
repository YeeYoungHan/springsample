package com.testwav;

import com.musicg.fingerprint.FingerprintManager;
import com.musicg.fingerprint.FingerprintSimilarity;
import com.musicg.fingerprint.FingerprintSimilarityComputer;
import com.musicg.wave.Wave;

public class CompareWav
{
	public static void main( String[] args )
	{
		if( args.length != 2 )
		{
			System.out.println( "[Usage] java -jar TestWav {wav file #1} {wav file #2}" );
			return;
		}
		
		// PCM wave 파일만 읽을 수 있는 것 같다.
		byte[] firstFingerPrint = new FingerprintManager().extractFingerprint(new Wave( args[0] ));
    byte[] secondFingerPrint = new FingerprintManager().extractFingerprint(new Wave( args[1] ));
    
    FingerprintSimilarity fingerprintSimilarity = new FingerprintSimilarityComputer(firstFingerPrint, secondFingerPrint).getFingerprintsSimilarity();
    
    // 동일한 wave 파일을 비교하면 3.0650887 이 출력되는 것 같다.
    System.out.println( "Similarity score = " + fingerprintSimilarity.getScore() );
  }
}
