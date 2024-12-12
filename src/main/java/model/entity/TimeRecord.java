package model.entity;

public class TimeRecord {
	private int recordId; // レコードID
	private int employeeId; // 従業員ID
	private int recordType; // 打刻タイプ（1: 出勤, 2: 休憩開始など）
	private String recordTime; // 打刻時間

	// デフォルトコンストラクタ
	public TimeRecord() {
	}

	// コンストラクタ
	public TimeRecord(int employeeId, int recordType, String recordTime) {
		this.employeeId = employeeId;
		this.recordType = recordType;
		this.recordTime = recordTime;
	}

	// ゲッターとセッター
	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getRecordType() {
		return recordType;
	}

	public void setRecordType(int recordType) {
		this.recordType = recordType;
	}

	public String getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}

	@Override
	public String toString() {
		return "TimeRecord [recordId=" + recordId + ", employeeId=" + employeeId +
				", recordType=" + recordType + ", recordTime=" + recordTime + "]";
	}
}
