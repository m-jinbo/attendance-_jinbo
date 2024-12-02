package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * データベース接続を管理するクラス
 */
public class ConnectionManager {
	private static final String URL = "jdbc:mysql://localhost:3306/system_project?useSSL=false&serverTimezone=Asia/Tokyo";
	private static final String USER = "m-jinbo";
	private static final String PASSWORD = "m-jinbo";

	/**
	 * データベース接続を取得
	 */
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		// MySQL ドライバのロード
		Class.forName("com.mysql.cj.jdbc.Driver"); // ドライバをロード
		// データベース接続を返す
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}
}
