package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.DailyAttendance;

public class DailyAttendanceDAO {
	private Connection conn;

	public DailyAttendanceDAO(Connection conn) {
		this.conn = conn;
	}

	public List<DailyAttendance> getAttendanceByUserId(String userId, String startDate, String endDate)
			throws SQLException {
		List<DailyAttendance> attendanceList = new ArrayList<>();
		String sql = "SELECT "
				+ "DATE(t1.record_time) AS work_date, "
				+ "TIME_FORMAT(MIN(CASE WHEN t2.type_name = '出勤' THEN t1.record_time END), '%H:%i') AS start_time, "
				+ "TIME_FORMAT(MAX(CASE WHEN t2.type_name = '退勤' THEN t1.record_time END), '%H:%i') AS end_time, "
				+ "IFNULL(SEC_TO_TIME(COALESCE(SUM(CASE "
				+ "WHEN t2.type_name = '休憩終了' THEN UNIX_TIMESTAMP(t1.record_time) "
				+ "WHEN t2.type_name = '休憩開始' THEN -UNIX_TIMESTAMP(t1.record_time) "
				+ "ELSE 0 END), 0)), '00:00:00') AS break_time, "
				+ "IFNULL(SEC_TO_TIME(TIMESTAMPDIFF(SECOND, "
				+ "MIN(CASE WHEN t2.type_name = '出勤' THEN t1.record_time END), "
				+ "MAX(CASE WHEN t2.type_name = '退勤' THEN t1.record_time END)) - "
				+ "COALESCE(SUM(CASE "
				+ "WHEN t2.type_name = '休憩終了' THEN UNIX_TIMESTAMP(t1.record_time) "
				+ "WHEN t2.type_name = '休憩開始' THEN -UNIX_TIMESTAMP(t1.record_time) "
				+ "ELSE 0 END), 0)), '00:00:00') AS working_time "
				+ "FROM time_records t1 "
				+ "JOIN record_types t2 ON t1.record_type = t2.type_id "
				+ "WHERE t1.user_id = ? AND t1.record_time BETWEEN ? AND ? "
				+ "GROUP BY DATE(t1.record_time)";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, userId);
			pstmt.setString(2, startDate);
			pstmt.setString(3, endDate);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					DailyAttendance attendance = new DailyAttendance();
					attendance.setWorkDate(rs.getDate("work_date"));
					attendance.setStartTime(rs.getString("start_time"));
					attendance.setEndTime(rs.getString("end_time"));
					attendance.setBreakTime(rs.getString("break_time"));
					attendance.setWorkingTime(rs.getString("working_time"));
					attendanceList.add(attendance);
				}
			}
		}

		return attendanceList;
	}
}
