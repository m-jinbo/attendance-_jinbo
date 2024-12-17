package model.entity;

public class Employees {
	private int employeeId; // 従業員の識別子
	private int userId; // ログインID
	private String name; // 名前 ← 追加
	private String password; // ログインパスワード
	private int roleId; // 役割ID（1=管理者, 2=一般）

	// Getter and Setter for employeeId
	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	// Getter and Setter for userId
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	// Getter and Setter for name
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// Getter and Setter for password
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// Getter and Setter for roleId
	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
}
