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

	<!-- フォームを外枠で囲む -->
	<div class="form-container">
		<!-- 社員IDまたはパスワードに誤りがあるエラーメッセージ（フォームの一番上に表示） -->
		<%
		String error = (String) request.getAttribute("error");
		if (error != null) {
		%>
		<div class="general-error"><%=error%></div>
		<%
		}
		%>

		<form action="login" method="post">
			<!-- 社員ID -->
			<div class="form-group">
				<label for="userid">社員ID</label>
				<%
				String userIdError = (String) request.getAttribute("userIdError");
				if (userIdError != null) {
				%>
				<!-- 社員IDのエラーメッセージを右横に小さく表示 -->
				<span class="error-message"><%=userIdError%></span>
				<%
				}
				%>
				<!-- ユーザーが入力した社員IDを保持 -->
				<input type="text" name="userid" id="userid"
					value="<%=request.getParameter("userid") != null ? request.getParameter("userid") : ""%>">
			</div>

			<!-- パスワード -->
			<div class="form-group">
				<label for="password">パスワード</label>
				<%
				String passwordError = (String) request.getAttribute("passwordError");
				if (passwordError != null) {
				%>
				<!-- パスワードのエラーメッセージを右横に小さく表示 -->
				<span class="error-message"><%=passwordError%></span>
				<%
				}
				%>
				<!-- ユーザーが入力したパスワードを保持 -->
				<input type="password" name="password" id="password">
			</div>

			<!-- ログインボタンの右寄せ -->
			<div class="submit-container">
				<input type="submit" value="ログイン">
			</div>

		</form>
	</div>

</body>
</html>
