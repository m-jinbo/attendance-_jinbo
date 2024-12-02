<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>打刻確認</title>
<!-- cssファイル読み込み -->
<link rel="stylesheet" type="text/css" href="styles/confirmPunch.css">
</head>
<body>
	<div class="outer-container">
		<div class="inner-container">
			<!-- サーブレットから渡された打刻の種類を表示 -->
			<div class="corner-label"><%=request.getAttribute("punchType")%></div>
			<%
			// 不明なタイプの場合のエラーチェック
			if ("不明な打刻".equals(request.getAttribute("punchType"))) {
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
			<a href="timeStamp.jsp" class="back-link">戻る</a>
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
