<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>申請完了</title>
<!-- CSSファイル読み込み -->
<link rel="stylesheet" type="text/css"
	href="styles/leaveRequestComplete.css">
</head>
<body>
	<!-- サイドバー -->
	<div class="sidebar">
		<div class="logo-container">
			<img src="images/sidebar_logo.png" alt="SE Assist Logo" class="logo">
		</div>
		<nav class="menu">
			<ul>
				<li><a href="attendance.jsp"
					class="menu-item <%=(request.getRequestURI().contains("attendance.jsp") ? "active" : "")%>">勤怠情報一覧</a></li>
				<li><a href="timeStamp.jsp"
					class="menu-item <%=(request.getRequestURI().contains("timeStamp.jsp")
		|| request.getRequestURI().contains("confirmPunch.jsp")
		|| request.getRequestURI().contains("completePunch.jsp") ? "active" : "")%>">打刻申請</a></li>
				<li><a href="leave_request.jsp"
					class="menu-item <%=(request.getRequestURI().contains("leave_request.jsp")
		|| request.getRequestURI().contains("leaveRequestConfirmation.jsp")
		|| request.getRequestURI().contains("leaveRequestComplete.jsp") ? "active" : "")%>">休暇申請</a></li>
			</ul>
		</nav>
		<div class="user-info">
			<!-- ログインユーザー名を表示 -->
			<%
			String username = (String) session.getAttribute("username");
			if (username != null) {
			%>
			<p class="username-display"><%=username%></p>
			<%
			} else {
			%>
			<p class="username-display">ゲスト</p>
			<%
			}
			%>
			<a href="logout.jsp" class="logout">ログアウト</a>
		</div>
	</div>
	<div class="outer-container">
		<div class="inner-container">
			<!-- コーナーラベル -->
			<div class="corner-label">休暇申請</div>

			<!-- インフォコンテナ -->
			<div class="info-container">
				<p>休暇申請が完了しました。</p>
			</div>

			<div class="button-container">
				<a href="leave_request.jsp">メニューへ戻る</a>
			</div>
		</div>
	</div>
</body>
</html>
