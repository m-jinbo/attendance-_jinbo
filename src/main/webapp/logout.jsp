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
	<div class="message-container">
		<!-- ログアウトしたユーザー名を表示する部分 -->
		<h1><%=session.getAttribute("name")%>さんログアウトしました
		</h1>
		<a href="top.jsp">ログイン画面へ</a>
	</div>
</body>
</html>