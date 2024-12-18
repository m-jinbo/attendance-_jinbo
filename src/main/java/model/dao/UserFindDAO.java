package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import model.entity.Employees;

public class UserFindDAO {

	private static final Logger logger = Logger.getLogger(UserFindDAO.class.getName());

	/**
	 * user_id と password に基づいて認証するメソッド
	 *
	 * @param employee Employees オブジェクト（userId, password を設定済み）
	 * @return 認証成功時: Employees オブジェクト / 失敗時: null
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Employees findAccount(Employees employee) throws SQLException, ClassNotFoundException {
		Employees authenticatedEmployee = null;

		String sql = "SELECT employee_id, user_id, name, role_id FROM employees WHERE user_id = ? AND password = ?";

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, employee.getUserId());
			ps.setString(2, employee.getPassword());

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					authenticatedEmployee = new Employees();
					authenticatedEmployee.setEmployeeId(rs.getInt("employee_id"));
					authenticatedEmployee.setUserId(rs.getInt("user_id"));
					authenticatedEmployee.setName(rs.getString("name"));
					authenticatedEmployee.setRoleId(rs.getInt("role_id"));
				}
			}
		} catch (SQLException e) {
			logger.severe("findAccount SQLエラー: " + e.getMessage());
			throw e;
		}

		return authenticatedEmployee;
	}

	/**
	 * user_id に基づいて従業員情報を取得するメソッド
	 *
	 * @param userId ユーザーID
	 * @return 従業員情報 Employees オブジェクト / 失敗時: null
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Employees findEmployeeByUserId(int userId) throws SQLException, ClassNotFoundException {
		Employees employee = null;

		String sql = "SELECT employee_id, user_id, name, role_id FROM employees WHERE user_id = ?";

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, userId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					employee = new Employees();
					employee.setEmployeeId(rs.getInt("employee_id"));
					employee.setUserId(rs.getInt("user_id"));
					employee.setName(rs.getString("name"));
					employee.setRoleId(rs.getInt("role_id"));
				}
			}
		} catch (SQLException e) {
			logger.severe("findEmployeeByUserId SQLエラー: " + e.getMessage());
			throw e;
		}

		return employee;
	}
}
