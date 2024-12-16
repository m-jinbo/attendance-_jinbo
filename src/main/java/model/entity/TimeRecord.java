package model.entity;

public class TimeRecord {
	private int recordId; // レコードID
	private int employeeId; // 従業員ID（外部キー）
	private int recordType; // 打刻タイプ（1: 出勤, 2: 退勤など）
	private String recordTime; // 打刻時間

	// Getter and Setter for recordId
	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	// Getter and Setter for employeeId
	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	// Getter and Setter for recordType
	public int getRecordType() {
		return recordType;
	}

	public void setRecordType(int recordType) {
		this.recordType = recordType;
	}

	// Getter and Setter for recordTime
	public String getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}
}
