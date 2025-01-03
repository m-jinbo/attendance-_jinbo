package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TimeRecordDAO {

	/**
	 * 打刻を登録するメソッド
	 *
	 * @param userId     ユーザーID
	 * @param recordType 打刻タイプ（1: 出勤, 2: 休憩開始, 3: 休憩終了, 4: 退勤）
	 * @return 登録成功: true, 失敗: false
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean insertTimeRecord(int userId, int recordType) throws SQLException, ClassNotFoundException {
		String sql = "INSERT INTO time_records (user_id, record_type, record_time) VALUES (?, ?, NOW())";

		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, userId);
			pstmt.setInt(2, recordType);

			int rowsInserted = pstmt.executeUpdate();
			return rowsInserted > 0;
		}
	}

	/**
	 * 最新の打刻時間を取得するメソッド
	 *
	 * @param userId ユーザーID
	 * @return 最新の打刻時間（文字列形式）または "登録された時間が見つかりません。"
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public String getLastRecordTime(int userId) throws SQLException, ClassNotFoundException {
		String sql = "SELECT record_time FROM time_records WHERE user_id = ? ORDER BY record_time DESC LIMIT 1";

		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, userId);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getString("record_time");
				}
			}
		}
		return "登録された時間が見つかりません。";
	}
}
