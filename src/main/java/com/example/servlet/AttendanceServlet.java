package com.example.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC Driver not found.");
			e.printStackTrace();
			request.setAttribute("errorMessage", "JDBCドライバが見つかりません: " + e.getMessage());
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}

		HttpSession session = request.getSession();
		Object userIdObject = session.getAttribute("userId");

		if (userIdObject == null) {
			response.sendRedirect("top.jsp");
			return;
		}

		String userId = userIdObject.toString();
		String yearParam = request.getParameter("year");
		String monthParam = request.getParameter("month");

		Calendar calendar = Calendar.getInstance();
		if (yearParam != null && monthParam != null) {
			try {
				calendar.set(Calendar.YEAR, Integer.parseInt(yearParam));
				calendar.set(Calendar.MONTH, Integer.parseInt(monthParam) - 1); // 0始まり
			} catch (NumberFormatException e) {
				request.setAttribute("errorMessage", "不正な年月の形式です。");
				request.getRequestDispatcher("error.jsp").forward(request, response);
				return;
			}
		}

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = dateFormat.format(calendar.getTime()) + " 00:00:00";

		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		String endDate = dateFormat.format(calendar.getTime()) + " 23:59:59";

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			DailyAttendanceDAO dao = new DailyAttendanceDAO(conn);
			List<DailyAttendance> attendanceList = dao.getAttendanceByUserId(userId, startDate, endDate);

			if (attendanceList != null) {
				SimpleDateFormat dateFormatter = new SimpleDateFormat("M月d日(E)", Locale.JAPANESE);
				String totalWorkingTime = "00:00";

				for (DailyAttendance attendance : attendanceList) {
					if (attendance.getWorkDate() != null) {
						attendance.setWorkDateFormatted(dateFormatter.format(attendance.getWorkDate()));
					}

					// 累計時間の処理
					String workingTime = attendance.getWorkingTime() != null ? attendance.getWorkingTime() : "00:00";
					totalWorkingTime = addTimes(totalWorkingTime, workingTime);
				}

				// 合計行をリストに追加
				DailyAttendance totalRow = new DailyAttendance();
				totalRow.setWorkDateFormatted("合計時間");
				totalRow.setStartTime(""); // 合計行は空の値
				totalRow.setEndTime(""); // 合計行は空の値
				totalRow.setBreakTime(""); // 合計行は空の値
				totalRow.setWorkingTime(totalWorkingTime); // 累計時間を設定
				attendanceList.add(totalRow);
			}

			request.setAttribute("attendanceList", attendanceList);
			request.setAttribute("year", calendar.get(Calendar.YEAR));
			request.setAttribute("month", String.format("%02d", calendar.get(Calendar.MONTH) + 1));
			request.getRequestDispatcher("attendance.jsp").forward(request, response);

		} catch (SQLException e) {
			request.setAttribute("errorMessage", "データベースエラー: " + e.getMessage());
			request.getRequestDispatcher("error.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("errorMessage", "予期しないエラー: " + e.getMessage());
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}

	// ヘルパーメソッド: HH:mm を足し合わせる
	private String addTimes(String time1, String time2) {
		try {
			if (time1 == null || time1.isEmpty())
				time1 = "00:00";
			if (time2 == null || time2.isEmpty())
				time2 = "00:00";

			String[] parts1 = time1.split(":");
			String[] parts2 = time2.split(":");
			int hours = Integer.parseInt(parts1[0]) + Integer.parseInt(parts2[0]);
			int minutes = Integer.parseInt(parts1[1]) + Integer.parseInt(parts2[1]);
			hours += minutes / 60;
			minutes %= 60;
			return String.format("%02d:%02d", hours, minutes);
		} catch (Exception e) {
			System.out.println("Error adding times: " + time1 + ", " + time2 + ". Error: " + e.getMessage());
		}
		return "00:00";
	}
}
