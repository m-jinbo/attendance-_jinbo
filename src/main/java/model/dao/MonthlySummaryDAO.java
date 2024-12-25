package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MonthlySummaryDAO {
	private final Connection conn;

	public MonthlySummaryDAO(Connection conn) {
		this.conn = conn;
	}

	public Map<String, String> getFullMonthlySummary(int userId, String yearMonth) throws SQLException {
		String sql = """
				WITH RECURSIVE calendar AS (
				    SELECT DATE(CONCAT(?, '-01')) AS work_date
				    UNION ALL
				    SELECT work_date + INTERVAL 1 DAY
				    FROM calendar
				    WHERE work_date < LAST_DAY(CONCAT(?, '-01'))
				),
				raw_work_hours AS (
				    SELECT
				        DATE(record_time) AS work_date,
				        record_type,
				        record_time
				    FROM time_records
				    WHERE user_id = ?
				      AND DATE_FORMAT(record_time, '%Y-%m') = ?
				),
				work_hours AS (
				    SELECT
				        work_date,
				        MIN(CASE WHEN record_type = 1 THEN record_time END) AS start_time,
				        MAX(CASE WHEN record_type = 2 THEN record_time END) AS end_time,
				        CASE
				            WHEN MIN(CASE WHEN record_type = 1 THEN record_time END) IS NOT NULL
				                 AND MAX(CASE WHEN record_type = 2 THEN record_time END) IS NOT NULL
				            THEN TIMESTAMPDIFF(
				                SECOND,
				                MIN(CASE WHEN record_type = 1 THEN record_time END),
				                MAX(CASE WHEN record_type = 2 THEN record_time END)
				            )
				            ELSE 0
				        END AS work_seconds,
				        SUM(
				            CASE
				                WHEN HOUR(record_time) >= 22 THEN TIMESTAMPDIFF(
				                    SECOND,
				                    record_time,
				                    ADDTIME(record_time, '01:00:00')
				                )
				                ELSE 0
				            END
				        ) AS late_night_seconds
				    FROM raw_work_hours
				    GROUP BY work_date
				),
				breaks AS (
				    SELECT
				        DATE(start_time) AS break_date,
				        SUM(GREATEST(TIMESTAMPDIFF(SECOND, start_time, end_time), 0)) AS break_seconds
				    FROM (
				        SELECT
				            record_time AS start_time,
				            LEAD(record_time) OVER (PARTITION BY user_id ORDER BY record_time) AS end_time
				        FROM time_records
				        WHERE user_id = ?
				          AND record_type IN (3, 4)
				    ) AS break_intervals
				    WHERE start_time IS NOT NULL
				      AND end_time IS NOT NULL
				      AND DATE(start_time) = DATE(end_time)
				    GROUP BY DATE(start_time)
				),
				leave_summary AS (
				    SELECT
				        SUM(CASE WHEN leave_id = 1 THEN DATEDIFF(end_date, start_date) + 1 ELSE 0 END) AS paid_leave_days,
				        SUM(CASE WHEN leave_id = 2 THEN DATEDIFF(end_date, start_date) + 1 ELSE 0 END) AS absence_days
				    FROM leave_requests
				    WHERE user_id = ?
				      AND DATE_FORMAT(start_date, '%Y-%m') = ?
				),
				holiday_work_days AS (
				    SELECT
				        COUNT(DISTINCT t.work_date) AS holiday_work_days
				    FROM work_hours t
				    WHERE WEEKDAY(t.work_date) IN (5, 6)
				       OR EXISTS (
				           SELECT 1
				           FROM leave_requests lr
				           WHERE lr.user_id = ?
				             AND t.work_date BETWEEN lr.start_date AND lr.end_date
				       )
				),
				summary AS (
				    SELECT
				        COUNT(CASE WHEN WEEKDAY(c.work_date) < 5 THEN 1 END) AS scheduled_days,
				        COUNT(CASE WHEN WEEKDAY(c.work_date) < 5 THEN 1 END) * 8 AS scheduled_work_hours,
				        COUNT(DISTINCT w.work_date) AS actual_work_days,
				        (SELECT holiday_work_days FROM holiday_work_days) AS holiday_work_days,
				        COALESCE(MAX(ls.absence_days), 0) AS absence_days,
				        COALESCE(MAX(ls.paid_leave_days), 0) AS paid_leave_days,
				        FLOOR(SUM(w.work_seconds) / 3600) AS actual_work_hours,
				        FLOOR(SUM(w.late_night_seconds) / 3600) AS late_night_hours,
				        FLOOR(SUM(GREATEST(w.work_seconds - (8 * 3600), 0)) / 3600) AS overtime_hours,
				        FLOOR(SUM(b.break_seconds) / 3600) AS total_break_hours
				    FROM calendar c
				    LEFT JOIN work_hours w ON c.work_date = w.work_date
				    LEFT JOIN breaks b ON c.work_date = b.break_date
				    CROSS JOIN leave_summary ls
				    WHERE DATE_FORMAT(c.work_date, '%Y-%m') = ?
				)
				SELECT * FROM summary;
				""";

		System.out.println("Executing getFullMonthlySummary with yearMonth: " + yearMonth + ", userId: " + userId);

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, yearMonth);
			pstmt.setString(2, yearMonth);
			pstmt.setInt(3, userId);
			pstmt.setString(4, yearMonth);
			pstmt.setInt(5, userId);
			pstmt.setInt(6, userId);
			pstmt.setString(7, yearMonth);
			pstmt.setInt(8, userId);
			pstmt.setString(9, yearMonth);

			System.out.println("PreparedStatement Parameters set successfully.");

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					Map<String, String> summary = new HashMap<>();
					summary.put("scheduled_days", rs.getString("scheduled_days"));
					summary.put("actual_work_days", rs.getString("actual_work_days"));
					summary.put("holiday_work_days", rs.getString("holiday_work_days"));
					summary.put("scheduled_work_hours", rs.getString("scheduled_work_hours") + "時間");
					summary.put("actual_work_hours", rs.getString("actual_work_hours") + "時間");
					summary.put("overtime_hours", rs.getString("overtime_hours") + "時間");
					summary.put("late_night_hours", rs.getString("late_night_hours") + "時間");
					summary.put("total_break_hours", rs.getString("total_break_hours") + "時間");
					summary.put("paid_leave_days", rs.getString("paid_leave_days"));
					summary.put("absence_days", rs.getString("absence_days"));

					System.out.println("Summary Data Retrieved: " + summary);
					return summary;
				} else {
					System.out.println("No data found for getFullMonthlySummary.");
					return null;
				}
			}
		}
	}

	public Map<String, String> getYearlySummary(int userId, String year) throws SQLException {
		String sql = """
				WITH RECURSIVE calendar AS (
				    SELECT DATE(CONCAT(?, '-01-01')) AS work_date
				    UNION ALL
				    SELECT work_date + INTERVAL 1 DAY
				    FROM calendar
				    WHERE work_date < LAST_DAY(CONCAT(?, '-12-31'))
				),
				filtered_calendar AS (
				    SELECT work_date
				    FROM calendar
				    WHERE WEEKDAY(work_date) < 5 -- 平日のみ
				),
				raw_work_hours AS (
				    SELECT
				        DATE(record_time) AS work_date,
				        record_type,
				        record_time
				    FROM time_records
				    WHERE user_id = ?
				      AND DATE_FORMAT(record_time, '%Y') = ?
				),
				work_hours AS (
				    SELECT
				        work_date,
				        MIN(CASE WHEN record_type = 1 THEN record_time END) AS start_time,
				        MAX(CASE WHEN record_type = 2 THEN record_time END) AS end_time,
				        CASE
				            WHEN MIN(CASE WHEN record_type = 1 THEN record_time END) IS NOT NULL
				                 AND MAX(CASE WHEN record_type = 2 THEN record_time END) IS NOT NULL
				            THEN TIMESTAMPDIFF(
				                SECOND,
				                MIN(CASE WHEN record_type = 1 THEN record_time END),
				                MAX(CASE WHEN record_type = 2 THEN record_time END)
				            )
				            ELSE 0
				        END AS work_seconds,
				        SUM(
				            CASE
				                WHEN HOUR(record_time) >= 22 THEN TIMESTAMPDIFF(
				                    SECOND,
				                    record_time,
				                    ADDTIME(record_time, '01:00:00')
				                )
				                ELSE 0
				            END
				        ) AS late_night_seconds
				    FROM raw_work_hours
				    GROUP BY work_date
				),
				breaks AS (
				    SELECT
				        DATE(start_time) AS break_date,
				        SUM(GREATEST(TIMESTAMPDIFF(SECOND, start_time, end_time), 0)) AS break_seconds
				    FROM (
				        SELECT
				            record_time AS start_time,
				            LEAD(record_time) OVER (PARTITION BY user_id ORDER BY record_time) AS end_time
				        FROM time_records
				        WHERE user_id = ?
				          AND record_type IN (3, 4)
				    ) AS break_intervals
				    WHERE start_time IS NOT NULL
				      AND end_time IS NOT NULL
				      AND DATE(start_time) = DATE(end_time)
				    GROUP BY DATE(start_time)
				),
				leave_summary AS (
				    SELECT
				        SUM(CASE WHEN leave_id = 1 THEN DATEDIFF(end_date, start_date) + 1 ELSE 0 END) AS paid_leave_days,
				        SUM(CASE WHEN leave_id = 2 THEN DATEDIFF(end_date, start_date) + 1 ELSE 0 END) AS absence_days
				    FROM leave_requests
				    WHERE user_id = ?
				      AND DATE_FORMAT(start_date, '%Y') = ?
				),
				holiday_work_days AS (
				    SELECT
				        COUNT(DISTINCT t.work_date) AS holiday_work_days
				    FROM work_hours t
				    WHERE WEEKDAY(t.work_date) IN (5, 6) -- 土日
				       OR EXISTS (
				           SELECT 1
				           FROM leave_requests lr
				           WHERE lr.user_id = ?
				             AND t.work_date BETWEEN lr.start_date AND lr.end_date
				       )
				),
				yearly_summary AS (
				    SELECT
				        COUNT(fc.work_date) AS scheduled_days, -- 平日数
				        COUNT(fc.work_date) * 8 AS scheduled_work_hours, -- 平日数 × 8時間
				        COUNT(DISTINCT w.work_date) AS actual_work_days,
				        (SELECT holiday_work_days FROM holiday_work_days) AS holiday_work_days, -- 修正
				        COALESCE(FLOOR(SUM(w.work_seconds) / 3600), 0) AS total_work_hours,
				        COALESCE(FLOOR(SUM(w.late_night_seconds) / 3600), 0) AS total_late_night_hours,
				        COALESCE(FLOOR(SUM(GREATEST(w.work_seconds - (8 * 3600), 0)) / 3600), 0) AS total_overtime_hours,
				        COALESCE(FLOOR(SUM(b.break_seconds) / 3600), 0) AS total_break_hours,
				        (SELECT COALESCE(SUM(CASE WHEN leave_id = 1 THEN DATEDIFF(end_date, start_date) + 1 ELSE 0 END), 0)
				         FROM leave_requests
				         WHERE user_id = ?
				           AND DATE_FORMAT(start_date, '%Y') = ?) AS paid_leave_days,
				        (SELECT COALESCE(SUM(CASE WHEN leave_id = 2 THEN DATEDIFF(end_date, start_date) + 1 ELSE 0 END), 0)
				         FROM leave_requests
				         WHERE user_id = ?
				           AND DATE_FORMAT(start_date, '%Y') = ?) AS absence_days
				    FROM filtered_calendar fc
				    LEFT JOIN work_hours w ON fc.work_date = w.work_date
				    LEFT JOIN breaks b ON fc.work_date = b.break_date
				)
				SELECT * FROM yearly_summary;
				""";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, year); // For calendar start date
			pstmt.setString(2, year); // For calendar end date
			pstmt.setInt(3, userId); // For raw_work_hours
			pstmt.setString(4, year); // For raw_work_hours
			pstmt.setInt(5, userId); // For breaks
			pstmt.setInt(6, userId); // For leave_summary (paid_leave_days)
			pstmt.setString(7, year); // For leave_summary (paid_leave_days)
			pstmt.setInt(8, userId); // For holiday_work_days
			pstmt.setInt(9, userId); // For holiday_work_days in EXISTS subquery
			pstmt.setInt(10, userId); // For leave_summary (paid_leave_days in yearly_summary)
			pstmt.setString(11, year); // For leave_summary (absence_days in yearly_summary)
			pstmt.setInt(12, userId); // For leave_summary (absence_days in yearly_summary)

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					Map<String, String> summary = new HashMap<>();
					summary.put("scheduled_days", rs.getString("scheduled_days"));
					summary.put("scheduled_work_hours", rs.getString("scheduled_work_hours") + "時間");
					summary.put("actual_work_days", rs.getString("actual_work_days"));
					summary.put("total_work_hours", rs.getString("total_work_hours") + "時間");
					summary.put("total_late_night_hours", rs.getString("total_late_night_hours") + "時間");
					summary.put("total_overtime_hours", rs.getString("total_overtime_hours") + "時間");
					summary.put("total_break_hours", rs.getString("total_break_hours") + "時間");
					summary.put("paid_leave_days", rs.getString("paid_leave_days"));
					summary.put("absence_days", rs.getString("absence_days"));
					return summary;
				} else {
					return null;
				}
			}
		}
	}
}
