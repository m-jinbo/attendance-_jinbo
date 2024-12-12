package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	private static final String URL = "jdbc:mysql://localhost:3306/system_project?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Tokyo";
	private static final String USER = "m-jinbo";
	private static final String PASSWORD = "m-jinbo";

	public static Connection getConnection() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}
}
