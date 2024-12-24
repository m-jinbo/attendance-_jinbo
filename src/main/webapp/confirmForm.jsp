<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.entity.Employees"%>
<%@ page import="javax.servlet.http.HttpSession"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>勤怠管理一覧</title>
<link rel="stylesheet" type="text/css" href="styles/confirmForm.css">
<link rel="stylesheet" type="text/css" href="styles/sidebar.css">
</head>
<body>

	<!-- サイドバーのインクルード -->
	<jsp:include page="sidebar.jsp" />
	<div class="outer-container">
		<!-- メインコンテンツ -->
		<div class="inner-container">
			<div class="button-container">
				<!-- 勤怠情報一覧ボタン -->
				<form action="attendance" method="get">
					<button type="submit">勤怠情報一覧</button>
				</form>

				<form action="timeStamp.jsp" method="get">
					<button type="submit">打刻申請</button>
				</form>

				<form action="leave_request.jsp" method="get">
					<button type="submit">休暇申請</button>
				</form>
			</div>
		</div>
	</div>

</body>
</html>
