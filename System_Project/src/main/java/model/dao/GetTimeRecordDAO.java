package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetTimeRecordDAO {

	// 最新の登録時間を取得
	public String getLastRecordTime(int employeeId) throws SQLException, ClassNotFoundException {
		String sql = "SELECT record_time FROM time_records WHERE employee_id = ? ORDER BY record_time DESC LIMIT 1";

		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, employeeId);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getString("record_time");
				}
			}
		}
		return "登録された時間が見つかりません。";
	}
}
