<%@ page language="java" contentType="text/html; charset=UTF-8" session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>게시글 리스트</title>
	<meta charset="UTF-8">
	<link href="./resources/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<div class="container">
		<h3>게시글 리스트</h3>
		<table class="table table-striped">
			<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성일</th>
				<th>조회</th>
			</tr>
			</thead>
			<tbody>
			
			<c:forEach items="${list}" var="row">
			
				<tr>
					<td>${row.id}</td>
					<td><a href="./select?id=${row.id}">${row.subject}</a></td>
					<td>${row.insertDate}</td>
					<td>${row.readCount}</td>
				</tr>
			
			</c:forEach>
			
			</tbody>
		</table>
	</div>
</body>
</html>
