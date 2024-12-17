package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import model.entity.Employees;

public class UserFindDAO {

	private static final Logger logger = Logger.getLogger(UserFindDAO.class.getName());

	// ログインアカウントを探す
	public Employees findAccount(Employees employee) throws ClassNotFoundException, SQLException {

		Employees resultEmployee = null;

		// SQLクエリ: user_id と password で検索し、employee_id, name, role_id を取得
		String sql = "SELECT employee_id, user_id, name, role_id " +
				"FROM employees WHERE user_id = ? AND password = ?";

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			// プレースホルダに値をセット
			ps.setInt(1, employee.getUserId());
			ps.setString(2, employee.getPassword());

			// クエリ実行
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					resultEmployee = new Employees();
					resultEmployee.setEmployeeId(rs.getInt("employee_id")); // employee_id のセット
					resultEmployee.setUserId(rs.getInt("user_id")); // user_id のセット
					resultEmployee.setName(rs.getString("name")); // name のセット
					resultEmployee.setRoleId(rs.getInt("role_id")); // role_id のセット
				}
			}
		} catch (SQLException e) {
			logger.severe("SQLエラー: " + e.getMessage());
			throw e;
		}

		return resultEmployee; // 結果の Employees オブジェクトを返す
	}
}
