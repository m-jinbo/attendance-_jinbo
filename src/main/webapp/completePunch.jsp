<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>打刻完了</title>
<link rel="stylesheet" type="text/css" href="styles/completePunch.css">
<link rel="stylesheet" type="text/css" href="styles/sidebar.css">

</head>
<body>
	<!-- サイドバー -->
	<jsp:include page="sidebar.jsp" />
	<div class="outer-container">
		<div class="inner-container">
			<%
			// サーブレットから渡された打刻の種類を変数に定義
			String punchType = (String) request.getAttribute("punchType");
			String registeredTime = (String) request.getAttribute("registeredTime");
			%>
			<!-- 打刻タイプを表示 -->
			<div class="corner-label">
				<%=punchType%>
			</div>

			<!-- 登録された時刻を表示 -->
			<p class="registered-time">
				<%=registeredTime%>
			</p>

			<!-- 赤文字で打刻完了メッセージを表示 -->
			<p class="message">
				上記時刻で<%=punchType%>を完了しました。
			</p>

			<!-- メニューへ戻るボタン -->
			<a href="confirmForm.jsp" class="back-link">メニューへ戻る</a>
		</div>
	</div>
</body>
</html>
