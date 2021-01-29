package TestHwp;

import java.io.File;

public class ChangeHwpFolder
{
	static boolean IsHwp( File clsFile )
	{
		String strFileName = clsFile.getName( );
		
		int iIndex = strFileName.lastIndexOf( '.' );
		if( iIndex > 0 )
		{
			String strExt = strFileName.substring( iIndex + 1 );
			if( strExt.equals( "hwp" ) )
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static void main( String[] args ) throws Exception
	{
		if( args.length != 2 )
		{
			System.out.println( "[Usage] {input folder} {output folder}" );
			return;
		}
		
		ChangeHwp.m_bDebug = true;
		
		String strInputFolder = args[0];
		String strOutputFolder = args[1];
		
		File clsInputFolder = new File( strInputFolder );
		File clsOutputFolder = new File( strOutputFolder );
		
		clsOutputFolder.mkdir( );
		
		File files[] = clsInputFolder.listFiles();

		for( int i = 0; i < files.length; ++i )
		{
			if( IsHwp( files[i] ) )
			{
				System.out.println( "file[" + files[i].getAbsolutePath( ) + "]" );
				
				String strOutputFile = strOutputFolder + "/" + files[i].getName( );
				
				try
				{
					ChangeHwp.Change( files[i].getAbsolutePath( ), strOutputFile );
				}
				catch( Exception e )
				{
					System.out.println( e );
					e.printStackTrace( );
				}
			}
		}
	}
}
