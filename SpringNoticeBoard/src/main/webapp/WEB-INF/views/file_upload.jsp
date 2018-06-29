<%@ page language="java" contentType="text/html; charset=UTF-8" session="false" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>File upload</title>
</head>
<body>
	<form action="file_upload" method="post" enctype="multipart/form-data">
		<input type="file" name="file">
		<input type="submit">
	</form>
</body>
</html>