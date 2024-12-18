<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログアウト</title>
<link rel="stylesheet" type="text/css" href="styles/logout.css">
</head>
<body>
	<div class="outer-container">
		<!-- ロゴ -->
		<div class="logo-container">
			<img src="images/seasissst.png" alt="SE Assist Logo" class="logo">
		</div>
		<div class="inner-container">
			<div class="message-container">
				<!-- ログアウトしたユーザー名を表示する部分 -->
				<h1><%=session.getAttribute("name")%>さん、ログアウトしました。
				</h1>
			</div>
			<!-- ログイン画面へのリンクコンテナ -->
			<div class="login-container">
				<a href="top.jsp" class="login-button">ログイン画面へ</a>
			</div>
		</div>
	</div>
</body>
</html>
