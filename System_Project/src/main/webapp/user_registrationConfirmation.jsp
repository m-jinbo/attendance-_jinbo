<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ユーザー登録確認</title>
<link rel="stylesheet" type="text/css"
	href="styles/user_registrationConfirmation.css">
</head>
<body>
	<div class="outer-container">
		<div class="inner-container">
			<!-- ロゴ -->
			<div class="logo-container">
				<img src="images/seasissst.png" alt="SE Assist Logo" class="logo">
			</div>
			<!-- ユーザー登録タイトル -->
			<p class="title">ユーザー登録</p>


			<!-- 確認メッセージ -->
			<p class="info confirmation-message">こちらの内容で登録しますか？</p>

			<!-- 確認フォーム -->
			<form action="CompleteRegistrationServlet" method="post"
				class="registration-form">
				<%
				String name = (String) request.getAttribute("name");
				String locationName = (String) request.getAttribute("locationName");
				String employeeId = (String) request.getAttribute("employeeId");
				String password = (String) request.getAttribute("password");
				%>

				<!-- 名前 -->
				<div class="form-group info">
					<label for="name">名前</label>
					<div>
						<input type="text" id="name" name="name"
							value="<%=name != null ? name : ""%>" readonly>
					</div>
				</div>

				<!-- 拠点 -->
				<div class="form-group info">
					<label for="location">拠点</label>
					<div>
						<input type="text" id="location" name="location"
							value="<%=locationName != null ? locationName : ""%>" readonly>
					</div>
				</div>

				<!-- 社員ID -->
				<div class="form-group info">
					<label for="employeeId">社員ID</label>
					<div>
						<input type="text" id="employeeId" name="employeeId"
							value="<%=employeeId != null ? employeeId : ""%>" readonly>
					</div>
				</div>

				<!-- パスワード -->
				<div class="form-group info">
					<label for="password">パスワード</label>
					<div>
						<input type="password" id="password" name="password"
							value="<%=password != null ? password : ""%>" readonly>
					</div>
				</div>

				<!-- ボタン -->
				<div class="form-group button-container">
					<button type="submit" class="submit-button">登録</button>
					<a href="user_registration.jsp" class="back-button">戻る</a>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
