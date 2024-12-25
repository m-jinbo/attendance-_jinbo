<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.time.Year"%>
<html>
<head>
<title>労働時間集</title>
<link rel="stylesheet" type="text/css" href="styles/sidebar.css">
<link rel="stylesheet" type="text/css" href="styles/monthlySummary.css">
</head>
<body>
	<!-- サイドバー -->
	<jsp:include page="sidebar.jsp" />

	<div class="outer-container">
		<div class="inner-container">
			<!-- フォームコンテナ -->
			<div class="form-container">
				<!-- 一覧へ戻るボタン -->
				<form action="attendance" method="get" class="form form-back">
					<button type="submit" class="button button-back">一覧へ戻る</button>
				</form>

				<!-- フィルターフォーム -->
				<div class="filter-container">
					<form method="get" action="monthlySummary" class="form form-filter">
						<select name="year" class="select select-year">
							<option value="">年別</option>
							<%
							int currentYear = Year.now().getValue();
							for (int i = currentYear - 5; i <= currentYear + 1; i++) {
							%>
							<option value="<%=i%>"
								<%=request.getParameter("year") != null && request.getParameter("year").equals(String.valueOf(i)) ? "selected"
		: ""%>>
								<%=i%>年
							</option>
							<%
							}
							%>
						</select> <select name="month" class="select select-month">
							<option value="">月別</option>
							<%
							for (int m = 1; m <= 12; m++) {
								String monthString = String.format("%02d", m);
							%>
							<option value="<%=monthString%>"
								<%=request.getParameter("month") != null && request.getParameter("month").equals(monthString) ? "selected" : ""%>>
								<%=m%>月
							</option>
							<%
							}
							%>
						</select>
						<button type="submit" class="button button-filter">抽出</button>
					</form>
				</div>
			</div>


			<!-- 集計結果表示 -->
			<%
			String year = request.getParameter("year");
			String monthSelected = request.getParameter("month");
			String label = (year != null && monthSelected != null) ? year + "年 " + monthSelected + "月 労働時間集計" : "労働時間集計";
			%>
			<p class="summary-title"><%=label%></p>

			<%
			Map<String, String> summary = (Map<String, String>) request.getAttribute("summary");
			if (summary != null && !summary.isEmpty()) {
			%>
			<table class="table summary-table">
				<tr class="table-header">
					<td>所定日数</td>
					<td>実働日数</td>
					<td>休出日数</td>
					<td>欠勤</td>
					<td>有給</td>
				</tr>
				<tr class="table-row">
					<td><%=summary.getOrDefault("scheduled_days", "0")%>日</td>
					<td><%=summary.getOrDefault("actual_work_days", "0")%>日</td>
					<td><%=summary.getOrDefault("holiday_work_days", "0")%>日</td>
					<td><%=summary.getOrDefault("absence_days", "0")%>日</td>
					<td><%=summary.getOrDefault("paid_leave_days", "0")%>日</td>
				</tr>
				<tr class="table-header">
					<td>所定時間</td>
					<td>実働時間</td>
					<td>深夜</td>
					<td>時間外</td>
					<td>休憩時間</td>
				</tr>
				<tr class="table-row">
					<td><%=summary.getOrDefault("scheduled_work_hours", "0時間")%></td>
					<td><%=summary.getOrDefault("actual_work_hours", "0時間")%></td>
					<td><%=summary.getOrDefault("late_night_hours", "0時間")%></td>
					<td><%=summary.getOrDefault("overtime_hours", "0時間")%></td>
					<td><%=summary.getOrDefault("total_break_hours", "0時間")%></td>
				</tr>
			</table>
			<%
			} else {
			%>
			<p class="message no-data">データがありません。</p>
			<%
			}
			%>
		</div>
	</div>
</body>
</html>
