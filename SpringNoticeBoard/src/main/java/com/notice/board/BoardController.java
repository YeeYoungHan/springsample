package com.notice.board;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.notice.db.NoticeBoardRow;
import com.notice.db.NoticeBoardSql;

@Controller
public class BoardController
{
	@Inject
	SqlSession m_clsSession;
	
	@PostConstruct
	public void Init()
	{
		m_clsSession.getConfiguration( ).addMapper( NoticeBoardSql.class );
	}
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list( Model model )
	{
		List<NoticeBoardRow> clsList = m_clsSession.selectList( "SelectList" );
		
		model.addAttribute( "list", clsList );
		
		return "list";
	}
	
	@RequestMapping(value = "insert", method = RequestMethod.GET)
	public String insert( Model model )
	{
		return "insert";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String update( Model model )
	{
		return "update";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String delete( Model model )
	{
		return "delete";
	}
	
	@RequestMapping(value = "select", method = RequestMethod.GET)
	public String select( @RequestParam("id") int iId, Model model )
	{
		NoticeBoardRow clsRow = m_clsSession.selectOne( "Select", iId );
		
		model.addAttribute( "row", clsRow );
		
		return "select";
	}
}
