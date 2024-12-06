package com.example.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.LocationDAO;

@WebServlet("/UserRegistrationServlet")
public class UserRegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// リクエストの文字エンコーディングを設定
		request.setCharacterEncoding("UTF-8");

		// リクエストパラメータの取得
		String name = request.getParameter("name"); // 名前
		String locationId = request.getParameter("location"); // 拠点ID
		String username = request.getParameter("employeeId"); // 社員ID (DBではusername)
		String password = request.getParameter("password"); // パスワード

		// 必須チェック（エラー処理は既存コードを踏襲）
		if (name == null || name.trim().isEmpty() ||
				locationId == null || locationId.trim().isEmpty() ||
				username == null || username.trim().isEmpty() ||
				password == null || password.trim().isEmpty()) {
			request.setAttribute("errorMessage", "全て入力してください。");
			request.getRequestDispatcher("user_registration.jsp").forward(request, response);
			return;
		}

		// 拠点名を取得
		String locationName = null;
		try {
			LocationDAO locationDAO = new LocationDAO();
			locationName = locationDAO.getLocationNameById(locationId);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "拠点情報の取得に失敗しました。");
			request.getRequestDispatcher("user_registration.jsp").forward(request, response);
			return;
		}

		// リクエストスコープにデータを保存
		request.setAttribute("name", name);
		request.setAttribute("locationName", locationName);
		request.setAttribute("employeeId", username); // 修正: "username"を"employeeId"に統一
		request.setAttribute("password", password);

		// 確認画面へフォワード
		request.getRequestDispatcher("user_registrationConfirmation.jsp").forward(request, response);
	}
}
