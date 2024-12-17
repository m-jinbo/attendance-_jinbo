<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>労働時間集</title>
<link rel="stylesheet" type="text/css" href="styles/timeSummary.css">
</head>
<body>

	<!-- サイドバー -->
	<div class="sidebar">
		<jsp:include page="sidebar.jsp" />
	</div>

	<div class="main-content">
		<!-- 一覧へ戻るボタン -->
		<a href="index.jsp" class="back-button">一覧へ戻る</a>

		<!-- 年別・月別ボタン -->
		<div class="filter-buttons">
			<button class="filter-button" onclick="toggleYearFilter()">年別</button>
			<div id="yearFilterList" class="filter-list" style="display: none;">
				<ul>
					<li><a href="attendance.jsp?year=2024">2024年</a></li>
					<li><a href="attendance.jsp?year=2023">2023年</a></li>
					<li><a href="attendance.jsp?year=2022">2022年</a></li>
				</ul>
			</div>

			<button class="filter-button" onclick="toggleMonthFilter()">月別</button>
			<div id="monthFilterList" class="filter-list" style="display: none;">
				<ul>
					<li><a href="attendance.jsp?year=2024&month=1">2024年1月</a></li>
					<li><a href="attendance.jsp?year=2024&month=2">2024年2月</a></li>
					<li><a href="attendance.jsp?year=2024&month=3">2024年3月</a></li>
					<li><a href="attendance.jsp?year=2024&month=4">2024年4月</a></li>
					<li><a href="attendance.jsp?year=2024&month=5">2024年5月</a></li>
					<li><a href="attendance.jsp?year=2024&month=6">2024年6月</a></li>
					<li><a href="attendance.jsp?year=2024&month=7">2024年7月</a></li>
					<li><a href="attendance.jsp?year=2024&month=8">2024年8月</a></li>
					<li><a href="attendance.jsp?year=2024&month=9">2024年9月</a></li>
					<li><a href="attendance.jsp?year=2024&month=10">2024年10月</a></li>
					<li><a href="attendance.jsp?year=2024&month=11">2024年11月</a></li>
					<li><a href="attendance.jsp?year=2024&month=12">2024年12月</a></li>
				</ul>
			</div>

			<!-- 「抽出」ボタンの追加 -->
			<button class="extract-button" onclick="extractData()">抽</button>
		</div>

		<!-- 労働時間集計情報を表示するための表 -->
		<h2>2024年度 労働時間集</h2>
		<table id="summaryTable">
			<tbody>
				<tr>
					<td><strong>所定日数</strong></td>
					<td>
						<div class="vertical-text">118日</div>
					</td>
					<td><strong>実働日数</strong></td>
					<td>
						<div class="vertical-text">124日</div>
					</td>
					<td><strong>休出日数</strong></td>
					<td>
						<div class="vertical-text">6日</div>
					</td>
				</tr>
				<tr>
					<td><strong>欠勤</strong></td>
					<td>
						<div class="vertical-text">1日</div>
					</td>
					<td><strong>有給休暇</strong></td>
					<td>
						<div class="vertical-text">1日</div>
					</td>
					<td><strong>所定時間</strong></td>
					<td>
						<div class="vertical-text">944時間</div>
					</td>
				</tr>
				<tr>
					<td><strong>実働時間</strong></td>
					<td>
						<div class="vertical-text">992時間</div>
					</td>
					<td><strong>深夜</strong></td>
					<td>
						<div class="vertical-text">0時間</div>
					</td>
					<td><strong>時間外</strong></td>
					<td>
						<div class="vertical-text">0時間</div>
					</td>
				</tr>
				<tr>
					<td><strong>休出時間</strong></td>
					<td>
						<div class="vertical-text">48時間</div>
					</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</tbody>
		</table>

	</div>

	<script>
		// 年別リストの表示/非表示を切り替える
		function toggleYearFilter() {
			var yearFilterList = document.getElementById("yearFilterList");
			var monthFilterList = document.getElementById("monthFilterList");
			if (yearFilterList.style.display === "none"
					|| yearFilterList.style.display === "") {
				yearFilterList.style.display = "block";
				monthFilterList.style.display = "none"; // 月別リストは隠す
			} else {
				yearFilterList.style.display = "none";
			}
		}

		// 月別リストの表示/非表示を切り替える 
		function toggleMonthFilter() {
			var monthFilterList = document.getElementById("monthFilterList");
			var yearFilterList = document.getElementById("yearFilterList");
			if (monthFilterList.style.display === "none"
					|| monthFilterList.style.display === "") {
				monthFilterList.style.display = "block";
				yearFilterList.style.display = "none"; // 年別リストは隠す
			} else {
				monthFilterList.style.display = "none";
			}
		}

		// 抽出ボタンの処理
		function extractData() {
			var selectedMonth = document.querySelector('#monthFilterList a').textContent;
			var year = selectedMonth.split("年")[0]; // 年を抽出
			var month = selectedMonth.split("年")[1].split("月")[0]; // 月を抽出

			// 新しいURLでリクエストを送信
			window.location.href = "attendance.jsp?year=" + year + "&month="
					+ month;
		}
	</script>

</body>
</html>
