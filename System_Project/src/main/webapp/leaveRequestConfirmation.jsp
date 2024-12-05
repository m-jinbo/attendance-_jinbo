<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>休暇申請</title>
<!-- CSSファイル読み込み -->
<link rel="stylesheet" type="text/css"
	href="styles/leaveRequestConfirmation.css">
</head>
<body>
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
					<span class="year"><%=startDate.substring(0, 4)%></span> <span
						class="unit">年</span> <span class="month"><%=startDate.substring(5, 7)%></span>
					<span class="unit">月</span> <span class="day"><%=startDate.substring(8, 10)%></span>
					<span class="unit">日</span>
				</p>
			</div>

			<!-- 終了日情報 -->
			<div class="info-block end-date">
				<p class="label">休暇終了日</p>
				<p class="value">
					<span class="year"><%=endDate.substring(0, 4)%></span> <span
						class="unit">年</span> <span class="month"><%=endDate.substring(5, 7)%></span>
					<span class="unit">月</span> <span class="day"><%=endDate.substring(8, 10)%></span>
					<span class="unit">日</span>
				</p>
			</div>

			<!-- 休暇種類情報 -->
			<div class="info-block leave-type">
				<p class="label">休暇種類</p>
				<p class="value">
					<%=leaveTypeName%>
				</p>
			</div>

			<!-- 休暇理由情報 -->
			<div class="info-block leave-reason">
				<p class="label">休暇理由</p>
				<p class="value">
					<%=reason%>
				</p>
			</div>

			<!-- フォーム -->
			<form action="LeaveRequestSubmitServlet" method="post"
				class="form-container">
				<input type="hidden" name="startDate" value="<%=startDate%>">
				<input type="hidden" name="endDate" value="<%=endDate%>">
				<input type="hidden" name="leaveType"
					value="<%=request.getAttribute("leaveType")%>"> <input
					type="hidden" name="reason" value="<%=reason%>">

				<p class="info confirmation-text">上記内容の申請で間違いないでしょうか？</p>
				<div class="button-container">
					<button type="submit" class="submit-button">申請する</button>
					<a href="leave_request.jsp" class="back-button">戻る</a>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
