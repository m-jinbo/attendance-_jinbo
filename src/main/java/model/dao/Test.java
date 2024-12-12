package model.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class Test {

	public static void main(String[] args) throws ClassNotFoundException {
		// TODO 自動生成されたメソッド・スタブ
		try {
			Connection connection = ConnectionManager.getConnection();
			System.out.println("成功");
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			System.out.println("失敗");
		}
	}

}
