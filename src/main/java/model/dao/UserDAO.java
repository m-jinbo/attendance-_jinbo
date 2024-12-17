package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.entity.EmployeeProfile;
import model.entity.Employees;

public class UserDAO {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/system_project";
	private static final String DB_USER = "m-jinbo";
	private static final String DB_PASSWORD = "m-jinbo"; // 修正済みパスワード

	public boolean registerEmployeeWithProfile(Employees employee, EmployeeProfile profile) {
		Connection conn = null;
		PreparedStatement employeeStmt = null;
		PreparedStatement profileStmt = null;
		ResultSet generatedKeys = null;

		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			conn.setAutoCommit(false);

			// Employeesテーブルへの登録
			String employeeSql = "INSERT INTO employees (name, user_id, password, role_id) VALUES (?, ?, ?, ?)";
			employeeStmt = conn.prepareStatement(employeeSql, PreparedStatement.RETURN_GENERATED_KEYS);
			employeeStmt.setString(1, employee.getName());
			employeeStmt.setInt(2, employee.getUserId()); // user_id に修正
			employeeStmt.setString(3, employee.getPassword());
			employeeStmt.setInt(4, employee.getRoleId());
			employeeStmt.executeUpdate();

			generatedKeys = employeeStmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				int employeeId = generatedKeys.getInt(1);
				profile.setEmployeeId(employeeId);
			} else {
				conn.rollback();
				return false;
			}

			// Employee_profilesテーブルへの登録
			String profileSql = "INSERT INTO employee_profiles (employee_id, location_id) VALUES (?, ?)";
			profileStmt = conn.prepareStatement(profileSql);
			profileStmt.setInt(1, profile.getEmployeeId());
			profileStmt.setInt(2, profile.getLocationId());
			profileStmt.executeUpdate();

			conn.commit();
			return true;
		} catch (SQLException e) {
			System.err.println("SQL Error: " + e.getMessage());
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException rollbackEx) {
					rollbackEx.printStackTrace();
				}
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (generatedKeys != null)
					generatedKeys.close();
				if (employeeStmt != null)
					employeeStmt.close();
				if (profileStmt != null)
					profileStmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException closeEx) {
				closeEx.printStackTrace();
			}
		}
	}
}
