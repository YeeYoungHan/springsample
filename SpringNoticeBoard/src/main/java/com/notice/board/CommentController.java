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

package com.notice.board;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.notice.db.NoticeBoardCommentRow;

/** 댓글 관리 컨트롤러
 * @author 이영한 ( http://blog.naver.com/websearch )
 */
@RestController
public class CommentController
{
	@Inject
	SqlSession m_clsSession;
	
	/** 댓글 리스트 요청에 대한 응답을 전송한다.
	 * @param iId		게시물 아이디
	 * @param model	view 로 전달할 객체
	 * @return 댓글 리스트 및 HTTP 응답 코드를 저장한 객체
	 */
	@RequestMapping(value = "list_comment", method = RequestMethod.GET)
	public ResponseEntity<List<NoticeBoardCommentRow>> listComment( @RequestParam("id") int iId, Model model )
	{
		ResponseEntity<List<NoticeBoardCommentRow>> clsResponse = null;
		
		try
		{
			List<NoticeBoardCommentRow> clsList = m_clsSession.selectList( "SelectCommentList", iId );
			clsResponse = new ResponseEntity<>( clsList, HttpStatus.OK );
		}
		catch( Exception e )
		{
			clsResponse = new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR );
		}
		
		return clsResponse;
	}
	
	/** 댓글 저장 요청에 대한 응답을 전송한다.
	 * @param clsRow 댓글 정보 저장 객체
	 * @return 성공 여부 및 HTTP 응답 코드를 저장한 객체
	 */
	@Transactional
	@RequestMapping(value = "insert_comment", method = RequestMethod.POST)
	public ResponseEntity<String> insertAction( @RequestBody NoticeBoardCommentData clsRow )
	{
		ResponseEntity<String> clsResponse = null;
		
		try
		{
			m_clsSession.insert( "InsertComment", new NoticeBoardCommentRow( clsRow.m_iParentId, clsRow.m_strComment ) );
			m_clsSession.update( "UpdateCommentCount", clsRow.m_iParentId );
			clsResponse = new ResponseEntity<>( "SUCCESS", HttpStatus.OK );
		}
		catch( Exception e )
		{
			clsResponse = new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR );
		}
		
		return clsResponse;
	}
}
