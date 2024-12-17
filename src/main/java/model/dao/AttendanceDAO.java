package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import model.entity.Attendance;

public class AttendanceDAO {

	private static final Logger logger = Logger.getLogger(AttendanceDAO.class.getName());

	/**
	 * 指定した社員ID、年、月に基づき、勤怠情報を取得する
	 * @param employeeId 社員ID
	 * @param year 年
	 * @param month 月
	 * @return 勤怠情報リスト
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<Attendance> getAttendanceListForMonth(int userId, int year, int month)
			throws SQLException, ClassNotFoundException {

		List<Attendance> attendanceList = new ArrayList<>();

		String query = "SELECT id, user_id, date, attendance_time, break_time, total_hours "
				+ "FROM attendance "
				+ "WHERE employee_id = ? AND YEAR(date) = ? AND MONTH(date) = ? "
				+ "ORDER BY date ASC";

		// データベース接続
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(query)) {

			stmt.setInt(1, userId);
			stmt.setInt(2, year);
			stmt.setInt(3, month);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Attendance attendance = new Attendance();

					// 各カラムのデータを取得して設定
					attendance.setId(rs.getInt("id"));
					attendance.setUserId(rs.getInt("employee_id"));
					attendance.setDate(rs.getDate("date")); // 勤務日
					attendance.setAttendanceTime(rs.getTime("attendance_time")); // 出勤時間
					attendance.setBreakTime(rs.getTime("break_time")); // 退勤時間
					attendance.setTotalHours(rs.getTime("total_hours")); // 合計勤務時間

					// リストに追加
					attendanceList.add(attendance);
				}
			}
		}

		return attendanceList;
	}
}
