<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Map"%>
<html>
<head>
<title>月次サマリー</title>
<link rel="stylesheet" type="text/css" href="styles/sidebar.css">
</head>
<body>
	<!-- サイドバー -->
	<jsp:include page="sidebar.jsp" />

	<div class="outer-container">
		<!-- メインコンテンツ -->
		<div class="inner-container">
			<!-- 勤怠一覧に戻るボタン -->
			<form action="attendance" method="get">
				<button type="submit">一覧に戻る</button>
			</form>
			<%
			// JSPスクリプトレット内で変数を定S義
			String year = (String) request.getAttribute("year");
			String monthSelected = (String) request.getAttribute("month");
			String label;

			if (monthSelected != null && !monthSelected.isEmpty()) {
				label = year + "年度" + Integer.parseInt(monthSelected) + "月度労働時間集計";
			} else if (year != null && !year.isEmpty()) {
				label = year + "年度労働時間集計";
			} else {
				label = "労働時間集計";
			}
			%>

			<!-- 年月選択フォーム -->
			<form method="get" action="monthlySummary">
				<label for="year"></label> <select name="year" id="year">
					<option value="">年別</option>
					<%
					int currentYear = java.time.Year.now().getValue();
					for (int i = currentYear - 5; i <= currentYear + 1; i++) {
					%>
					<option value="<%=i%>"
						<%=year != null && year.equals(String.valueOf(i)) ? "selected" : ""%>><%=i%>年
					</option>
					<%
					}
					%>
				</select> <label for="month"></label> <select name="month" id="month">
					<option value="">月別</option>
					<%
					for (int m = 1; m <= 12; m++) {
					%>
					<option value="<%=String.format("%02d", m)%>"
						<%=monthSelected != null && monthSelected.equals(String.format("%02d", m)) ? "selected" : ""%>><%=m%>月
					</option>
					<%
					}
					%>
				</select>

				<button type="submit" id="submitButton">抽出</button>
			</form>

			<script>
				document.getElementById("submitButton")
						.addEventListener(
								"click",
								function(event) {
									const year = document
											.getElementById("year").value;
									const month = document
											.getElementById("month").value;
									if (!year && month) {
										event.preventDefault();
										alert("年を選択してください");
									}
								});
			</script>

			<!-- ラベル表示 -->
			<h1><%=label%></h1>
			<%
			Map<String, String> summary = (Map<String, String>) request.getAttribute("summary");
			String errorMessage = (String) request.getAttribute("errorMessage");

			if (errorMessage != null) {
			%>
			<p style="color: red;"><%=errorMessage%></p>
			<%
			} else if (summary != null && !summary.isEmpty()) {
			%>
			<table>
				<tr>
					<th>所定日数</th>
					<th>実働日数</th>
					<th>休出日数</th>
					<th>欠勤</th>
					<th>有給</th>
				</tr>
				<tr>
					<td><%=summary.getOrDefault("scheduled_days", "0")%>日</td>
					<td><%=summary.getOrDefault("actual_work_days", "0")%>日</td>
					<td><%=summary.getOrDefault("holiday_work_days", "0")%>日</td>
					<td><%=summary.getOrDefault("absence_days", "0")%>日</td>
					<td><%=summary.getOrDefault("paid_leave_days", "0")%>日</td>
				</tr>
				<tr>
					<th>所定時間</th>
					<th>実働時間</th>
					<th>深夜</th>
					<th>時間外</th>
					<th>休憩時間</th>
				</tr>
				<tr>
					<td><%=summary.getOrDefault("scheduled_work_hours", "0時間")%></td>
					<td><%=summary.getOrDefault("actual_work_hours", summary.getOrDefault("total_work_hours", "0時間"))%></td>
					<td><%=summary.getOrDefault("late_night_hours", summary.getOrDefault("total_late_night_hours", "0時間"))%></td>
					<td><%=summary.getOrDefault("overtime_hours", summary.getOrDefault("total_overtime_hours", "0時間"))%></td>
					<td><%=summary.getOrDefault("total_break_hours", "0時間")%></td>
				</tr>
			</table>
			<%
			} else {
			%>
			<p>データがありません。</p>
			<%
			}
			%>
		</div>
	</div>
</body>
</html>
