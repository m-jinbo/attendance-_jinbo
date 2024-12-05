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
	<div class="outer-container">
		<%
        // 変数に値を代入
        String errorMessage = (String) request.getAttribute("errorMessage");
        String startYear = (String) request.getAttribute("startYear");
        String startMonth = (String) request.getAttribute("startMonth");
        String startDay = (String) request.getAttribute("startDay");
        String endYear = (String) request.getAttribute("endYear");
        String endMonth = (String) request.getAttribute("endMonth");
        String endDay = (String) request.getAttribute("endDay");
        String leaveType = (String) request.getAttribute("leaveType");
        String reason = (String) request.getAttribute("reason");
        %>
		<div
			class="inner-container <%= (errorMessage != null) ? "error-shown" : "" %>">
			<!-- コーナーラベル -->
			<div class="corner-label">休暇申請</div>

			<!-- エラーメッセージ -->
			<% if (errorMessage != null && !errorMessage.isEmpty()) { %>
			<p class="error-message"><%= errorMessage %></p>
			<% } %>

			<form action="LeaveRequestInputServlet" method="post">
				<!-- 開始日 -->
				<div class="date-container start-date">
					<label for="startYear">休暇開始日</label>
					<div class="select-group">
						<select name="startYear" id="startYear">
							<option value="" <%= (startYear == null) ? "selected" : "" %>></option>
							<% for (int year = 2024; year <= 2030; year++) { %>
							<option value="<%= year %>"
								<%= String.valueOf(year).equals(startYear) ? "selected" : "" %>>
								<%= year %>
							</option>
							<% } %>
						</select> <span>年</span> <select name="startMonth" id="startMonth">
							<option value="" <%= (startMonth == null) ? "selected" : "" %>></option>
							<% for (int month = 1; month <= 12; month++) { %>
							<option value="<%= month %>"
								<%= String.valueOf(month).equals(startMonth) ? "selected" : "" %>>
								<%= month %>
							</option>
							<% } %>
						</select> <span>月</span> <select name="startDay" id="startDay">
							<option value="" <%= (startDay == null) ? "selected" : "" %>></option>
							<% for (int day = 1; day <= 31; day++) { %>
							<option value="<%= day %>"
								<%= String.valueOf(day).equals(startDay) ? "selected" : "" %>>
								<%= day %>
							</option>
							<% } %>
						</select> <span>日</span>
					</div>
				</div>

				<!-- 終了日 -->
				<div class="date-container end-date">
					<label for="endYear">休暇終了日</label>
					<div class="select-group">
						<select name="endYear" id="endYear">
							<option value="" <%= (endYear == null) ? "selected" : "" %>></option>
							<% for (int year = 2024; year <= 2030; year++) { %>
							<option value="<%= year %>"
								<%= String.valueOf(year).equals(endYear) ? "selected" : "" %>>
								<%= year %>
							</option>
							<% } %>
						</select> <span>年</span> <select name="endMonth" id="endMonth">
							<option value="" <%= (endMonth == null) ? "selected" : "" %>></option>
							<% for (int month = 1; month <= 12; month++) { %>
							<option value="<%= month %>"
								<%= String.valueOf(month).equals(endMonth) ? "selected" : "" %>>
								<%= month %>
							</option>
							<% } %>
						</select> <span>月</span> <select name="endDay" id="endDay">
							<option value="" <%= (endDay == null) ? "selected" : "" %>></option>
							<% for (int day = 1; day <= 31; day++) { %>
							<option value="<%= day %>"
								<%= String.valueOf(day).equals(endDay) ? "selected" : "" %>>
								<%= day %>
							</option>
							<% } %>
						</select> <span>日</span>
					</div>
				</div>

				<!-- 休暇種類 -->
				<div class="leave-type-container">
					<label for="leaveType">休暇種類</label> <select name="leaveType"
						id="leaveType">
						<option value="" <%= (leaveType == null) ? "selected" : "" %>></option>
						<option value="1" <%= "1".equals(leaveType) ? "selected" : "" %>>有給</option>
						<option value="2" <%= "2".equals(leaveType) ? "selected" : "" %>>欠勤</option>
						<option value="3" <%= "3".equals(leaveType) ? "selected" : "" %>>特別休暇</option>
					</select>
				</div>

				<!-- 休暇理由 -->
				<div class="reason-container">
					<label for="reason">休暇理由</label>
					<textarea name="reason" id="reason"><%= (reason != null) ? reason : "" %></textarea>
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
