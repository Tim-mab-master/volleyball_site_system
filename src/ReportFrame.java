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
	private String[] optionsToChoose = { "101	����t��", "102	�Ш|�t��", "103	���v�t��", "104	���Ǩt��", "202	�F�v�t��", "203	�~��t��",
			"204	���|�t��", "205	�]�F�t��", "206	����t��", "207	�a�F�t��", "208	�g�٨t��", "209	���ڨt��", "301	��T�t��", "302	���Ĩt��",
			"303	�|�p�t��", "304	�έp�t��", "305	���ިt��", "306	��ިt��", "307	�]�ިt��", "308	���ިt��", "405	�ǰ|�t��", "501	�^��t��",
			"502	���y�t��", "504	���y�t��", "506	���t��", "507	����t��", "508	�g�y�t��", "601	�k�ߨt��", "701	���ƨt��", "702	�߲z�t��",
			"703	���t��", "141	�Ѯv�M��", "142	�U�бM��", "142	�U�бM��", "142	�U�бM��", "999 ���ո��" };
	private String teamID, nowTeamID;
	private String Violation;
	private JPanel pane0, pane1, pane2, pane3, pane4, pane5, pane6;
	private boolean Success = false;
	Connection conn;
	Statement stat;


	public ReportFrame(Connection conn, String ID) {
		this.setTitle("NCCU�Ʋy����t��_���|�t��");
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
		btnBack_to_home = new JButton("�^����");
		btnViolation = new JButton("���|");
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
				System.out.println("�^����");
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
					JOptionPane.showMessageDialog(null, "���|���\�A���±z����U�I", "Information", JOptionPane.INFORMATION_MESSAGE);
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
				// �ݭn����v�I�I�I�I�I�I
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
		vioTeam = new JLabel("�п�ܭn���|���t��");
		information1 = new JLabel("| �w�p�X�R�\�� |");
		information2 = new JLabel("1.�W�ǻ{�ҷӤ��\��A�H�T�O���|�\�ण�|�Q�ݥΡC");
		information3 = new JLabel("2.�̷����|�t�γQ�ϥΪ��ɶ��AComboBox�ȦC�X�Ӯɬq���w�����t���A����ܸӨt�����b�ϥΪ����a�C");

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
