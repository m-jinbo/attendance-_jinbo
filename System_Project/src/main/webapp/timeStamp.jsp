<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>打刻メニュー</title>
<!-- cssファイル読み込み -->
<link rel="stylesheet" type="text/css" href="styles/timeStamp.css">
</head>
<body>
	<div class="outer-container">
		<div class="inner-container">
			<div class="button-container">
				<form action="PunchServlet" method="post">
					<button type="submit" name="type" value="1">出勤打刻</button>
					<button type="submit" name="type" value="2">休憩開始打刻</button>
					<button type="submit" name="type" value="3">休憩終了打刻</button>
					<button type="submit" name="type" value="4">退勤打刻</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
