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
				<div class="form-group name-container">
					<label>名前</label>
					<div class="result-box"><%=request.getAttribute("name")%></div>
					<input type="hidden" name="name"
						value="<%=request.getAttribute("name")%>">
				</div>

				<div class="form-group location-container">
					<label>拠点</label>
					<div class="result-box"><%=request.getAttribute("location")%></div>
					<input type="hidden" name="locationId"
						value="<%=request.getAttribute("locationId")%>">
				</div>

				<div class="form-group user-id-container">
					<label>社員ID</label>
					<div class="result-box"><%=request.getAttribute("user_id")%></div>
					<input type="hidden" name="user_id"
						value="<%=request.getAttribute("user_id")%>">
				</div>

				<div class="form-group password-container">
					<label>パスワード</label>
					<div class="result-box">******</div>
					<input type="hidden" name="password"
						value="<%=request.getAttribute("password")%>">
				</div>

				<div class="form-group button-container">
					<!-- 登録ボタン -->
					<form action="<%=request.getAttribute("formAction")%>"
						method="post" style="display: inline;">
						<button type="submit" class="submit-button"><%=request.getAttribute("submitButtonText")%></button>
					</form>

					<!-- 戻るボタン -->
					<form action="UserRegistrationServlet" method="post"
						style="display: inline;">
						<input type="hidden" name="name"
							value="<%=request.getAttribute("name")%>">
						<input type="hidden" name="locationId"
							value="<%=request.getAttribute("locationId")%>">
						<input type="hidden" name="user_id"
							value="<%=request.getAttribute("user_id")%>">
						<input type="hidden" name="password"
							value="<%=request.getAttribute("password")%>">
						<input type="hidden" name="action" value="戻る">
						<button type="submit" class="back-button"><%=request.getAttribute("backButtonText")%></button>
					</form>
				</div>
		</div>
</body>
</html>
