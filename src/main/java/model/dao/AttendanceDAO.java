package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.Attendance;

public class AttendanceDAO {

	public List<Attendance> getAttendanceListForMonth(int userId, int year, int month)
			throws SQLException, ClassNotFoundException {

		List<Attendance> attendanceList = new ArrayList<>();
		String query = "SELECT date, attendance_time, break_time, total_hours "
				+ "FROM attendance "
				+ "WHERE user_id = ? AND YEAR(date) = ? AND MONTH(date) = ? "
				+ "ORDER BY date ASC";

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement ps = con.prepareStatement(query)) {

			ps.setInt(1, userId);
			ps.setInt(2, year);
			ps.setInt(3, month);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Attendance att = new Attendance();
					att.setDate(rs.getDate("date"));
					att.setAttendanceTime(rs.getTime("attendance_time"));
					att.setBreakTime(rs.getTime("break_time"));
					att.setTotalHours(rs.getBigDecimal("total_hours")); // BigDecimal 型で取得
					attendanceList.add(att);
				}
			}
		}
		return attendanceList;
	}
}
