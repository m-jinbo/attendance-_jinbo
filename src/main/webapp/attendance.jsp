<%@ page import="java.util.List"%>
<%@ page import="bean.AttendanceBean"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>勤怠情報</title>
<link rel="stylesheet" type="text/css" href="styles/attendance.css">
</head>
<body>
	<!-- サイドバー -->
	<div class="sidebar">
		<jsp:include page="sidebar.jsp" />
	</div>

	<!-- メインコンテンツ -->
	<div class="main-content">
		<!-- 月別表示 -->
		<div class="month-display">
			<h2>勤怠情報一覧</h2>
			<!-- 勤怠情報一覧のタイトル -->
			<h3>
				<%=request.getAttribute("currentYear")%>年
				<%=request.getAttribute("currentMonth")%>月
			</h3>
			<div class="time-summary-button">
				<button class="summary-button"
					onclick="location.href='timeSummary.jsp'">労働時間集</button>
			</div>
		</div>

		<!-- 勤怠情報のテーブル -->
		<table>
			<thead>
				<tr>
					<th>日付</th>
					<th>出勤時間</th>
					<th>休憩時間</th>
					<th>退勤時間</th>
					<th>累計時間</th>
					<th>ステータス</th>
				</tr>
			</thead>
			<tbody>
				<%
				List<AttendanceBean> attendanceList = (List<AttendanceBean>) request.getAttribute("attendanceList");
				if (attendanceList != null && !attendanceList.isEmpty()) {
					for (AttendanceBean attendance : attendanceList) {
				%>
				<tr>
					<td><%=attendance.getFormattedDate()%></td>
					<td><%=attendance.getFormattedStartTime()%></td>
					<td><%=attendance.getFormattedBreakTime()%></td>
					<td><%=attendance.getFormattedEndTime()%></td>
					<%
					// ステータスが「出勤」の場合に累計時間を表示
					if ("出勤".equals(attendance.getStatusString())) {
					%>
					<td><%=String.format("%.2f", attendance.getWorkHours())%></td>
					<%
					} else {
					%>
					<td></td>
					<%
					}
					%>
					<td><%=attendance.getStatusString()%></td>
				</tr>
				<%
				}
				} else {
				%>
				<tr>
					<td colspan="6">勤怠情報がありません。</td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>

		<!-- 合計時間表示 -->
		<div>
			<strong>合計時間:</strong>
			<%
			// totalWorkHoursをrequestから取得
			Double totalWorkHours = (Double) request.getAttribute("totalWorkHours");
			if (totalWorkHours != null) {
				int totalWholeHours = totalWorkHours.intValue(); // 変換
				int totalMinutes = (int) ((totalWorkHours - totalWholeHours) * 60);
				// 時間と分を「時間:分」の形式で表示
				String formattedTime = String.format("%d:%02d", totalWholeHours, totalMinutes);
				out.print(formattedTime); // フォーマットされた時間を表示
			} else {
				out.print("時間の計算に失敗しました。");
			}
			%>
		</div>

		<!-- 翌月・先月ボタン -->
		<%
		Integer currentYear = (Integer) request.getAttribute("currentYear");
		Integer currentMonth = (Integer) request.getAttribute("currentMonth");

		if (currentYear == null) {
			currentYear = 2024; // デフォルトの年を設定
		}
		if (currentMonth == null) {
			currentMonth = 9; // デフォルトの月を設定
		}

		int previousYear = (currentMonth == 1) ? currentYear - 1 : currentYear;
		int previousMonth = (currentMonth == 1) ? 12 : currentMonth - 1;
		int nextYear = (currentMonth == 12) ? currentYear + 1 : currentYear;
		int nextMonth = (currentMonth == 12) ? 1 : currentMonth + 1;
		%>


		<div class="button-container">
			<!-- 先月・翌月ボタンのリンク -->
			<button class="month-button"
				onclick="location.href='AttendanceServlet?year=<%=previousYear%>&month=<%=previousMonth%>'">先月</button>
			<button class="month-button"
				onclick="location.href='AttendanceServlet?year=<%=nextYear%>&month=<%=nextMonth%>'">翌月</button>
		</div>
	</div>
</body>
</html>
