<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title><%=request.getAttribute("pageTitle") != null ? request.getAttribute("pageTitle") : "ユーザー登録確認"%></title>
<link rel="stylesheet" type="text/css"
	href="styles/<%=request.getAttribute("stylesheetName") != null ? request.getAttribute("stylesheetName")
		: "user_registrationConfirmation.css"%>">
</head>
<body>
	<div class="outer-container">
		<!-- ロゴ -->
		<div class="logo-container">
			<img
				src="<%=request.getAttribute("logoPath") != null ? request.getAttribute("logoPath") : "images/seasissst.png"%>"
				alt="<%=request.getAttribute("logoAlt") != null ? request.getAttribute("logoAlt") : "SE Assist Logo"%>"
				class="logo">
		</div>

		<div class="inner-container">
			<!-- タイトル -->
			<p class="title"><%=request.getAttribute("pageTitle")%></p>

			<!-- 確認メッセージ -->
			<p class="info confirmation-message"><%=request.getAttribute("confirmationMessage")%></p>

			<!-- 確認フォーム -->
			<form action="<%=request.getAttribute("formAction")%>" method="post"
				class="confirmation-form">
				<div class="form-group info">
					<label>名前</label>
					<p><%=request.getAttribute("name")%></p>
					<input type="hidden" name="name"
						value="<%=request.getAttribute("name")%>">
				</div>

				<div class="form-group info">
					<label>拠点</label>
					<p><%=request.getAttribute("location")%></p>
					<input type="hidden" name="locationId"
						value="<%=request.getAttribute("locationId")%>">
				</div>

				<div class="form-group info">
					<label>社員ID</label>
					<p><%=request.getAttribute("username")%></p>
					<input type="hidden" name="username"
						value="<%=request.getAttribute("username")%>">
				</div>

				<div class="form-group info">
					<label>パスワード</label>
					<p>******</p>
					<input type="hidden" name="password"
						value="<%=request.getAttribute("password")%>">
				</div>

				<!-- ボタン -->
				<div class="form-group button-container">
					<button type="submit" class="submit-button"><%=request.getAttribute("submitButtonText")%></button>
					<a href="<%=request.getAttribute("backButtonLink")%>"
						class="back-button"> <%=request.getAttribute("backButtonText")%>
					</a>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
