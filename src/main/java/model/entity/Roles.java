package model.entity;

public class Roles {
	private int roleId; // 権限ID
	private String roleName; // 権限名

	// Getter and Setter for roleId
	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	// Getter and Setter for roleName
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
