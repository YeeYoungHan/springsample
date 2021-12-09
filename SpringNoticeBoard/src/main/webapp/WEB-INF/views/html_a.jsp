<%@ page language="java" contentType="text/html; charset=UTF-8" session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>HTML A</title>
	<meta charset="UTF-8">
	<link href="./resources/css/bootstrap.min.css" rel="stylesheet">
	<script src="./resources/js/jquery-3.3.1.min.js"></script>
</head>
<body>

	<div class="container">
	
		<div style="margin-top:20px"></div>
		
		<div>HTML A</div>
		
		<div id="msg"></div>
		
		<input type="button" value="reset session time" onclick="ResetSessionTime()">

	</div>
	
	<script>
		var iLoginTime = 0;
		var iSessionTimeout = 10;
		
		function GetSecond()
		{
			var clsDate = new Date();
		    return clsDate.getTime() / 1000;
		}
		
		function ShowRemainSessionTime() {
		    var iTime = GetSecond();
		    var iRemain = parseInt( ( iLoginTime + iSessionTimeout ) - iTime );
		    
		    if( iRemain >= 0 )
		    {
		    	$('#msg').html( iRemain );
		    }
		    else
		    {
		    	location.reload();
		    }
		}
		
		function ResetSessionTime() {
			$.ajax( "html_a" );
			iLoginTime = GetSecond();
		}
	
		$( document ).ready(function() {
		    iLoginTime = GetSecond();
		    
		    ShowRemainSessionTime();
		    setInterval( ShowRemainSessionTime, 1000 );
		});
	</script>
	
</body>
</html>
