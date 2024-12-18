package com.example.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.AttendanceDAO;
import model.entity.Attendance;

@WebServlet("/AttendanceServlet")
public class AttendanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("userid") == null) {
			response.sendRedirect("top.jsp");
			return;
		}

		int userId = (int) session.getAttribute("userid");
		int year = getParameterAsInt(request, "year", 2024);
		int month = getParameterAsInt(request, "month", 9);

		try {
			AttendanceDAO dao = new AttendanceDAO();
			List<Attendance> attendanceList = dao.getAttendanceListForMonth(userId, year, month);

			request.setAttribute("attendanceList", attendanceList);
			request.setAttribute("currentYear", year);
			request.setAttribute("currentMonth", month);

			request.getRequestDispatcher("attendance.jsp").forward(request, response);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "システムエラーが発生しました。");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}

	private int getParameterAsInt(HttpServletRequest request, String paramName, int defaultValue) {
		String value = request.getParameter(paramName);
		if (value != null && !value.isEmpty()) {
			try {
				return Integer.parseInt(value);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return defaultValue;
	}
}
