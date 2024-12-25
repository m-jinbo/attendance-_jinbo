<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>申請完了</title>
<link rel="stylesheet" type="text/css" href="styles/leaveRequestComplete.css">
<link rel="stylesheet" type="text/css" href="styles/sidebar.css">

</head>
<body>
	<!-- サイドバー -->
	<jsp:include page="sidebar.jsp" />
	<div class="outer-container">
		<div class="inner-container">
			<!-- コーナーラベル -->
			<div class="corner-label">休暇申請</div>

			<!-- インフォコンテナ -->
			<div class="info-container">
				<p>休暇申請が完了しました。</p>
			</div>

			<div class="button-container">
				<a href="confirmForm.jsp">メニューへ戻る</a>
			</div>
		</div>
	</div>
</body>
</html>
