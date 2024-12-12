package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationDAO {

	/**
	 * 拠点IDに対応する拠点名を取得するメソッド
	 *
	 * @param locationId 拠点ID
	 * @return 拠点名 (該当なしの場合は "不明な拠点" を返す)
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public String getLocationNameById(String locationId) throws SQLException, ClassNotFoundException {
		String locationName = "不明な拠点";
		String sql = "SELECT location_name FROM locations WHERE location_id = ?";

		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, locationId);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					locationName = rs.getString("location_name");
				}
			}
		}
		return locationName;
	}
}
