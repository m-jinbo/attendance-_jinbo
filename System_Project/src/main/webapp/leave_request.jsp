<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>休暇申請フォーム</title>
<!-- CSSファイル読み込み -->
<link rel="stylesheet" type="text/css" href="styles/leave_request.css">
</head>
<body>
	<div class="outer-container">
		<div class="inner-container">
			<!-- コーナーラベル -->
			<div class="corner-label">休暇申請</div>

			<form action="LeaveRequestInputServlet" method="post">
				<!-- 開始日 -->
				<div class="date-container start-date">
					<label for="startYear">休暇開始日</label>
					<div class="select-group">
						<select name="startYear" id="startYear">
							<option value="" selected></option>
							<%
							for (int year = 2024; year <= 2030; year++) {
							%>
							<option value="<%=year%>"><%=year%></option>
							<%
							}
							%>
						</select><span>年</span> <select name="startMonth" id="startMonth">
							<option value="" selected></option>
							<%
							for (int month = 1; month <= 12; month++) {
							%>
							<option value="<%=month%>"><%=month%></option>
							<%
							}
							%>
						</select><span>月</span> <select name="startDay" id="startDay">
							<option value="" selected></option>
							<%
							for (int day = 1; day <= 31; day++) {
							%>
							<option value="<%=day%>"><%=day%></option>
							<%
							}
							%>
						</select><span>日</span>
					</div>
				</div>

				<!-- 終了日 -->
				<div class="date-container end-date">
					<label for="endYear">休暇終了日</label>
					<div class="select-group">
						<select name="endYear" id="endYear">
							<option value="" selected></option>
							<%
							for (int year = 2024; year <= 2030; year++) {
							%>
							<option value="<%=year%>"><%=year%></option>
							<%
							}
							%>
						</select><span>年</span> <select name="endMonth" id="endMonth">
							<option value="" selected></option>
							<%
							for (int month = 1; month <= 12; month++) {
							%>
							<option value="<%=month%>"><%=month%></option>
							<%
							}
							%>
						</select><span>月</span> <select name="endDay" id="endDay">
							<option value="" selected></option>
							<%
							for (int day = 1; day <= 31; day++) {
							%>
							<option value="<%=day%>"><%=day%></option>
							<%
							}
							%>
						</select><span>日</span>
					</div>
				</div>

				<!-- 休暇種類 -->
				<div class="leave-type-container">
					<label for="leaveType">休暇種類</label> <select name="leaveType"
						id="leaveType">
						<option value="" selected></option>
						<option value="1">有給</option>
						<option value="2">欠勤</option>
						<option value="3">特別休暇</option>
					</select>
				</div>

				<!-- 休暇理由 -->
				<div class="reason-container">
					<label for="reason">休暇理由</label>
					<textarea name="reason" id="reason" required></textarea>
				</div>

				<!-- 申請ボタン -->
				<div class="button-container">
					<button type="submit">申請</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
