import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

//import com.mysql.jdbc.Statement;

public class ReportFrame extends JFrame {
	private static final int FRAME_WIDTH = 800;
	private static final int FRAME_HEIGHT = 600;
	private JComboBox<String> teamName;
	private JLabel vioTeam, information1, information2, information3;
	private JButton btnBack_to_home, btnViolation;
	private String[] optionsToChoose = { "101	中文系排", "102	教育系排", "103	歷史系排", "104	哲學系排", "202	政治系排", "203	外交系排",
			"204	社會系排", "205	財政系排", "206	公行系排", "207	地政系排", "208	經濟系排", "209	民族系排", "301	國貿系排", "302	金融系排",
			"303	會計系排", "304	統計系排", "305	企管系排", "306	資管系排", "307	財管系排", "308	風管系排", "405	傳院系排", "501	英文系排",
			"502	阿語系排", "504	斯語系排", "506	日文系排", "507	韓文系排", "508	土語系排", "601	法律系排", "701	應數系排", "702	心理系排",
			"703	資科系排", "141	老師專用", "142	助教專用", "142	助教專用", "142	助教專用", "999 測試資料" };
	private String teamID, nowTeamID;
	private String Violation;
	private JPanel pane0, pane1, pane2, pane3, pane4, pane5, pane6;
	private boolean Success = false;
	Connection conn;
	Statement stat;


	public ReportFrame(Connection conn, String ID) {
		this.setTitle("NCCU排球場協系統_檢舉系統");
		this.conn = conn;
		this.nowTeamID = ID;
		createLabel();
		createButton();
		try {
			createComboBox();
		} catch (ReportFrame.ReportSuccess e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createLayout();
		this.setSize(this.FRAME_WIDTH, this.FRAME_HEIGHT);
	}

	private void createComboBox() throws ReportSuccess {
		teamName = new JComboBox<>(optionsToChoose);
	}

	private void createButton() {
		btnBack_to_home = new JButton("回首頁");
		btnViolation = new JButton("檢舉");
		btnBack_to_home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setVisible(false);
				TimetableFrame frame;
				try {
					frame = new TimetableFrame(conn, nowTeamID);
					frame.setVisible(true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("回首頁");
				//
				//
				//
				//
			}
		});

		btnViolation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					setTeamViolation();
					if (Success = true)
						throw new ReportSuccess("");
				} catch (ReportSuccess e) {
					JOptionPane.showMessageDialog(null, "檢舉成功，謝謝您的協助！", "Information", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

	}

	public String getTeamID() {
		teamID = (String) teamName.getSelectedItem();
		teamID = teamID.substring(0, 3);
		return teamID;
	}

	public void setTeamViolation() {
		

		try  {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:site.db");
			Statement stat = (Statement) conn.createStatement();
			String query, query1;
			boolean success, success1;
			query = "SELECT Violation FROM `TeamList_oneteam` WHERE ID=" + getTeamID();
			success = stat.execute(query);
			if (success) {
				ResultSet result = stat.getResultSet();
				while (result.next()) {
					Violation = result.getString(1);
				}
				result.close();
			} else {
				System.out.println("query failed");
			}
			int violation = Integer.parseInt(Violation);
			if (violation < 3) {
				violation += 1;
				String vio = Integer.toString(violation);
				query1 = "UPDATE `TeamList_oneteam` SET `Violation`='" + vio + "'WHERE `ID`=" + getTeamID();
				success1 = stat.execute(query1);
				Success = true;
			} else if (violation == 3) {
				violation = 0;
				String vio = Integer.toString(violation);
				query1 = "UPDATE `TeamList_oneteam` SET `Violation`='" + vio + "'WHERE `ID`=" + getTeamID();
				success1 = stat.execute(query1);
				Success = true;
				//
				//
				//
				//
				// 看要怎麼停權！！！！！！
				//
				//
				//
				//
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void createLabel() {
		vioTeam = new JLabel("請選擇要檢舉的系隊");
		information1 = new JLabel("| 預計擴充功能 |");
		information2 = new JLabel("1.上傳認證照片功能，以確保檢舉功能不會被濫用。");
		information3 = new JLabel("2.依照檢舉系統被使用的時間，ComboBox僅列出該時段有預約的系隊，並顯示該系隊正在使用的場地。");

	}

	private void createLayout() {
		JPanel flow_panel = new JPanel(new GridLayout(20, 1));
		pane0 = new JPanel();
		pane1 = new JPanel();
		pane2 = new JPanel();
		pane3 = new JPanel();
		pane4 = new JPanel();
		pane5 = new JPanel();
		pane6 = new JPanel();

		pane1.add(vioTeam);
		pane1.add(teamName);
		pane2.add(btnViolation);
		pane2.add(btnBack_to_home);
		pane4.add(information1);
		pane5.add(information2);
		pane6.add(information3);

		flow_panel.add(pane0);
		flow_panel.add(pane1);
		flow_panel.add(pane2);
		flow_panel.add(pane3);
		flow_panel.add(pane4);
		flow_panel.add(pane5);
		flow_panel.add(pane6);

		add(flow_panel);
	}

	class ReportSuccess extends Exception {
		public ReportSuccess(String Error) {
			super(Error);
		}
	}
}
