<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head lang="en">
  <meta charset="UTF-8">
  <title></title>
  <script src="https://code.jquery.com/jquery-3.4.0.min.js" integrity="sha256-BJeo0qm959uMBGb65z40ejJYGSgR7REI4+CW1fNKwOg=" crossorigin="anonymous"></script>
  <script>
  	$(document).ready( function(){
  		var iSecond = 10;
  		
  		setInterval( function(){
  			$('#content').html('<audio controls autoplay><source src="playaudio?second=' + iSecond + '" type="audio/wav"></source></audio>');
  			iSecond += 10;
  		}, 10000 );
  	});
  </script>
</head>
<body id="content">
  <audio controls autoplay>
    <source src="playaudio?second=0" type="audio/wav"></source>
  </audio>
</body>
</html>