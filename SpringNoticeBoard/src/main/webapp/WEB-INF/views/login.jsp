<%@ page language="java" contentType="text/html; charset=UTF-8" session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>로그인</title>
	<meta charset="UTF-8">
	<link href="./resources/css/bootstrap.min.css" rel="stylesheet">
	<script src="./resources/js/jquery-3.3.1.min.js"></script>
</head>
<body>

	<div class="container">
	
		<div style="margin-top:20px"></div>
		
		<form action="login" method="post" class="form-horizontal">
			<div class="form-inline form-group" >
				<label for="num" class="col-sm-2 control-label">아이디:</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="id" name="id" size="20" value="admin">
				</div>
			</div>
			<div class="form-inline form-group" >
				<label for="num" class="col-sm-2 control-label">비밀번호:</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="pw" name="pw" size="20" value="spring">
				</div>
			</div>
			
			<div class="form-inline form-group" >
				<div class="col-sm-offset-3 col-sm-3">
					<button class="btn btn-default" type="submit" >
						<span class="glyphicon glyphicon-ok"></span>&nbsp;&nbsp;로그인
					</button>
				</div>
			</div>
		</form>

	</div>
	
	<script>
		$("form").submit( function() {
			var strId = $("#id").val();
			var strPw = $("#pw" ).val();
			
			if( strId.length == 0 )
			{
				alert("아이디를 입력해 주세요!!!")
				return false;
			}
			
			if( strPw.length == 0 )
			{
				alert("비밀번호를 입력해 주세요!!!")
				return false;
			}
			
			return true;
		} );
	</script>
	
</body>
</html>
