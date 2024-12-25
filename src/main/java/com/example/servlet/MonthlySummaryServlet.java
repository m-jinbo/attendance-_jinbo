package com.example.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.MonthlySummaryDAO;

@WebServlet("/monthlySummary")
public class MonthlySummaryServlet extends HttpServlet {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/system_project";
	private static final String DB_USER = "m-jinbo";
	private static final String DB_PASSWORD = "m-jinbo";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Object userIdObject = session.getAttribute("userId");

		// セッションがない場合はログインページにリダイレクト
		if (userIdObject == null) {
			response.sendRedirect("top.jsp");
			return;
		}

		int userId = (int) userIdObject;

		// パラメータ取得
		String year = request.getParameter("year");
		String month = request.getParameter("month");

		// 初回表示または選択肢が未選択の場合の処理
		if ((year == null || year.isEmpty()) && (month == null || month.isEmpty())) {
			// 初回表示用のフォームデータを設定
			request.setAttribute("year", String.valueOf(java.time.Year.now().getValue())); // 現在の年
			request.setAttribute("month", String.format("%02d", java.time.LocalDate.now().getMonthValue())); // 現在の月
			request.getRequestDispatcher("monthlySummary.jsp").forward(request, response);
			return;
		}

		// 月のみが指定された場合はエラー
		if ((year == null || year.isEmpty()) && (month != null && !month.isEmpty())) {
			request.setAttribute("errorMessage", "年を選択してください。");
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}

		System.out.println("Received Year: " + year + ", Month: " + month);

		// 年月の組み立て
		String yearMonth = null;
		if (year != null && !year.isEmpty()) {
			if (month != null && !month.isEmpty()) {
				yearMonth = year + "-" + month; // 年月データ
			}
		}

		System.out.println("Calculated YearMonth: " + yearMonth);

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			MonthlySummaryDAO dao = new MonthlySummaryDAO(conn);

			Map<String, String> summary;
			if (yearMonth != null) {
				// 月次サマリーを取得
				summary = dao.getFullMonthlySummary(userId, yearMonth);
				System.out.println("Monthly Summary Data: " + summary);
			} else {
				// 年次サマリーを取得
				summary = dao.getYearlySummary(userId, year);
				System.out.println("Yearly Summary Data: " + summary);
			}

			request.setAttribute("summary", summary);
			request.setAttribute("year", year);
			request.setAttribute("month", month);

			// サマリーデータが空の場合のエラー処理
			if (summary == null || summary.isEmpty()) {
				request.setAttribute("errorMessage", "選択された期間のデータがありません。");
				request.getRequestDispatcher("error.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("monthlySummary.jsp").forward(request, response);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "データベースエラー: " + e.getMessage());
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}

}
