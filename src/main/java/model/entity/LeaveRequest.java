package model.entity;

import java.time.LocalDate;

public class LeaveRequest {
	private int requestId; // 申請ID
	private int employeeId; // 従業員ID（外部キー）
	private int leaveId; // 休暇種類（1: 有給, 2: 欠勤, 3: 特別休暇など） <- 修正
	private LocalDate startDate; // 休暇開始日
	private LocalDate endDate; // 休暇終了日
	private String reason; // 休暇理由

	// Getter and Setter for requestId
	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	// Getter and Setter for employeeId
	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	// Getter and Setter for leaveId <- 修正
	public int getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
	}

	// Getter and Setter for startDate
	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	// Getter and Setter for endDate
	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	// Getter and Setter for reason
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
