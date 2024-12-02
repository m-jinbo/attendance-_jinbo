package com.example.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.ConnectionManager;

@WebServlet("/LeaveRequestConfirmationServlet")
public class LeaveRequestConfirmationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 // リクエストの文字エンコーディングをUTF-8に設定
        request.setCharacterEncoding("UTF-8");
		
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String leaveTypeParam = request.getParameter("leaveType");
		String reason = request.getParameter("reason");

		// デバッグ用ログ
		System.out.println("startDate: " + startDate);
		System.out.println("endDate: " + endDate);
		System.out.println("leaveTypeParam: " + leaveTypeParam);
		System.out.println("reason: " + reason);

		if (leaveTypeParam == null || leaveTypeParam.trim().isEmpty()) {
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().println("<p>エラー: 休暇種類が選択されていません。</p>");
			return;
		}

		int leaveType;
		try {
			leaveType = Integer.parseInt(leaveTypeParam);
		} catch (NumberFormatException e) {
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().println("<p>エラー: 休暇種類が無効です。</p>");
			return;
		}

		String leaveTypeName = getLeaveTypeName(leaveType);

		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		request.setAttribute("leaveType", leaveType);
		request.setAttribute("leaveTypeName", leaveTypeName);
		request.setAttribute("reason", reason);

		request.getRequestDispatcher("leaveRequestConfirmation.jsp").forward(request, response);
	}

	private String getLeaveTypeName(int leaveType) {
		String leaveTypeName = "不明な休暇種類";
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("SELECT type_name FROM leave_types WHERE type_id = ?")) {
			pstmt.setInt(1, leaveType);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					leaveTypeName = rs.getString("type_name");
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return leaveTypeName;
	}
}
