package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LeaveRequestDAO {

	/**
	 * 休暇申請を登録するメソッド
	 *
	 * @param employeeId 申請者の従業員ID
	 * @param leaveType 休暇の種類
	 * @param startDate 休暇開始日
	 * @param endDate 休暇終了日
	 * @param reason 休暇の理由
	 * @return 登録が成功した場合 true, 失敗した場合 false
	 */
	public boolean insertLeaveRequest(int employeeId, int leaveType, String startDate, String endDate, String reason) throws ClassNotFoundException {
		String sql = "INSERT INTO leave_requests (employee_id, leave_type, start_date, end_date, reason) VALUES (?, ?, ?, ?, ?)";

		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// パラメータを設定
			pstmt.setInt(1, employeeId);
			pstmt.setInt(2, leaveType);
			pstmt.setString(3, startDate);
			pstmt.setString(4, endDate);
			pstmt.setString(5, reason);

			// SQL 実行
			int rowsInserted = pstmt.executeUpdate();

			// 完了画面へリダイレクトが必要な場合の処理
			if (rowsInserted > 0) {
				System.out.println("休暇申請が正常に登録されました。");
				return true; // 登録成功
			} else {
				System.out.println("休暇申請の登録に失敗しました。");
				return false; // 登録失敗
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false; // エラー時も false を返す
		}
	}
}