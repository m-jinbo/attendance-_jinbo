package model.entity;

import java.sql.Date;

public class DailyAttendance {
	private Date workDate;
	private String workDateFormatted; 
	private String startTime; 
	private String endTime; 
	private String breakTime;
	private String workingTime;

	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}

	public String getWorkDateFormatted() {
		return workDateFormatted;
	}

	public void setWorkDateFormatted(String workDateFormatted) {
		this.workDateFormatted = workDateFormatted;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getBreakTime() {
		return breakTime;
	}

	public void setBreakTime(String breakTime) {
		this.breakTime = breakTime;
	}

	public String getWorkingTime() {
		return workingTime;
	}

	public void setWorkingTime(String workingTime) {
		this.workingTime = workingTime;
	}
}
