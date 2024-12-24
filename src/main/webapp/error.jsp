<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>エラーが発生しました</title>
</head>
<body>
	<h1>エラーが発生しました</h1>
	<p>申し訳ありませんが、リクエストの処理中にエラーが発生しました。</p>

	<!-- サーブレットからエラーメッセージを受け取る -->
	<p>
		<strong>エラーメッセージ:</strong>
	</p>
	<p style="color: red;">
		<%=request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "不明なエラー"%>
	</p>

	<!-- ナビゲーションリンク -->
	<p>
		<a href="login.jsp">ログインページに戻る</a><br> <a href="index.jsp">ホームに戻る</a>
	</p>
</body>
</html>
