<%@ page language="java" contentType="text/html; charset=UTF-8" session="false" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>File upload</title>
	<script src="./resources/js/jquery-3.3.1.min.js"></script>
</head>
<body>
	<div id="file" style="width:300px; height:300px; border:1px solid black;">
	</div>
	
	<script>
		$("#file").on("dragenter dragover", function(event){
			event.preventDefault();
		});
		
		$("#file").on("drop", function(event){
			event.preventDefault();
			
			var arrFile = event.originalEvent.dataTransfer.files;
			
			for( var i = 0; i < arrFile.length; ++i )
			{
				var clsData = new FormData();
				clsData.append( "file", arrFile[i] );
				
				$.ajax({
					url: "file_upload",
					data: clsData,
					dataType: "text",
					processData: false,
					contentType: false,
					type: "POST"
				});
			}
		});
	</script>
</body>
</html>