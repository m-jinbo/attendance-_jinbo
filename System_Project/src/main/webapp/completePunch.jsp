<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>打刻完了</title>
<link rel="stylesheet" type="text/css" href="styles/completePunch.css">
</head>
<body>
	<div class="outer-container">
		<div class="inner-container">
			<!-- 打刻タイプを表示 -->
			<div class="corner-label">
				<%=request.getAttribute("punchType")%>
			</div>

			<!-- 登録された時刻を表示 -->
			<p class="registered-time">
				<%=request.getAttribute("registeredTime")%>
			</p>

			<!-- 赤文字で打刻完了メッセージを表示 -->
			<p class="message">
				上記時刻で<%=request.getAttribute("punchType")%>を完了しました。
			</p>

			<!-- メニューへ戻るボタン -->
			<a href="timeStamp.jsp" class="back-link">メニューへ戻る</a>
		</div>
	</div>
</body>
</html>
