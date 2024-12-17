package com.example.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 打刻の確認画面を表示するサーブレット
 */
@WebServlet("/PunchServlet")
public class PunchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * GETリクエストの処理
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String type = request.getParameter("type");
		String punchType;

		// リクエストのtypeが存在しない場合
		if (type == null || type.trim().isEmpty()) {
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().println("<p>タイプが指定されていません。</p>");
			return;
		}

		// 打刻タイプを判定
		switch (type) {
		case "1":
			punchType = "出勤打刻";
			break;
		case "3":
			punchType = "休憩開始打刻";
			break;
		case "4":
			punchType = "休憩終了打刻";
			break;
		case "2":
			punchType = "退勤打刻";
			break;
		default:
			punchType = "不明な打刻";
		}

		// リクエスト属性に値を設定
		request.setAttribute("type", type);
		request.setAttribute("punchType", punchType);

		// JSPファイルにフォワード
		request.getRequestDispatcher("confirmPunch.jsp").forward(request, response);
	}

	/**
	 * POSTリクエストの処理（GETに委譲）
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
