package com.example.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.TimeRecordDAO;

@WebServlet("/CompletePunchServlet")
public class CompletePunchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		// セッションから user_id を取得
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			response.sendRedirect("top.jsp");
			return;
		}
		int userId = (Integer) session.getAttribute("userId"); // ログインユーザーのID

		// リクエストから打刻タイプを取得
		String type = request.getParameter("type");

		try {
			if (type == null || type.trim().isEmpty()) {
				throw new IllegalArgumentException("打刻タイプが指定されていません。");
			}

			int typeInt;
			try {
				typeInt = Integer.parseInt(type.trim());
				if (typeInt < 1 || typeInt > 4) {
					throw new IllegalArgumentException("無効な打刻タイプ: " + typeInt);
				}
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("打刻タイプは数値である必要があります: " + type, e);
			}

			// 登録処理
			TimeRecordDAO dao = new TimeRecordDAO();
			boolean success = dao.insertTimeRecord(userId, typeInt); // セッションから取得した userId を使用
			if (!success) {
				throw new SQLException("打刻の登録に失敗しました。");
			}

			// 最新の登録時間を取得
			String registeredTime = dao.getLastRecordTime(userId);

			// 登録時間を時刻形式（時:分）のみでフォーマット
			String formattedTime = null;
			if (registeredTime != null) {
				try {
					Date recordTime = java.sql.Timestamp.valueOf(registeredTime); // String を Timestamp に変換
					SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
					formattedTime = timeFormat.format(recordTime); // フォーマット
				} catch (IllegalArgumentException e) {
					formattedTime = "時刻フォーマットエラー"; // エラー時のフォールバック
				}
			}

			// JSPに渡すデータを設定
			request.setAttribute("registeredTime", formattedTime);
			request.setAttribute("punchType", convertPunchType(typeInt));
			request.getRequestDispatcher("completePunch.jsp").forward(request, response);

		} catch (IllegalArgumentException e) {
			handleError(request, response, "入力エラー: " + e.getMessage());
		} catch (SQLException e) {
			handleError(request, response, "データベースエラー: " + e.getMessage());
		} catch (Exception e) {
			handleError(request, response, "システムエラー: " + e.getMessage());
		}
	}

	/**
	 * 打刻タイプを文字列に変換するメソッド
	 */
	private String convertPunchType(int type) {
		switch (type) {
		case 1:
			return "出勤打刻";
		case 3:
			return "休憩開始打刻";
		case 4:
			return "休憩終了打刻";
		case 2:
			return "退勤打刻";
		default:
			return "不明な打刻";
		}
	}

	/**
	 * エラーメッセージを処理して表示する
	 */
	private void handleError(HttpServletRequest request, HttpServletResponse response, String errorMessage)
			throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().println("<h3>エラーが発生しました</h3>");
		response.getWriter().println("<p>" + errorMessage + "</p>");
		response.getWriter().println("<a href=\"timeStamp.jsp\">戻る</a>");
	}
}
