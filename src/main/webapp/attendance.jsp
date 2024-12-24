<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="model.entity.DailyAttendance"%>
<html>
<head>
<title>勤怠情報一覧</title>
<link rel="stylesheet" type="text/css" href="styles/sidebar.css">

</head>
<body>
	<!-- サイドバー -->
	<jsp:include page="sidebar.jsp" />

	<div class="outer-container">
		<!-- メインコンテンツ -->
		<div class="inner-container">
			<div class="corner-label">勤怠情報一覧</div>

			<!-- 労働時間集計ボタン -->
			<form action="monthlySummary" method="get">
				<button type="submit">労働時間集計</button>
			</form>
			<%
			// 現在表示している年と月（URLパラメータから取得）
			String yearParam = request.getParameter("year");
			String monthParam = request.getParameter("month");

			// 現在の年月を計算（デフォルトは現在の年月）
			Calendar calendar = Calendar.getInstance();
			if (yearParam != null && monthParam != null) {
				calendar.set(Calendar.YEAR, Integer.parseInt(yearParam));
				calendar.set(Calendar.MONTH, Integer.parseInt(monthParam) - 1);
			}

			// 表示用年月
			int currentYear = calendar.get(Calendar.YEAR);
			int currentMonth = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTHは0始まり

			// 先月と来月を計算
			calendar.add(Calendar.MONTH, -1);
			int prevYear = calendar.get(Calendar.YEAR);
			int prevMonth = calendar.get(Calendar.MONTH) + 1;

			calendar.add(Calendar.MONTH, 2); // 来月に移動（現在月 + 1）
			int nextYear = calendar.get(Calendar.YEAR);
			int nextMonth = calendar.get(Calendar.MONTH) + 1;

			// ラベルを表示
			String displayLabel = currentYear + "年 " + currentMonth + "月";
			%>
			<div class="displaylabel"><%=displayLabel%></div>

			<!-- 勤怠一覧テーブル -->
			<table border="1">
				<tr>
					<th>日付</th>
					<th>出勤時間</th>
					<th>退勤時間</th>
					<th>休憩時間</th>
					<th>実働時間</th>
				</tr>
				<%
				List<DailyAttendance> attendanceList = (List<DailyAttendance>) request.getAttribute("attendanceList");
				if (attendanceList != null) {
					for (DailyAttendance attendance : attendanceList) {
				%>
				<tr>
					<td><%=attendance.getWorkDate()%></td>
					<td><%=attendance.getStartTime()%></td>
					<td><%=attendance.getEndTime()%></td>
					<td><%=attendance.getBreakTime()%></td>
					<td><%=attendance.getWorkingTime()%></td>
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
			</table>
			<!-- 先月・来月ボタン -->
			<form action="attendance" method="get" style="display: inline;">
				<input type="hidden" name="year" value="<%=prevYear%>"> <input
					type="hidden" name="month"
					value="<%=String.format("%02d", prevMonth)%>">
				<button type="submit">先月</button>
			</form>
			<form action="attendance" method="get" style="display: inline;">
				<input type="hidden" name="year" value="<%=nextYear%>"> <input
					type="hidden" name="month"
					value="<%=String.format("%02d", nextMonth)%>">
				<button type="submit">翌月</button>
			</form>
		</div>
	</div>
</body>
</html>
