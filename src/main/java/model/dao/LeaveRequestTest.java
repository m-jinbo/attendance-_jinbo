package model.dao;

public class LeaveRequestTest {
    public static void main(String[] args) {
        // DAOクラスのインスタンスを作成
        LeaveRequestDAO dao = new LeaveRequestDAO();

        // テストデータを設定
        int employeeId = 1; // 既存の従業員ID
        int leaveType = 1;  // 有給
        String startDate = "2024-12-01"; // 休暇開始日
        String endDate = "2024-12-05";   // 休暇終了日
        String reason = "家族旅行のため"; // 休暇理由

        // DAOのメソッドを呼び出して休暇申請を登録
        boolean result = false;
		try {
			result = dao.insertLeaveRequest(employeeId, leaveType, startDate, endDate, reason);
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

        // 結果を表示
        if (result) {
            System.out.println("休暇申請が正常に登録されました！");
        } else {
            System.out.println("休暇申請の登録に失敗しました。");
        }
    }
}
