<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>打刻確認</title>
<link rel="stylesheet" type="text/css" href="styles/confirmPunch.css">
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
			%>
			<!-- サーブレットから渡された打刻の種類を表示 -->
			<div class="corner-label"><%=punchType%></div>
			<%
			// 不明なタイプの場合のエラーチェック
			if ("不明な打刻".equals(punchType)) {
			%>
			<p class="info">エラー: 無効な打刻タイプが選択されました。</p>
			<a href="timeStamp.jsp" class="back-link">戻る</a>
			<%
			} else {
			%>
			<p id="currentTime"></p>
			<form action="CompletePunchServlet" method="post">
				<input type="hidden" name="type"
					value="<%=request.getAttribute("type")%>">
				<button type="submit" class="button">打刻する</button>
			</form>
			<p class="info">上記時刻で打刻を行いますか？</p>
			<%
			}
			%>
		</div>
	</div>
	<script>
        setInterval(() => {
            const now = new Date();
            document.getElementById("currentTime").textContent = now.toLocaleTimeString('ja-JP', {
                hour: '2-digit',
                minute: '2-digit'
            });
        }, 1000);
    </script>
</body>
</html>
