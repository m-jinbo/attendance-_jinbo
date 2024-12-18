package model.entity;

import java.math.BigDecimal;
import java.sql.Date;

public class MonthlyAttendanceSummary {
	private int employeeId; // 社員ID
	private Date month; // 月 (例: 2024-06-01)
	private int totalWorkDays; // 総勤務日数
	private BigDecimal totalWorkHours; // 総勤務時間 (時間:分 → 小数時間)

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

	public BigDecimal getTotalWorkHours() {
		return totalWorkHours;
	}

	/**
	 * 時間（Time 型）を小数時間に変換し、BigDecimal に設定
	 */
	public void setTotalWorkHours(java.sql.Time totalWorkHoursTime) {
		if (totalWorkHoursTime != null) {
			// 時間と分を小数時間に変換 (例: 2:30 -> 2.5)
			int hours = totalWorkHoursTime.toLocalTime().getHour();
			int minutes = totalWorkHoursTime.toLocalTime().getMinute();
			this.totalWorkHours = BigDecimal.valueOf(hours)
					.add(BigDecimal.valueOf(minutes).divide(BigDecimal.valueOf(60), 2, BigDecimal.ROUND_HALF_UP));
		} else {
			this.totalWorkHours = BigDecimal.ZERO; // null 時は 0.0
		}
	}

	// 既存の totalWorkHours の BigDecimal セット
	public void setTotalWorkHours(BigDecimal totalWorkHours) {
		this.totalWorkHours = totalWorkHours;
	}
}
