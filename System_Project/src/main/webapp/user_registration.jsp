<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ユーザー登録</title>
<link rel="stylesheet" type="text/css"
	href="styles/user_registration.css">
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

			<!-- 登録フォーム -->
			<form action="UserRegistrationServlet" method="post"
				class="registration-form">
				<!-- 名前 -->
				<div class="form-group">
					<label for="name">名前</label>
					<div>
						<input type="text" id="name" name="name" required>
					</div>
				</div>

				<!-- 拠点 -->
				<div class="form-group">
					<label for="location">拠点</label>
					<div>
						<select id="location" name="location" required
							class="custom-select">
							<option value="" disabled selected></option>
							<option value="1">札幌</option>
							<option value="2">仙台</option>
							<option value="3">名古屋</option>
							<option value="4">大阪</option>
							<option value="5">東京</option>
							<option value="6">福岡</option>
						</select>
					</div>
				</div>

				<!-- 社員ID -->
				<div class="form-group">
					<label for="employeeId">社員ID</label>
					<div>
						<input type="text" id="employeeId" name="employeeId" required>
					</div>
				</div>

				<!-- パスワード -->
				<div class="form-group">
					<label for="password">パスワード</label>
					<div>
						<input type="password" id="password" name="password" required>
					</div>
				</div>

				<!-- 登録ボタン -->
				<div class="form-group">
					<button type="submit" class="submit-button">登録</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
