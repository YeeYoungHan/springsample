<%@ page language="java" contentType="text/html; charset=UTF-8" session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>게시글 상세보기</title>
	<meta charset="UTF-8">
	<link href="./resources/css/bootstrap.min.css" rel="stylesheet">
	<style>
		td:first-child { width:80%; }
	</style>
	<script src="./resources/js/jquery-3.3.1.min.js"></script>
</head>
<body>

	<div class="container">
	
		<div style="margin-top:20px"></div>
		
		<div style="border-bottom: 2px solid black; padding-bottom:10px;">
			${row.subject}
			
			<span style="float:right; font-size:8px;">${row.insertDate}</span>
		</div>
		
		<div style="border-bottom: 2px solid black; padding-top:10px; padding-bottom:30px;">
			${row.content}
		</div>
		
		<table class="table table-striped">
			<tbody id="commentList">
			</tbody>
		</table>		
		
		<div class="row" style="padding-top:10px; padding-bottom:20px;">
			<div class="col-sm-10"><textarea class="form-control" id="comment" name="comment" rows="2" cols="62"></textarea></div>
			<div class="col-sm-2"><button class="btn btn-block" onclick="sendComment()">댓글 등록</button></div>
		</div>
				
		<div class="row" >
    		<div class="col-sm-2"><button class="btn btn-block" onclick="window.location='update?id=${row.id}'">수정</button></div>
			<div class="col-sm-2"><button class="btn btn-block" onclick="window.location='delete?id=${row.id}'">삭제</button></div>
			<div class="col-sm-2"><button class="btn btn-block" onclick="window.location='list'">리스트</button></div>
		</div>
	</div>

	<script>
		function sendComment()
		{
			var strComment = $("#comment").val();
			
			if( strComment.length == 0 )
			{
				alert("댓글을 입력해 주세요!!!")
				return false;
			}
			
			$.ajax( { 
				type:'post', 
				url:'insert_comment', 
				headers: { "Content-Type" : "application/json", "X-HTTP-Method-Override" : "POST" },
				dataType: 'text',
				data : JSON.stringify( { parentId:${row.id}, comment:strComment } ),
				success : function(result){
						if( result == "SUCCESS" ){
							$("#comment").val('');
							getComment();
						}
				},
				error: function(){
					alert("댓글 저장에 실패하였습니다.");

				}
			});
		}
		
		function getComment()
		{
			$.getJSON( "list_comment?id=" + ${row.id}, function(arrComment){
				var strHtml = "";
				
				$(arrComment).each( function(){
					strHtml += "<tr><td>" + this.m_strComment + "</td><td>" + this.m_strInsertDate + "</td></tr>";
				});
				
				$("#commentList").html(strHtml);
			} );
		}
		
		$(document).ready( function(){
			getComment();
		});
	</script>
</body>
</html>
