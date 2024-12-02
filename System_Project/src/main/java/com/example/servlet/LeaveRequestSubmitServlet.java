package com.example.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import model.dao.ConnectionManager;

@WebServlet("/LeaveRequestSubmitServlet")
public class LeaveRequestSubmitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(javax.servlet.http.HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 // リクエストの文字エンコーディングをUTF-8に設定
		request.setCharacterEncoding("UTF-8");

		// 入力データを取得
		int employeeId = 1; // 固定値（ログイン機能未実装）
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		int leaveType = Integer.parseInt(request.getParameter("leaveType"));
		String reason = request.getParameter("reason");

		// DB登録処理
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"INSERT INTO leave_requests (employee_id, leave_type, start_date, end_date, reason) VALUES (?, ?, ?, ?, ?)")) {
			pstmt.setInt(1, employeeId);
			pstmt.setInt(2, leaveType);
			pstmt.setString(3, startDate);
			pstmt.setString(4, endDate);
			pstmt.setString(5, reason);
			pstmt.executeUpdate();

			// 完了画面へリダイレクト
			response.sendRedirect("leaveRequestComplete.jsp");

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "申請に失敗しました: " + e.getMessage());
			request.getRequestDispatcher("leaveRequestConfirmation.jsp").forward(request, response);
		}
	}
}
