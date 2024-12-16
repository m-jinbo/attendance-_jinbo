<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>打刻完了</title>
<link rel="stylesheet" type="text/css" href="styles/completePunch.css">
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
		<div class="inner-container">
			<%
			// サーブレットから渡された打刻の種類を変数に定義
			String punchType = (String) request.getAttribute("punchType");
			String registeredTime = (String) request.getAttribute("registeredTime");
			%>
			<!-- 打刻タイプを表示 -->
			<div class="corner-label">
				<%=punchType%>
			</div>

			<!-- 登録された時刻を表示 -->
			<p class="registered-time">
				<%=registeredTime%>
			</p>

			<!-- 赤文字で打刻完了メッセージを表示 -->
			<p class="message">
				上記時刻で<%=punchType%>を完了しました。
			</p>

			<!-- メニューへ戻るボタン -->
			<a href="confirmForm.jsp" class="back-link">メニューへ戻る</a>
		</div>
	</div>
</body>
</html>
