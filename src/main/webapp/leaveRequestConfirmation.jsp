<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>休暇申請</title>
<link rel="stylesheet" type="text/css" href="styles/leaveRequestConfirmation.css">
<link rel="stylesheet" type="text/css" href="styles/sidebar.css">

</head>
<body>
	<!-- サイドバー -->
	<jsp:include page="sidebar.jsp" />
	<div class="outer-container">
		<div class="inner-container">
			<!-- 変数に定義 -->
			<%
			String startDate = (String) request.getAttribute("startDate");
			String endDate = (String) request.getAttribute("endDate");
			String leaveTypeName = (String) request.getAttribute("leaveTypeName");
			String reason = (String) request.getAttribute("reason");
			%>

			<!-- コーナーラベル -->
			<div class="corner-label">休暇申請</div>

			<!-- 開始日情報 -->
			<div class="info-block start-date">
				<p class="label">休暇開始日</p>
				<p class="value">
					<%
					if (startDate != null) {
					%>
					<span class="year"><%=startDate.substring(0, 4)%></span> <span
						class="unit">年</span> <span class="month"><%=startDate.substring(5, 7)%></span>
					<span class="unit">月</span> <span class="day"><%=startDate.substring(8, 10)%></span>
					<span class="unit">日</span>
					<%
					} else {
					%>
					<span class="error">開始日が未指定です。</span>
					<%
					}
					%>
				</p>
			</div>

			<!-- 終了日情報 -->
			<div class="info-block end-date">
				<p class="label">休暇終了日</p>
				<p class="value">
					<%
					if (endDate != null) {
					%>
					<span class="year"><%=endDate.substring(0, 4)%></span> <span
						class="unit">年</span> <span class="month"><%=endDate.substring(5, 7)%></span>
					<span class="unit">月</span> <span class="day"><%=endDate.substring(8, 10)%></span>
					<span class="unit">日</span>
					<%
					} else {
					%>
					<span class="error">終了日が未指定です。</span>
					<%
					}
					%>
				</p>
			</div>

			<!-- 休暇種類情報 -->
			<div class="info-block leave-type">
				<p class="label">休暇種類</p>
				<p class="value">
					<%
					if (leaveTypeName != null) {
					%>
					<%=leaveTypeName%>
					<%
					} else {
					%>
					<span class="error">休暇種類が未指定です。</span>
					<%
					}
					%>
				</p>
			</div>

			<!-- 休暇理由情報 -->
			<div class="info-block leave-reason">
				<p class="label">休暇理由</p>
				<p class="value">
					<%
					if (reason != null) {
					%>
					<%=reason%>
					<%
					} else {
					%>
					<span class="error">理由が未指定です。</span>
					<%
					}
					%>
				</p>
			</div>

			<!-- フォーム -->
			<form action="LeaveRequestSubmitServlet" method="post"
				class="form-container">
				<input type="hidden" name="startDate"
					value="<%=request.getAttribute("startDate") != null ? request.getAttribute("startDate") : ""%>">
				<input type="hidden" name="endDate"
					value="<%=request.getAttribute("endDate") != null ? request.getAttribute("endDate") : ""%>">
				<input type="hidden" name="leaveType"
					value="<%=request.getAttribute("leaveType") != null ? request.getAttribute("leaveType") : ""%>">
				<input type="hidden" name="reason"
					value="<%=request.getAttribute("reason") != null ? request.getAttribute("reason") : ""%>">

				<p class="info confirmation-text">上記内容の申請で間違いないでしょうか？</p>
				<%
				// 共通変数に値を格納
				String startDateValue = request.getAttribute("startDate") != null ? request.getAttribute("startDate").toString() : "";
				String endDateValue = request.getAttribute("endDate") != null ? request.getAttribute("endDate").toString() : "";
				String leaveTypeValue = request.getAttribute("leaveType") != null ? request.getAttribute("leaveType").toString() : "";
				String reasonValue = request.getAttribute("reason") != null ? request.getAttribute("reason").toString() : "";
				%>
				<div class="button-container">
					<!-- 申請するボタン -->
					<form action="LeaveRequestSubmitServlet" method="post"
						class="form-container" style="display: inline;">
						<input type="hidden" name="startDate" value="<%=startDateValue%>">
						<input type="hidden" name="endDate" value="<%=endDateValue%>">
						<input type="hidden" name="leaveType" value="<%=leaveTypeValue%>">
						<input type="hidden" name="reason" value="<%=reasonValue%>">
						<button type="submit" class="submit-button">申請する</button>
					</form>

					<form action="LeaveRequestConfirmationServlet" method="post"
						style="display: inline;">
						<input type="hidden" name="startDate" value="<%=startDateValue%>">
						<input type="hidden" name="endDate" value="<%=endDateValue%>">
						<input type="hidden" name="leaveType" value="<%=leaveTypeValue%>">
						<input type="hidden" name="reason" value="<%=reasonValue%>">
						<input type="hidden" name="action" value="back">
						<!-- 登録画面に戻る指示 -->
						<button type="submit" class="back-button">戻る</button>
					</form>

				</div>
			</form>
		</div>
	</div>
</body>
</html>
