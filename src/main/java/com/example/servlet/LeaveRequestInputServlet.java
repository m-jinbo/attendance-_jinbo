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

		// パラメータ取得
		String startYear = request.getParameter("startYear");
		String startMonth = request.getParameter("startMonth");
		String startDay = request.getParameter("startDay");
		String endYear = request.getParameter("endYear");
		String endMonth = request.getParameter("endMonth");
		String endDay = request.getParameter("endDay");
		String leaveType = request.getParameter("leaveType");
		String reason = request.getParameter("reason");

		// 入力チェック
		if (isNullOrEmpty(startYear) || isNullOrEmpty(startMonth) || isNullOrEmpty(startDay) ||
				isNullOrEmpty(endYear) || isNullOrEmpty(endMonth) || isNullOrEmpty(endDay) ||
				isNullOrEmpty(leaveType) || isNullOrEmpty(reason)) {

			// エラーメッセージを設定し、入力内容を保持してフォームに戻る
			request.setAttribute("errorMessage", "全ての項目を入力してください。");
			request.setAttribute("startYear", startYear);
			request.setAttribute("startMonth", startMonth);
			request.setAttribute("startDay", startDay);
			request.setAttribute("endYear", endYear);
			request.setAttribute("endMonth", endMonth);
			request.setAttribute("endDay", endDay);
			request.setAttribute("leaveType", leaveType);
			request.setAttribute("reason", reason);

			// 入力画面にフォワード
			request.getRequestDispatcher("leave_request.jsp").forward(request, response);
			return;
		}

		// 開始日と終了日を組み立て
		String startDate = startYear + "-" +
				String.format("%02d", Integer.parseInt(startMonth)) + "-" +
				String.format("%02d", Integer.parseInt(startDay));
		String endDate = endYear + "-" +
				String.format("%02d", Integer.parseInt(endMonth)) + "-" +
				String.format("%02d", Integer.parseInt(endDay));

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
	 * 入力値が空かどうかを判定するヘルパーメソッド
	 *
	 * @param param 入力値
	 * @return true: 空またはnull, false: 非空
	 */
	private boolean isNullOrEmpty(String param) {
		return param == null || param.trim().isEmpty();
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
