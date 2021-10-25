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

package TestWord.TestWord;

public interface DownloadDoc
{
	public void Open( String strFileName ) throws Exception;
	public void AddString( String strText, boolean bNewLine ) throws Exception;
	public void AddDotString( String strText, boolean bNewLine ) throws Exception;
	public void AddNewLine( ) throws Exception;
	public void CreateTable( int iColCount );
	public void AddCol( String strText );
	public void AddCol( String strText, int iColSpan );
	public void AddCol( int iInt );
	public void AddTable( ) throws Exception;
	public void Close() throws Exception;
}
