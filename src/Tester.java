import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFrame;

public class Tester {
	
	public static void main(String[] args) {

		
		// TODO Auto-generated method stub
//		String server = "jdbc:mysql://localhost:3306/";
//		String database = "site"; // change to your own database
//		String url = server + database + "?useSSL=false";
//		String username = "root"; // change to your own user name
//		String password = "Tim109302061"; // change to your own password	
		
		
		String id = "0";
		
		try {
//			Connection conn = DriverManager.getConnection(url, username, password);
			
			 // 註冊 JDBC 驅動程式
//            Class.forName("org.sqlite.JDBC");
            
            // 建立資料庫連線
            Connection conn = DriverManager.getConnection("jdbc:sqlite:site.db");
			
			System.out.println("DB Connectted");
			
			TimetableFrame frame = new TimetableFrame(conn, id);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		     
	}
}
