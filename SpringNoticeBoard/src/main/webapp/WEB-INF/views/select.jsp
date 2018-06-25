<%@ page language="java" contentType="text/html; charset=UTF-8" session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>게시글 상세보기</title>
	<meta charset="UTF-8">
	<link href="./resources/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

	<div class="container">
	
		<div style="margin-top:20px"></div>
		
		<div style="border-bottom: 2px solid black; padding-bottom:10px;">
			${row.subject}
			
			<span style="float:right; font-size:8px;">${row.insertDate}</span>
		</div>
		
		<div style="padding-top:10px; padding-bottom:10px;">
			${row.content}
		</div>
				
		<div class="row" >
    		<div class="col-sm-2"><button class="btn btn-block" onclick="window.location='update?id=${row.id}'">수정</button></div>
			<div class="col-sm-2"><button class="btn btn-block" onclick="window.location='delete?id=${row.id}'">삭제</button></div>
			<div class="col-sm-2"><button class="btn btn-block" onclick="window.location='list'">리스트</button></div>
		</div>
	</div>

</body>
</html>
