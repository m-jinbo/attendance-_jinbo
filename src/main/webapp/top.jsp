<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>勤怠管理</title>
<link rel="stylesheet" type="text/css" href="styles/top.css">
</head>
<body>
	<div class="outer-container">
		<!-- ロゴコンテナ -->
		<div class="logo-container">
			<img src="images/seasissst.png" alt="SE Assist Logo" class="logo">
		</div>

		<!-- 内側コンテナ -->
		<div
			class="inner-container <%=request.getAttribute("errorMessage") != null ? "error-shown" : ""%>">

			<!-- エラーメッセージコンテナ -->
			<div class="error-container">
				<%
				String error = (String) request.getAttribute("error");
				if (error != null) {
				%>
				<div class="general-error"><%=error%></div>
				<%
				}
				%>
			</div>

			<!-- ログインフォーム -->
			<form action="login" method="post" class="form-container">

				<!-- 社員IDコンテナ -->
				<div class="userid-container">
					<label for="userid">社員ID</label>
					<%
					String userIdError = (String) request.getAttribute("userIdError");
					if (userIdError != null) {
					%>
					<span class="error-message"><%=userIdError%></span>
					<%
					}
					%>
					<input type="text" name="userid" id="userid"
						value="<%=request.getParameter("userid") != null ? request.getParameter("userid") : ""%>">
				</div>

				<!-- パスワードコンテナ -->
				<div class="password-container">
					<label for="password">パスワード</label>
					<%
					String passwordError = (String) request.getAttribute("passwordError");
					if (passwordError != null) {
					%>
					<span class="error-message"><%=passwordError%></span>
					<%
					}
					%>
					<input type="password" name="password" id="password">
				</div>

				<!-- ボタンコンテナ -->
				<div class="submit-container">
					<input type="submit" value="ログイン">
				</div>
			</form>
		</div>
	</div>
</body>
</html>
