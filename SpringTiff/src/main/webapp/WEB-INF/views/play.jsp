<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head lang="en">
  <meta charset="UTF-8">
  <title></title>
  <script src="https://code.jquery.com/jquery-3.4.0.min.js" integrity="sha256-BJeo0qm959uMBGb65z40ejJYGSgR7REI4+CW1fNKwOg=" crossorigin="anonymous"></script>
  <script>
  	var giPlaySecond = 5;
  	var giTotalSecond = 0;
  	var giStartSecond = 0;
  	
  	function GetAudioSecond()
  	{
  		$.ajax( { type : "GET", url : "getaudiosecond", dataType : "JSON", success : function(data){
				PlayAudio( data.second );
			}, error : function( xhr, status, error ){
				alert( "error - " + error );
			}
		} );
  	}
  
  	function SetAudioContent( iStartSecond, iPlaySecond )
  	{
  		$('#content').html('<iframe src="getaudiofile?start=' + iStartSecond + '&second=' + iPlaySecond + '" allow="autoplay" style="display:none" id="iframeAudio"></iframe>');
  		
  		setTimeout( GetAudioSecond, giPlaySecond * 1000 );
  	}
  	
  	function PlayAudio( iTotalSecond )
  	{
  		if( iTotalSecond == 0 )
  		{
  			alert( "audio file second is 0" );
  			return;
  		}
  		
  		if( giTotalSecond == iTotalSecond )
  		{
  			$('#msg').html('END');
  			return;
  		}
  		
  		giTotalSecond = iTotalSecond;
  		
  		if( giTotalSecond < giPlaySecond )
  		{
  			SetAudioContent( 0, iTotalSecond );
  			$('#msg').html('TotalSecond(' + iTotalSecond + ')');
  		}
  		else
  		{
  			giStartSecond = giTotalSecond - giPlaySecond;
  			SetAudioContent( giStartSecond, giPlaySecond );
  			$('#msg').html('TotalSecond(' + iTotalSecond + ') StartSecond(' + giStartSecond + ')');
  		}
  	}
  
  	$(document).ready( function(){
  		
  		GetAudioSecond();
  		
   	});
  </script>
</head>
<body>
	<div id="content">
	</div>
	<div id="msg">
	</div>
</body>
</html>