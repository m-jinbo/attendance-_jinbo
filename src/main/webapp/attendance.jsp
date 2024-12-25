<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="model.entity.DailyAttendance"%>
<html>
<head>
<title>勤怠情報一覧</title>
<link rel="stylesheet" type="text/css" href="styles/attendance.css">
<link rel="stylesheet" type="text/css" href="styles/sidebar-a.css">
</head>
<body>
	<!-- サイドバー -->
	<jsp:include page="sidebar-a.jsp" />

	<div class="outer-container">
		<div class="inner-container attendance">
			<div class="attendance_header">
				<div class="attendance_label">勤怠情報一覧</div>
				<form action="monthlySummary" method="get" class="attendance_form">
					<button type="submit" class="button button-summary">労働時間集計</button>
				</form>
			</div>

			<%
			String yearParam = request.getParameter("year");
			String monthParam = request.getParameter("month");
			Calendar calendar = Calendar.getInstance();
			if (yearParam != null && monthParam != null) {
				calendar.set(Calendar.YEAR, Integer.parseInt(yearParam));
				calendar.set(Calendar.MONTH, Integer.parseInt(monthParam) - 1);
			}
			int currentYear = calendar.get(Calendar.YEAR);
			int currentMonth = calendar.get(Calendar.MONTH) + 1;
			calendar.add(Calendar.MONTH, -1);
			int prevYear = calendar.get(Calendar.YEAR);
			int prevMonth = calendar.get(Calendar.MONTH) + 1;
			calendar.add(Calendar.MONTH, 2);
			int nextYear = calendar.get(Calendar.YEAR);
			int nextMonth = calendar.get(Calendar.MONTH) + 1;
			String displayLabel = currentYear + "年 " + currentMonth + "月";
			%>
			<div class="attendance_display-label"><%=displayLabel%></div>

			<div class="attendance_content">
				<table class="attendance_table">
					<thead>
						<tr>
							<th>日付</th>
							<th>出勤時間</th>
							<th>退勤時間</th>
							<th>休憩時間</th>
							<th>累計時間</th>
						</tr>
					</thead>
					<tbody>
						<%
						List<DailyAttendance> attendanceList = (List<DailyAttendance>) request.getAttribute("attendanceList");
						if (attendanceList != null) {
							for (DailyAttendance attendance : attendanceList) {
								// データのフォーマットとデフォルト値設定
								String workDateFormatted = attendance.getWorkDateFormatted() != null
								? attendance.getWorkDateFormatted()
								: ""; // デフォルト値は空文字
								String formattedStartTime = attendance.getStartTime() != null && attendance.getStartTime().length() >= 5
								? attendance.getStartTime().substring(0, 5)
								: ""; // 空文字
								String formattedEndTime = attendance.getEndTime() != null && attendance.getEndTime().length() >= 5
								? attendance.getEndTime().substring(0, 5)
								: ""; // 空文字
								String breakTime = attendance.getBreakTime() != null && !attendance.getBreakTime().isEmpty()
								? attendance.getBreakTime()
								: "00:00"; // デフォルトで "00:00"
								String workingTime = attendance.getWorkingTime() != null && !attendance.getWorkingTime().isEmpty()
								? attendance.getWorkingTime()
								: "00:00"; // デフォルトで "00:00"

								// 合計行の処理
								if ("合計時間".equals(workDateFormatted)) {
						%>
						<tr class="attendance_total_row">
							<td>合計時間</td>
							<td colspan="3"></td>
							<td><%=workingTime%></td>
						</tr>
						<%
						continue; // 合計行はスキップ
						}
						%>
						<tr>
							<td><%=workDateFormatted%></td>
							<td><%=formattedStartTime%></td>
							<td><%=formattedEndTime%></td>
							<td><%=breakTime%></td>
							<td><%=workingTime%></td>
						</tr>
						<%
						}
						} else {
						%>
						<tr>
							<td colspan="5">データがありません</td>
						</tr>
						<%
						}
						%>
					</tbody>
				</table>
			</div>

			<div class="attendance_navigation">
				<form action="attendance" method="get" class="navigation_form">
					<input type="hidden" name="year" value="<%=prevYear%>"> <input
						type="hidden" name="month"
						value="<%=String.format("%02d", prevMonth)%>">
					<button type="submit" class="button button-nav">先月</button>
				</form>
				<form action="attendance" method="get" class="navigation_form">
					<input type="hidden" name="year" value="<%=nextYear%>"> <input
						type="hidden" name="month"
						value="<%=String.format("%02d", nextMonth)%>">
					<button type="submit" class="button button-nav">翌月</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
