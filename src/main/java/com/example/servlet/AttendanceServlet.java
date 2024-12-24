package com.example.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.DailyAttendanceDAO;
import model.entity.DailyAttendance;

@WebServlet("/attendance")
public class AttendanceServlet extends HttpServlet {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/system_project";
	private static final String DB_USER = "m-jinbo"; // 必要に応じて変更
	private static final String DB_PASSWORD = "m-jinbo"; // 必要に応じて変更

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// JDBCドライバのロード
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC Driver not found.");
			e.printStackTrace();
			request.setAttribute("errorMessage", "JDBCドライバが見つかりません: " + e.getMessage());
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}

		// ユーザーセッションを取得
		HttpSession session = request.getSession();
		Object userIdObject = session.getAttribute("userId");

		// デバッグ: セッションの userId 確認
		System.out.println("Session userId: " + userIdObject);

		if (userIdObject == null) {
			System.out.println("User is not logged in. Redirecting to login page.");
			response.sendRedirect("login.jsp");
			return;
		}

		String userId = userIdObject.toString();
		System.out.println("Validated userId: " + userId);

		// リクエストパラメータから年月を取得
		String yearParam = request.getParameter("year");
		String monthParam = request.getParameter("month");

		// 年月を設定（デフォルトは当月）
		Calendar calendar = Calendar.getInstance();
		if (yearParam != null && monthParam != null) {
			try {
				calendar.set(Calendar.YEAR, Integer.parseInt(yearParam));
				calendar.set(Calendar.MONTH, Integer.parseInt(monthParam) - 1); // 0始まり
			} catch (NumberFormatException e) {
				System.out.println("Invalid year or month format: " + yearParam + ", " + monthParam);
				request.setAttribute("errorMessage", "不正な年月の形式です。");
				request.getRequestDispatcher("error.jsp").forward(request, response);
				return;
			}
		}

		// 表示する月の開始日と終了日を計算
		calendar.set(Calendar.DAY_OF_MONTH, 1); // 月初
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = dateFormat.format(calendar.getTime()) + " 00:00:00";

		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 0); // 月末
		String endDate = dateFormat.format(calendar.getTime()) + " 23:59:59";

		System.out.println("StartDate: " + startDate);
		System.out.println("EndDate: " + endDate);

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			System.out.println("Database connection established.");

			// DAO を使用して勤怠データを取得
			DailyAttendanceDAO dao = new DailyAttendanceDAO(conn);
			List<DailyAttendance> attendanceList = dao.getAttendanceByUserId(userId, startDate, endDate);

			// デバッグ: リスト内容確認
			System.out.println("Attendance List Size: " + (attendanceList != null ? attendanceList.size() : "null"));
			if (attendanceList != null) {
				for (DailyAttendance attendance : attendanceList) {
					System.out.println("Work Date: " + attendance.getWorkDate());
					System.out.println("Start Time: " + attendance.getStartTime());
					System.out.println("End Time: " + attendance.getEndTime());
					System.out.println("Break Time: " + attendance.getBreakTime());
					System.out.println("Working Time: " + attendance.getWorkingTime());
				}
			}

			// JSP にデータを渡す
			request.setAttribute("attendanceList", attendanceList);
			request.setAttribute("year", calendar.get(Calendar.YEAR));
			request.setAttribute("month", String.format("%02d", calendar.get(Calendar.MONTH) + 1)); // 月を設定
			request.getRequestDispatcher("attendance.jsp").forward(request, response);

		} catch (SQLException e) {
			System.out.println("Database error occurred.");
			e.printStackTrace();
			request.setAttribute("errorMessage", "データベースエラー: " + e.getMessage());
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}
}
