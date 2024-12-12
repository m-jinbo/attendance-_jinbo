<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ユーザー登録</title>
<link rel="stylesheet" type="text/css" href="styles/user_registration.css">
</head>
<body>
	<div class="outer-container">
		<!-- ロゴ -->
		<div class="logo-container">
			<img src="images/seasissst.png" alt="SE Assist Logo" class="logo">
		</div>

		<div class="inner-container <%=request.getAttribute("errorMessage") != null ? "error-shown" : ""%>">
			<!-- ユーザー登録タイトル -->
			<p class="title">ユーザー登録</p>

			<!-- 全体エラーメッセージ -->
			<%
			if (request.getAttribute("errorMessage") != null) {
			%>
			<div class="error-message">
				<%=request.getAttribute("errorMessage")%>
			</div>
			<%
			}
			%>

			<!-- 登録フォーム -->
			<form action="UserRegistrationServlet" method="post" class="registration-form">
				<div class="form-group name-container">
					<label for="name">名前</label>
					<input type="text" id="name" name="name" value="<%=request.getParameter("name") != null ? new String(request.getParameter("name").getBytes("ISO-8859-1"),"utf-8") : ""%>">
				</div>

				<div class="form-group location-container">
					<label for="locationId">拠点</label>
					<select id="locationId" name="locationId">
						<option value="" disabled selected></option>
						<option value="1" <%="1".equals(request.getParameter("locationId")) ? "selected" : ""%>>札幌</option>
						<option value="2" <%="2".equals(request.getParameter("locationId")) ? "selected" : ""%>>仙台</option>
						<option value="3" <%="3".equals(request.getParameter("locationId")) ? "selected" : ""%>>名古屋</option>
						<option value="4" <%="4".equals(request.getParameter("locationId")) ? "selected" : ""%>>大阪</option>
						<option value="5" <%="5".equals(request.getParameter("locationId")) ? "selected" : ""%>>東京</option>
						<option value="6" <%="6".equals(request.getParameter("locationId")) ? "selected" : ""%>>福岡</option>
					</select>
				</div>

				<div class="form-group username-container">
					<label for="username">社員ID</label>
					<input type="text" id="username" name="username" value="<%=request.getParameter("username") != null ? request.getParameter("username") : ""%>">
				</div>

				<div class="form-group password-container">
					<label for="password">パスワード</label>
					<input type="password" id="password" name="password" value="<%=request.getParameter("password") != null ? request.getParameter("password") : ""%>">
				</div>

				<div class="form-group button-container">
					<button type="submit" class="submit-button">登録</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
