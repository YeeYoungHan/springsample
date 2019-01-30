<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
	<title>Tiff Modify</title>
	<style>
    	#main canvas { border: 1px solid red; witdh:800px; height:1072px; display:block; }
    	#list canvas { border: 1px solid red; witdh:100px; height:134px; display:block; cursor:pointer; }
    	div { float:left }
    </style>
</head>
<body>
  	<div id='main'>
  	</div>
  	<div id='list'>
  	</div>
  	<div>
  		<button type="button" onclick="Modify()">modify</button>
  	</div>
  
    <script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
    <script src="js/tiff.min.js"></script>
    <script type="text/javascript">
			$(function () {
			  Tiff.initialize({TOTAL_MEMORY: 16777216 * 10});
			  var xhr = new XMLHttpRequest();
			  var tiff;
			  xhr.open('GET', './resources/img/5.tiff');
			  xhr.responseType = 'arraybuffer';
			  xhr.onload = function (e) {
			    var buffer = xhr.response;
			    tiff = new Tiff({buffer: buffer});
			    for (var i = 0, len = tiff.countDirectory(); i < len; ++i) 
			    {
			      tiff.setDirectory(i);
			      var canvas = tiff.toCanvas();
			      canvas.id = i;
			      canvas.style.width = "100px";
			      canvas.style.height = "137px";
			      
			      canvas.addEventListener('click', function(e){
			      	var id = e.target.id;
			      	tiff.setDirectory(id);
					var dest = tiff.toCanvas();
					dest.id = "main_canvas";
					dest.style.width = "800px";
			      	dest.style.height = "1072px";
					$('#main').empty();
			      	$('#main').append(dest);
			      	SetCanvasEvent();
			      }, false );
			      
			      $('#list').append(canvas);
			      
			      if( i == 0 )
			      {
			      	var canvas = tiff.toCanvas();
			      	canvas.id = "main_canvas";
			      	canvas.style.width = "800px";
			      	canvas.style.height = "1072px";
			      	$('#main').empty();
			      	$('#main').append(canvas);
			      	SetCanvasEvent();
			      }
			    }
			  };
			  xhr.send();
			});

			function SetCanvasEvent()
			{
				var iX, iY;
				var bDraw = false;
				
				var canvas = document.getElementById("main_canvas");
				var ctx = canvas.getContext("2d");
				ctx.strokeStyle = "#000000";
				ctx.lineJoin = "round";
				ctx.lineWidth = 2;
			
				$('#main_canvas').mousedown(function(e){
					var bb = canvas.getBoundingClientRect();
					iX = ( e.clientX - bb.left ) * canvas.width / 800;
					iY = ( e.clientY - bb.top ) * canvas.height / 1072;
					bDraw = true;
				});
				
				$('#main_canvas').mousemove(function(e){
					if( bDraw )
					{
						var bb = canvas.getBoundingClientRect();
						var iNewX = ( e.clientX - bb.left ) * canvas.width / 800;
						var iNewY = ( e.clientY - bb.top ) * canvas.height / 1072;
					
						ctx.beginPath();
						ctx.moveTo( iX, iY );
						ctx.lineTo( iNewX, iNewY );
						ctx.closePath();
						ctx.stroke();
					
						iX = iNewX;
						iY = iNewY;
					}
				});
				
				$('#main_canvas').mouseup(function(e){
					bDraw = false;
				});			
			}
			
			function Modify()
			{
				
			}
			
    </script>
  </body>	
</body>
</html>
