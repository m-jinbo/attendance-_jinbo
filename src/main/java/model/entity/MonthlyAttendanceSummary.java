package model.entity;

import java.sql.Date;
import java.sql.Time;

public class MonthlyAttendanceSummary {
	private int employeeId; // 社員ID
	private Date month; // 月 (例: 2024-06-01)
	private int totalWorkDays; // 総勤務日数
	private Time totalWorkHours; // 総勤務時間

	// コンストラクタ
	public MonthlyAttendanceSummary() {
	}

	// Getter と Setter
	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public Date getMonth() {
		return month;
	}

	public void setMonth(Date month) {
		this.month = month;
	}

	public int getTotalWorkDays() {
		return totalWorkDays;
	}

	public void setTotalWorkDays(int totalWorkDays) {
		this.totalWorkDays = totalWorkDays;
	}

	public Time getTotalWorkHours() {
		return totalWorkHours;
	}

	public void setTotalWorkHours(Time totalWorkHours) {
		this.totalWorkHours = totalWorkHours;
	}
}
