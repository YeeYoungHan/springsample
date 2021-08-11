<%@ page language="java" contentType="text/html; charset=UTF-8" session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>게시글 수정 테스트</title>
	<meta charset="UTF-8">
	<link href="./resources/css/bootstrap.min.css" rel="stylesheet">
	<script src="./resources/js/jquery-3.3.1.min.js"></script>
</head>
<body>

	<div class="container">
	
		<div style="margin-top:20px"></div>
	
		<% 
			com.notice.db.NoticeBoardRow clsRow = (com.notice.db.NoticeBoardRow)request.getAttribute("row");
		%>
	
		<form action="update_test2" method="post" class="form-horizontal">
			<input type="hidden" id="id" name="id" value="<%=clsRow.m_iId %>">
		
			<div class="form-inline form-group" >
				<label for="num" class="col-sm-2 control-label">제목:</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="subject" name="subject" size="60" value="<%=clsRow.m_strSubject %>" >
				</div>
			</div>
			<div class="form-inline form-group" >
				<label for="num" class="col-sm-2 control-label">내용:</label>
				<div class="col-sm-10">
					<textarea class="form-control" id="content" name="content" rows="10" cols="62"><%=clsRow.m_strSubject %></textarea>
				</div>
			</div>
			
			<div class="form-inline form-group" >
				<div class="col-sm-offset-3 col-sm-3">
					<button class="btn btn-default" type="submit" >
						<span class="glyphicon glyphicon-ok"></span>&nbsp;&nbsp;저장
					</button>
					<button class="btn btn-default" type="button" onclick="window.location='list'">
						<span class="glyphicon glyphicon-remove"></span>&nbsp;&nbsp;취소
					</button>
				</div>
			</div>
		</form>

	</div>
	
	<script>
		$("form").submit( function() {
			var strSubject = $("#subject").val();
			var strContent = $("#content" ).val();
			
			if( strSubject.length == 0 )
			{
				alert("제목을 입력해 주세요!!!")
				return false;
			}
			
			if( strContent.length == 0 )
			{
				alert("내용을 입력해 주세요!!!")
				return false;
			}
			
			return true;
		} );
	</script>
	
</body>
</html>
