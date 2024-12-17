package com.example.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.UserFindDAO; // UserFindDAO クラス
import model.entity.Employees; // Employees エンティティ

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		// リクエストパラメータの取得
		String userIdParam = request.getParameter("userid");
		String password = request.getParameter("password");

		boolean hasError = false; // エラーフラグ
		Integer userId = null;

		// 社員IDの検証
		if (userIdParam == null || userIdParam.isEmpty()) {
			request.setAttribute("userIdError", "社員IDを入力してください。");
			hasError = true;
		} else {
			try {
				userId = Integer.parseInt(userIdParam);
			} catch (NumberFormatException e) {
				request.setAttribute("userIdError", "社員IDは数字でなければなりません。");
				hasError = true;
			}
		}

		// パスワードの検証
		if (password == null || password.isEmpty()) {
			request.setAttribute("passwordError", "パスワードを入力してください。");
			hasError = true;
		}

		// エラーがあればログイン画面に戻す
		if (hasError) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("top.jsp");
			dispatcher.forward(request, response);
			return;
		}

		// UserFindDAO インスタンス化
		UserFindDAO userFindDAO = new UserFindDAO();
		Employees employee = new Employees();
		employee.setUserId(userId); // 修正: 正しい setter 使用
		employee.setPassword(password); // パスワード設定

		// 認証処理
		try {
			Employees authenticatedEmployee = userFindDAO.findAccount(employee);

			if (authenticatedEmployee != null) { // ログイン成功
				HttpSession session = request.getSession();
				session.setAttribute("userId", authenticatedEmployee.getUserId());
				session.setAttribute("name", authenticatedEmployee.getName());

				// 確認フォームへフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("confirmForm.jsp");
				dispatcher.forward(request, response);
			} else { // ログイン失敗
				request.setAttribute("error", "※社員IDまたはパスワードに誤りがあります。");
				RequestDispatcher dispatcher = request.getRequestDispatcher("top.jsp");
				dispatcher.forward(request, response);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			request.setAttribute("error", "データベースエラーが発生しました。再度お試しください。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("top.jsp");
			dispatcher.forward(request, response);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("top.jsp").forward(request, response);
	}
}
