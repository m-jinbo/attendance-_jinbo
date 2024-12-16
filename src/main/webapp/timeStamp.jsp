<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>打刻メニュー</title>
<link rel="stylesheet" type="text/css" href="styles/timeStamp.css">
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
			String name = (String) session.getAttribute("name");
			if (name != null) {
			%>
			<p class="name-display"><%=name%></p>
			<%
			} else {
			%>
			<p class="name-display">ゲスト</p>
			<%
			}
			%>
			<a href="logout.jsp" class="logout">ログアウト</a>
		</div>
	</div>
	<div class="outer-container">
		<!-- メインコンテンツ -->
		<div class="inner-container">
			<div class="content">
				<div class="button-container">
					<form action="PunchServlet" method="post">
						<button type="submit" name="type" value="1" class="action-button">出勤打刻</button>
						<button type="submit" name="type" value="2" class="action-button">休憩開始打刻</button>
						<button type="submit" name="type" value="3" class="action-button">休憩終了打刻</button>
						<button type="submit" name="type" value="4" class="action-button">退勤打刻</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
