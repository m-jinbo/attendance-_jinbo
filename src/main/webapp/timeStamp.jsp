<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>打刻メニュー</title>
<link rel="stylesheet" type="text/css" href="styles/timeStamp.css">
<link rel="stylesheet" type="text/css" href="styles/sidebar.css">

</head>
<body>
	<!-- サイドバー -->
	<jsp:include page="sidebar.jsp" />

	<div class="outer-container">
		<!-- メインコンテンツ -->
		<div class="inner-container">
			<div class="content">
				<div class="button-container">
					<form action="PunchServlet" method="post">
						<button type="submit" name="type" value="1" class="action-button">出勤打刻</button>
						<button type="submit" name="type" value="3" class="action-button">休憩開始打刻</button>
						<button type="submit" name="type" value="4" class="action-button">休憩終了打刻</button>
						<button type="submit" name="type" value="2" class="action-button">退勤打刻</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
