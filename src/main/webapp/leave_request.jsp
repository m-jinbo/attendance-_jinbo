<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>休暇申請フォーム</title>
<link rel="stylesheet" type="text/css" href="styles/leave_request.css">
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
		<div
			class="inner-container <%=(request.getAttribute("errorMessage") != null) ? "error-shown" : ""%>">
			<!-- コーナーラベル -->
			<div class="corner-label">休暇申請</div>

			<!-- エラーメッセージ -->
			<%
			String errorMessage = (String) request.getAttribute("errorMessage");
			if (errorMessage != null && !errorMessage.isEmpty()) {
			%>
			<p class="error-message"><%=errorMessage%></p>
			<%
			}
			%>

			<form action="LeaveRequestInputServlet" method="post">
				<!-- 開始日 -->
				<div class="date-container start-date">
					<label for="startYear">休暇開始日</label>
					<div class="select-group">
						<select name="startYear" id="startYear">
							<option value=""
								<%=(request.getAttribute("startYear") == null) ? "selected" : ""%>></option>
							<%
							for (int year = 2024; year <= 2030; year++) {
							%>
							<option value="<%=year%>"
								<%=String.valueOf(year).equals(request.getAttribute("startYear")) ? "selected" : ""%>>
								<%=year%>
							</option>
							<%
							}
							%>
						</select> <span>年</span> <select name="startMonth" id="startMonth">
							<option value=""
								<%=(request.getAttribute("startMonth") == null) ? "selected" : ""%>></option>
							<%
							for (int month = 1; month <= 12; month++) {
							%>
							<option value="<%=String.format("%02d", month)%>"
								<%=String.format("%02d", month).equals(request.getAttribute("startMonth")) ? "selected" : ""%>>
								<%=month%>
							</option>
							<%
							}
							%>
						</select> <span>月</span> <select name="startDay" id="startDay">
							<option value=""
								<%=(request.getAttribute("startDay") == null) ? "selected" : ""%>></option>
							<%
							for (int day = 1; day <= 31; day++) {
							%>
							<option value="<%=String.format("%02d", day)%>"
								<%=String.format("%02d", day).equals(request.getAttribute("startDay")) ? "selected" : ""%>>
								<%=day%>
							</option>
							<%
							}
							%>
						</select> <span>日</span>
					</div>
				</div>

				<!-- 終了日 -->
				<div class="date-container end-date">
					<label for="endYear">休暇終了日</label>
					<div class="select-group">
						<select name="endYear" id="endYear">
							<option value=""
								<%=(request.getAttribute("endYear") == null) ? "selected" : ""%>></option>
							<%
							for (int year = 2024; year <= 2030; year++) {
							%>
							<option value="<%=year%>"
								<%=String.valueOf(year).equals(request.getAttribute("endYear")) ? "selected" : ""%>>
								<%=year%>
							</option>
							<%
							}
							%>
						</select> <span>年</span> <select name="endMonth" id="endMonth">
							<option value=""
								<%=(request.getAttribute("endMonth") == null) ? "selected" : ""%>></option>
							<%
							for (int month = 1; month <= 12; month++) {
							%>
							<option value="<%=String.format("%02d", month)%>"
								<%=String.format("%02d", month).equals(request.getAttribute("endMonth")) ? "selected" : ""%>>
								<%=month%>
							</option>
							<%
							}
							%>
						</select> <span>月</span> <select name="endDay" id="endDay">
							<option value=""
								<%=(request.getAttribute("endDay") == null) ? "selected" : ""%>></option>
							<%
							for (int day = 1; day <= 31; day++) {
							%>
							<option value="<%=String.format("%02d", day)%>"
								<%=String.format("%02d", day).equals(request.getAttribute("endDay")) ? "selected" : ""%>>
								<%=day%>
							</option>
							<%
							}
							%>
						</select> <span>日</span>
					</div>
				</div>

				<!-- 休暇種類 -->
				<div class="leave-type-container">
					<label for="leaveType">休暇種類</label> <select name="leaveType"
						id="leaveType">
						<option value=""
							<%=(request.getAttribute("leaveType") == null) ? "selected" : ""%>></option>
						<option value="1"
							<%="1".equals(request.getAttribute("leaveType")) ? "selected" : ""%>>有給</option>
						<option value="2"
							<%="2".equals(request.getAttribute("leaveType")) ? "selected" : ""%>>欠勤</option>
						<option value="3"
							<%="3".equals(request.getAttribute("leaveType")) ? "selected" : ""%>>特別休暇</option>
					</select>
				</div>

				<!-- 休暇理由 -->
				<div class="reason-container">
					<label for="reason">休暇理由</label>
					<textarea name="reason" id="reason"><%=(request.getAttribute("reason") != null) ? request.getAttribute("reason").toString() : ""%></textarea>
				</div>

				<!-- ボタン -->
				<div class="button-container">
					<button type="submit">申請</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
