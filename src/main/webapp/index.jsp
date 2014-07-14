<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>山东潍坊老乡会</title>
<meta name="viewport"
	content="width=device-width; initial-scale=1.0; maximum-scale=1.0" />
<link rel="stylesheet" media="all" href="css/style.css" type="text/css">
</head>
<script type="text/javascript">
	function handleFiles(files) {
		var txt = "选择文件数：" + files.length + "<br/>";
		document.getElementById("status").innerHTML = txt;

		for ( var i = 0; i < files.length; i++) {
			var reader = new FileReader();
			reader.onloadend = function(e) {
				$.ajax({
					type : 'POST',
					url : 'f/upload_html5.nut',
					data : {
						filedata : e.target.result
					},
					success : function(msg) {
						alert("Server return : " + msg);
					}
				});
			};
			// 开始访问文件内容
			reader.readAsBinaryString(files[i]);
		}
	}
</script>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript">
	$(function() {
		$('#addMoreFile').click(function() {
			$(this).before('<input name="file" type="file"></input><p/>');
		});
	});
</script>
</head>
<body>
	<div id="result"></div>
	<div>
		<p>1. 单文件表单上传</p>
		Wrong HTTP method!
		<div>
			<form action="f/upload.nut" method="post"
				enctype="multipart/form-data">
				<input name="file" type="file"></input> <input type="submit"
					value="提交" />
			</form>
		</div>
		<p>
			2.Servlet通过forward转向/WEB-INF/pages/hi.html页面 <a href="sayhi">/sayhi</a><br />
		</p>
		<p>
			3.FreeMarker加上nutz:<a href="hellonut/dohello">/dohello</a><br />
		</p>
		<form action="http://localhost:8086/sunrise22-galaxy/hellonut/dohello.nut" method="POST" enctype="multipart/form-data">
			仅仅只是为了触发POST方法：<input type="text" name="name" tabindex="30" size="15"
				maxlength="5" title="请输入姓名" /><br> <input type="submit" value="提交" name="name">
		</form>
	</div>
</body>
</html>