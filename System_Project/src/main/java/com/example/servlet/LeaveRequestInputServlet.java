package com.example.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LeaveRequestInputServlet")
public class LeaveRequestInputServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// リクエストの文字エンコーディングをUTF-8に設定
		request.setCharacterEncoding("UTF-8");

		// 開始日と終了日を組み立て
		String startDate = request.getParameter("startYear") + "-"
				+ String.format("%02d", Integer.parseInt(request.getParameter("startMonth"))) + "-"
				+ String.format("%02d", Integer.parseInt(request.getParameter("startDay")));
		String endDate = request.getParameter("endYear") + "-"
				+ String.format("%02d", Integer.parseInt(request.getParameter("endMonth"))) + "-"
				+ String.format("%02d", Integer.parseInt(request.getParameter("endDay")));

		// 他のパラメータを取得
		String leaveType = request.getParameter("leaveType");
		String reason = request.getParameter("reason");

		// 休暇種類IDを名前に変換
		String leaveTypeName = convertLeaveType(leaveType);

		// リクエスト属性に設定
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		request.setAttribute("leaveType", leaveType);
		request.setAttribute("leaveTypeName", leaveTypeName);
		request.setAttribute("reason", reason);

		// 確認画面へフォワード
		request.getRequestDispatcher("leaveRequestConfirmation.jsp").forward(request, response);
	}

	/**
	 * 休暇種類IDを名前に変換するメソッド
	 *
	 * @param leaveType 休暇種類ID
	 * @return 休暇種類名
	 */
	private String convertLeaveType(String leaveType) {
		switch (leaveType) {
		case "1":
			return "有給";
		case "2":
			return "欠勤";
		case "3":
			return "特別休暇";
		default:
			return "不明な休暇種類";
		}
	}
}
