package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TimeRecordDAO {

	/**
	 * 打刻を登録するメソッド
	 *
	 * @param employeeId 従業員ID
	 * @param recordType 打刻タイプ（1: 出勤, 2: 休憩開始, 3: 休憩終了, 4: 退勤）
	 * @return 登録成功: true, 失敗: false
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public boolean insertTimeRecord(int employeeId, int recordType) throws SQLException, ClassNotFoundException {
		
		String sql = "INSERT INTO time_records (employee_id, record_type, record_time) VALUES (?, ?, NOW())";

		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, employeeId);
			pstmt.setInt(2, recordType);

			int rowsInserted = pstmt.executeUpdate();
			return rowsInserted > 0;

		} 
	}

	public String getLatestTimeRecord(int employeeId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}
