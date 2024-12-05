package com.example.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import model.dao.LeaveRequestDAO;

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

		// DAO を使用して休暇申請を登録
		LeaveRequestDAO leaveRequestDAO = new LeaveRequestDAO();
		boolean isInserted = leaveRequestDAO.insertLeaveRequest(employeeId, leaveType, startDate, endDate, reason);

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
