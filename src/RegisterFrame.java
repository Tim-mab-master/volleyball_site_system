
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class RegisterFrame extends JFrame {
	private static final int FRAME_WIDTH = 800;
	private static final int FRAME_HEIGHT = 600;
	private JLabel teamName, information1, information2, information3, information4, information5, reserve, checktime;
	private JTextField textteamID;
	private JButton btnBack_to_home, btnCheckin, btnSearch;
	private JPanel pane0, pane1, pane2, pane3, pane4, pane5, pane6, pane7, pane8, pane9, pane10;
	private String team_id;
	private TimeSet timeSet;
	private boolean sucess, check = false;
	private String query;
	Statement stat;
	Connection conn;

	public RegisterFrame(Connection conn, String UserID) throws SQLException{
		this.setTitle("NCCU排球場協系統_報到系統");
		this.setSize(this.FRAME_WIDTH, this.FRAME_HEIGHT);
		this.team_id = UserID;
		this.conn = conn;
		stat = conn.createStatement();
		this.timeSet = new TimeSet();
		createTextField();
		createLabel();
		createButton();
		createLayout();
	}

	private void createTextField() throws SQLException{
		final int FIELD_WIDTH = 10;
		textteamID = new JTextField(FIELD_WIDTH);
		textteamID.setText("");
		
		///加到個個頁面    更新
		query = String.format("SELECT start_time FROM record WHERE team_id = '%s'",team_id);
		sucess = stat.execute(query);
		if (sucess) {
			ResultSet result = stat.getResultSet();
			if(getDeadline(result).size() != 0) {
				for(int i = 0; i < getDeadline(result).size(); i++) {
					if(timeSet.isTimeVio(getDeadline(result).get(i))) {
						query = String.format("UPDATE record SET state = 'Unregistered' WHERE team_id = '%s' AND start_time = '%s'",team_id, getTime(result).get(i));
						sucess = stat.execute(query);
					}
				}
				result.close();
			}
		}
		
		///
	}

	private void createLabel() throws SQLException{
		query = String.format("SELECT TeamName FROM TeamList_oneteam WHERE ID = '%s'", team_id);
		sucess = stat.execute(query);
		if (sucess) {
			ResultSet result = stat.getResultSet();
			teamName = new JLabel(getOneData(result), JLabel.LEFT);
			result.close();
		}
		reserve = new JLabel("---");
		information1 = new JLabel("| 預計擴充功能場地報到認證機制 |");
		information2 = new JLabel("1.掃描場地QRcode：");
		information3 = new JLabel("在山上球場旁邊貼QRcode，掃描QRcode進行報到");
		information4 = new JLabel("2.上傳場地使用照片：");
		information5 = new JLabel("系隊拍攝即時場地使用照片，並上傳報到。");
		checktime = new JLabel("");
		
		timeSet.stateCheck(checktime);
//		System.out.println(checktime.getText());
		
	}
	

	private void createButton() {
		btnBack_to_home = new JButton("回首頁");
		btnCheckin = new JButton("報到");
		btnSearch = new JButton("查詢");
		btnBack_to_home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				//

				setVisible(false);
				TimetableFrame frame;
				try {
					frame = new TimetableFrame(conn, team_id);
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

		btnCheckin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(check == true) {
					try {
						query = String.format("UPDATE record SET state = 'Register' WHERE team_id = '%s' AND start_time = '%s'", team_id, checktime.getText());
						sucess = stat.execute(query);
						
					}catch(SQLException e) {
						e.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "報到成功！", "Information", JOptionPane.INFORMATION_MESSAGE);
					
				}
				else {
					JOptionPane.showMessageDialog(null, "報到失敗！", "Information", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				check = false;
				if(checktime.getText().equals("")) {
					reserve.setText("無可報到時段");
					System.out.println(checktime.getText());
				}
				else  {
					
					try {
						query = String.format("SELECT name, start_time, end_time FROM record JOIN court ON record.court_id = court.id WHERE team_id = '%s' AND start_time = '%s'", team_id, checktime.getText());
						sucess = stat.execute(query);
						
						if (sucess) {
							ResultSet result = stat.getResultSet();
							
							if(getReserveData(result,reserve).equals("")) {
//								System.out.println("0");
								reserve.setText("無可報到時段");
								
							}
							else {
								check = true;
							}
							
							result.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
				}
			}
		});

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
		pane7 = new JPanel();
		pane8 = new JPanel();
		pane9 = new JPanel();
		pane10 = new JPanel();

		pane1.add(teamName);
		pane2.add(btnSearch);
		pane2.add(btnCheckin);
		pane2.add(btnBack_to_home);
		pane4.add(information1);
		pane5.add(information2);
		pane6.add(information3);
		pane7.add(information4);
		pane8.add(information5);
		pane9.add(reserve);
		pane10.add(checktime);

		flow_panel.add(pane0);
		flow_panel.add(pane1);
		flow_panel.add(pane9);
		flow_panel.add(pane2);
		flow_panel.add(pane3);
		flow_panel.add(pane4);
		flow_panel.add(pane5);
		flow_panel.add(pane6);
		flow_panel.add(pane7);
		flow_panel.add(pane8);
//		flow_panel.add(pane10);

		add(flow_panel);
	}
	public static String getReserveData(ResultSet result, JLabel j) throws SQLException{
//		System.out.print("0");
		String output = "";
		while(result.next()) {
		
		
			
			output+= "地點： ";
			output+=result.getString(1);
			output+="   ";
			output+="時間： ";
			output+=result.getString(2).substring(5,16) + " - ";
			output+=result.getString(3).substring(5,16);
			output += "\n";
			break;
		}
		j.setText(output);
		return output;
		
		
	}
	public static String getOneData(ResultSet result) throws SQLException{
		result.next();
		return result.getString(1);
	}
	
	///加到各個頁面
	public static ArrayList<String> getDeadline(ResultSet result) throws SQLException{
		ArrayList<String> list = new ArrayList<String>();
		while(result.next()) {
			list.add(result.getString(1).substring(5,14)+"20:00");
		}
		return list;
	}
	
	
	public static ArrayList<String> getTime(ResultSet result) throws SQLException{
		ArrayList<String> list = new ArrayList<String>();
		while(result.next()) {
			list.add(result.getString(1).substring(5,14)+"00:00");
		}
		return list;
	}
	///
}