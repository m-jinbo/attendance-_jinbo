package com.example.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.LeaveRequestDAO;

@WebServlet("/LeaveRequestSubmitServlet")
public class LeaveRequestSubmitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// リクエストの文字エンコーディングをUTF-8に設定
		request.setCharacterEncoding("UTF-8");

		// セッションから user_id を取得
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			// ログインしていない場合はログインページへリダイレクト
			response.sendRedirect("login.jsp");
			return;
		}
		int userId = (Integer) session.getAttribute("userId");

		// 入力データを取得
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String leaveTypeParam = request.getParameter("leaveType");
		String reason = request.getParameter("reason");

		// デバッグ用ログ
		System.out.println("userId: " + userId);
		System.out.println("startDate: " + startDate);
		System.out.println("endDate: " + endDate);
		System.out.println("leaveType: " + leaveTypeParam);
		System.out.println("reason: " + reason);

		int leaveType;

		// leaveType のバリデーション
		if (leaveTypeParam == null || leaveTypeParam.trim().isEmpty()) {
			request.setAttribute("errorMessage", "休暇タイプが指定されていません。");
			request.getRequestDispatcher("leaveRequestConfirmation.jsp").forward(request, response);
			return;
		}

		try {
			leaveType = Integer.parseInt(leaveTypeParam);
		} catch (NumberFormatException e) {
			request.setAttribute("errorMessage", "無効な休暇タイプが指定されました。");
			request.getRequestDispatcher("leaveRequestConfirmation.jsp").forward(request, response);
			return;
		}

		// DAO を使用して休暇申請を登録
		LeaveRequestDAO leaveRequestDAO = new LeaveRequestDAO();
		boolean isInserted = false;
		try {
			isInserted = leaveRequestDAO.insertLeaveRequest(userId, leaveType, startDate, endDate, reason);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "システムエラーが発生しました。");
			request.getRequestDispatcher("leaveRequestConfirmation.jsp").forward(request, response);
			return;
		}

		if (isInserted) {
			// 登録成功時は完了画面へリダイレクト
			response.sendRedirect("leaveRequestComplete.jsp");
		} else {
			// 登録失敗時はエラーメッセージを設定して確認画面へ戻る
			request.setAttribute("errorMessage", "申請に失敗しました。再度お試しください。");
			request.getRequestDispatcher("leaveRequestConfirmation.jsp").forward(request, response);
		}
	}
}
