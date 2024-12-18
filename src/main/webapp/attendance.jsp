<%@ page import="java.math.BigDecimal"%>
<%@ page import="java.util.List"%>
<%@ page import="model.entity.Attendance"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>勤怠情報一覧</title>
</head>
<body>
	<table>
		<thead>
			<tr>
				<th>日付</th>
				<th>出勤時間</th>
				<th>休憩時間</th>
				<th>退勤時間</th>
				<th>累計時間</th>
			</tr>
		</thead>
		<tbody>
			<%
			List<Attendance> attendanceList = (List<Attendance>) request.getAttribute("attendanceList");
			if (attendanceList != null && !attendanceList.isEmpty()) {
				for (Attendance att : attendanceList) {
			%>
			<tr>
				<td><%=att.getDate() != null ? att.getDate() : "-"%></td>
				<td><%=att.getAttendanceTime() != null ? att.getAttendanceTime() : "-"%></td>
				<td><%=att.getBreakTime() != null ? att.getBreakTime() : "-"%></td>
				<td>
					<%
					BigDecimal totalHours = att.getTotalHours();
					if (totalHours != null && totalHours.compareTo(BigDecimal.ZERO) > 0) {
						int hours = totalHours.intValue(); // 時間部分
						int minutes = totalHours.subtract(BigDecimal.valueOf(hours))
						.multiply(BigDecimal.valueOf(60)).intValue(); // 分部分
						out.print(String.format("%02d:%02d", hours, minutes));
					} else {
						out.print("00:00");
					}
					%>
				</td>
			</tr>
			<%
			}
			} else {
			%>
			<tr>
				<td colspan="5">勤怠情報がありません。</td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>
</body>
</html>
