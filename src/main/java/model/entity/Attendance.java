package model.entity;

import java.sql.Date;
import java.sql.Time;

public class Attendance {
	private int id; // 主キー
	private int userId;; // 社員ID
	private Date date; // 勤務日
	private Time attendanceTime; // 出勤時間
	private Time breakTime; // 退勤時間
	private Time totalHours; // 合計勤務時間

	// コンストラクタ
	public Attendance() {
	}

	// Getter と Setter
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getAttendanceTime() {
		return attendanceTime;
	}

	public void setAttendanceTime(Time attendanceTime) {
		this.attendanceTime = attendanceTime;
	}

	public Time getBreakTime() {
		return breakTime;
	}

	public void setBreakTime(Time breakTime) {
		this.breakTime = breakTime;
	}

	public Time getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(Time totalHours) {
		this.totalHours = totalHours;
	}
}
