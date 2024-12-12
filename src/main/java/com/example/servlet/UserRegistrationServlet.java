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
		
		request.setCharacterEncoding("UTF-8");

		// リクエストパラメータの取得
		String name = request.getParameter("name");
		String locationId = request.getParameter("locationId");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		// 入力検証
		if (name == null || name.trim().isEmpty() ||
				locationId == null || locationId.trim().isEmpty() ||
				username == null || username.trim().isEmpty() ||
				password == null || password.trim().isEmpty()) {

			// エラー時の処理
			request.setAttribute("errorMessage", "全ての項目を入力してください。");
			request.setAttribute("name", new String(name.getBytes("ISO-8859-1"), "UTF-8"));
			request.setAttribute("locationId", locationId);
			request.setAttribute("username", username);
			request.setAttribute("password", password);
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

		// 確認画面用データ設定
		request.setAttribute("pageTitle", "ユーザー登録");
		request.setAttribute("stylesheetName", "user_registrationConfirmation.css");
		request.setAttribute("logoPath", "images/seasissst.png");
		request.setAttribute("logoAlt", "SE Assist Logo");
		request.setAttribute("confirmationMessage", "以下の内容で登録しますか？");
		request.setAttribute("formAction", "CompleteRegistrationServlet");
		request.setAttribute("submitButtonText", "登録");
		request.setAttribute("backButtonLink", "user_registration.jsp");
		request.setAttribute("backButtonText", "戻る");

		// 入力内容を確認画面に送る
		request.setAttribute("name", name);
		request.setAttribute("location", locationName);
		request.setAttribute("locationId", locationId);
		request.setAttribute("username", username);
		request.setAttribute("password", password);

		// 確認画面へフォワード
		request.getRequestDispatcher("user_registrationConfirmation.jsp").forward(request, response);
	}
}
