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
				+ "MIN(CASE WHEN t2.type_name = '出勤' THEN t1.record_time END) AS start_time, "
				+ "MAX(CASE WHEN t2.type_name = '退勤' THEN t1.record_time END) AS end_time, "
				+ "SEC_TO_TIME(COALESCE(SUM(CASE "
				+ "WHEN t2.type_name = '休憩終了' THEN UNIX_TIMESTAMP(t1.record_time) "
				+ "WHEN t2.type_name = '休憩開始' THEN -UNIX_TIMESTAMP(t1.record_time) "
				+ "ELSE 0 END), 0)) AS break_time, "
				+ "SEC_TO_TIME(TIMESTAMPDIFF(SECOND, "
				+ "MIN(CASE WHEN t2.type_name = '出勤' THEN t1.record_time END), "
				+ "MAX(CASE WHEN t2.type_name = '退勤' THEN t1.record_time END)) - "
				+ "COALESCE(SUM(CASE "
				+ "WHEN t2.type_name = '休憩終了' THEN UNIX_TIMESTAMP(t1.record_time) "
				+ "WHEN t2.type_name = '休憩開始' THEN -UNIX_TIMESTAMP(t1.record_time) "
				+ "ELSE 0 END), 0)) AS working_time "
				+ "FROM time_records t1 "
				+ "JOIN record_types t2 ON t1.record_type = t2.type_id "
				+ "WHERE t1.user_id = ? AND t1.record_time BETWEEN ? AND ? "
				+ "GROUP BY DATE(t1.record_time)";

		System.out.println("Executing SQL: " + sql);

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, userId);
			pstmt.setString(2, startDate);
			pstmt.setString(3, endDate);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					System.out.println("Fetched Row: "
							+ "Work Date: " + rs.getDate("work_date")
							+ ", Start Time: " + rs.getTime("start_time")
							+ ", End Time: " + rs.getTime("end_time")
							+ ", Break Time: " + rs.getString("break_time")
							+ ", Working Time: " + rs.getString("working_time"));

					DailyAttendance attendance = new DailyAttendance();
					attendance.setWorkDate(rs.getDate("work_date"));
					attendance.setStartTime(rs.getTime("start_time"));
					attendance.setEndTime(rs.getTime("end_time"));
					attendance.setBreakTime(rs.getString("break_time"));
					attendance.setWorkingTime(rs.getString("working_time"));
					attendanceList.add(attendance);
				}
			}
		}
		return attendanceList;
	}

}