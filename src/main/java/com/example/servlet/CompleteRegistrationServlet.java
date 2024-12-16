package com.example.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.UserDAO;
import model.entity.EmployeeProfile;
import model.entity.Employees;

@WebServlet("/CompleteRegistrationServlet")
public class CompleteRegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// リクエストの文字エンコーディングを設定
		request.setCharacterEncoding("UTF-8");
		try {
			// ユーザー情報を取得
			String name = request.getParameter("name");
			String userId = request.getParameter("user_id"); // 社員ID (変更)
			String password = request.getParameter("password");
			String locationIdParam = request.getParameter("locationId");

			// 入力検証
			if (name == null || name.trim().isEmpty() ||
					userId == null || userId.trim().isEmpty() ||
					password == null || password.trim().isEmpty() ||
					locationIdParam == null || locationIdParam.trim().isEmpty()) {
				request.setAttribute("errorMessage", "全ての項目を入力してください。");
				request.getRequestDispatcher("user_registration.jsp").forward(request, response);
				return;
			}

			// locationId を整数に変換
			int locationId = Integer.parseInt(locationIdParam);

			// Employees モデルを作成
			Employees employee = new Employees();
			employee.setName(name);
			employee.setUserId(userId); // user_id に変更
			employee.setPassword(password);
			employee.setRoleId(2); // 一般ユーザーを想定

			// EmployeeProfile モデルを作成
			EmployeeProfile profile = new EmployeeProfile();
			profile.setLocationId(locationId);

			// DAO を使って登録処理
			UserDAO userDAO = new UserDAO();
			boolean isRegistered = userDAO.registerEmployeeWithProfile(employee, profile);

			if (isRegistered) {
				// 登録成功時の完了画面リダイレクト
				response.sendRedirect("user_registrationComplete.jsp");
			} else {
				// 登録失敗時
				request.setAttribute("errorMessage", "登録に失敗しました。");
				request.getRequestDispatcher("user_registration.jsp").forward(request, response);
			}

		} catch (NumberFormatException e) {
			// locationId の形式が不正
			e.printStackTrace();
			request.setAttribute("errorMessage", "拠点の選択が正しくありません。");
			request.getRequestDispatcher("user_registration.jsp").forward(request, response);
		} catch (Exception e) {
			// その他のシステムエラー
			e.printStackTrace();
			request.setAttribute("errorMessage", "システムエラーが発生しました。");
			request.getRequestDispatcher("user_registration.jsp").forward(request, response);
		}
	}
}
