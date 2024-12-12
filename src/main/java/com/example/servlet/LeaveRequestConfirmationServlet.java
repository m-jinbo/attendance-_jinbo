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

		// 入力データを取得
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String leaveTypeParam = request.getParameter("leaveType");
		String reason = request.getParameter("reason");
		String action = request.getParameter("action"); // 戻るか確認画面へ進むかの判定

		// デバッグ用ログ
		System.out.println("startDate: " + startDate);
		System.out.println("endDate: " + endDate);
		System.out.println("leaveTypeParam: " + leaveTypeParam);
		System.out.println("reason: " + reason);
		System.out.println("action: " + action);

		// 日付を年、月、日に分割してリクエスト属性に設定
		if (startDate != null && startDate.contains("-")) {
			String[] startParts = startDate.split("-");
			if (startParts.length == 3) { // 分割結果の配列が3つ（年・月・日）であることを確認
				request.setAttribute("startYear", startParts[0]);
				request.setAttribute("startMonth", startParts[1]);
				request.setAttribute("startDay", startParts[2]);
			} else {
				System.out.println("Error: Invalid startDate format: " + startDate);
			}
		} else {
			System.out.println("Error: startDate is null or does not contain '-'");
		}

		if (endDate != null && endDate.contains("-")) {
			String[] endParts = endDate.split("-");
			if (endParts.length == 3) { // 分割結果の配列が3つ（年・月・日）であることを確認
				request.setAttribute("endYear", endParts[0]);
				request.setAttribute("endMonth", endParts[1]);
				request.setAttribute("endDay", endParts[2]);
			} else {
				System.out.println("Error: Invalid endDate format: " + endDate);
			}
		} else {
			System.out.println("Error: endDate is null or does not contain '-'");
		}

		// 入力データをリクエストスコープに設定
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		request.setAttribute("leaveType", leaveTypeParam);
		request.setAttribute("reason", reason);

		// 戻るボタンが押された場合、登録画面に戻る
		if ("back".equals(action)) {
			request.getRequestDispatcher("leave_request.jsp").forward(request, response);
			return;
		}

		// バリデーション
		if (leaveTypeParam == null || leaveTypeParam.trim().isEmpty()) {
			request.setAttribute("errorMessage", "休暇種類が選択されていません。");
			request.getRequestDispatcher("leave_request.jsp").forward(request, response);
			return;
		}

		int leaveType;
		try {
			leaveType = Integer.parseInt(leaveTypeParam);
		} catch (NumberFormatException e) {
			request.setAttribute("errorMessage", "休暇種類が無効です。");
			request.getRequestDispatcher("leave_request.jsp").forward(request, response);
			return;
		}

		// 休暇種類名を取得
		String leaveTypeName = null;
		try {
			leaveTypeName = getLeaveTypeName(leaveType);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// 確認画面に進む場合
		request.setAttribute("leaveTypeName", leaveTypeName);
		request.getRequestDispatcher("leaveRequestConfirmation.jsp").forward(request, response);
	}

	private String getLeaveTypeName(int leaveType) throws ClassNotFoundException {
		String leaveTypeName = "不明な休暇種類"; // デフォルト値
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("SELECT type_name FROM leave_types WHERE type_id = ?")) {
			pstmt.setInt(1, leaveType);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					leaveTypeName = rs.getString("type_name");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return leaveTypeName;
	}
}
