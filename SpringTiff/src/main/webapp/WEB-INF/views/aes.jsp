<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>aes</title>
</head>
<body>
	<script src="./resources/js/aes.js"></script>
	<script>
		var CryptoJSAesJson = {
		    stringify: function (cipherParams) {
		        var j = {ct: cipherParams.ciphertext.toString(CryptoJS.enc.Base64)};
		        if (cipherParams.iv) j.iv = cipherParams.iv.toString();
		        if (cipherParams.salt) j.s = cipherParams.salt.toString();
		        return JSON.stringify(j);
		    }
		}
	
		var strOutput = CryptoJS.AES.encrypt( "안녕하세요1234abcdefghijklmn", "password", {format: CryptoJSAesJson}).toString();
		
		document.write( strOutput );
	</script>
</body>
</html>