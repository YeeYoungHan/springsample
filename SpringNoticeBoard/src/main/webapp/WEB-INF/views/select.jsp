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
	
		<h3>게시글 상세보기</h3>
	
		<div class="form-inline form-group" >
			<label for="num" class="col-sm-2 control-label">번호:</label>
			<div class="col-sm-10">
				${row.id}
			</div>
		</div>
		<div class="form-inline form-group" >
			<label for="num" class="col-sm-2 control-label">주제:</label>
			<div class="col-sm-10">
				${row.subject}
			</div>
		</div>
		<div class="form-inline form-group" >
			<label for="num" class="col-sm-2 control-label">내용:</label>
			<div class="col-sm-10">
				${row.content}
			</div>
		</div>
		<div class="form-inline form-group" >
			<label for="num" class="col-sm-2 control-label">저장일:</label>
			<div class="col-sm-10">
				${row.insertDate}
			</div>
		</div>
	</div>

</body>
</html>
