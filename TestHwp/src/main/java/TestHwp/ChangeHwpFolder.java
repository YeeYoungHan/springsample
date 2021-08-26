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
