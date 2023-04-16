import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Reserve extends JFrame{
	private JLabel depName, violation, numVio, time, D, S, E, siteName;
	private JTextArea output;
	private JComboBox start, end, date, operate, site;
	private JButton home, commit;
	private JScrollPane scrollPane;
	private TimeSet timeSet;
	private String query;
	private boolean sucess;
	String team_id;
	Connection conn;
	Statement stat;
	
	public Reserve (Connection conn, String team_id) throws SQLException{
		this.team_id = team_id;
		this.conn = conn;	
		stat = conn.createStatement();
		timeSet = new TimeSet();
		createLabel();
		createTextArea();
		createCombo();
		createButton();
		createPanel();
		setTitle("Reserve");
		setSize(800, 600);
	}
	
	private void createTextArea() throws SQLException{
		output = new JTextArea(1,12);
		output.setEditable(false);
		
		
		
		query = "SELECT name, state, start_time, end_time FROM court JOIN record ON court.id = record.court_id WHERE team_id = " + team_id;
		sucess = stat.execute(query);
		if (sucess) {
			ResultSet result = stat.getResultSet();
			output.setText(showResultSet(result));
			result.close();
		}	 
	}
	
	private void createCombo() {
		start = new JComboBox();
		end = new JComboBox();
		date = new JComboBox();
		operate = new JComboBox();
		site = new JComboBox();
		operate.addItem("加入");
		operate.addItem("刪除");
		operate.addItem("重製");
		operate.setBounds(80, 50, 100, 40);
		operate.setFont(new java.awt.Font("標楷體", 1, 20));
		for(String i: timeSet.getAllWeek()) {
			date.addItem(i);
		}
		for(String i: timeSet.getAllTime("s1")) {
			start.addItem(i);
		}
		for(String i: timeSet.getAllTime("e1")) {
			end.addItem(i);
		}
		site.addItem("四期A");
		site.addItem("四期B");
		site.addItem("四期C");
		site.addItem("五期A");
		site.addItem("五期B");
		site.setFont(new java.awt.Font("標楷體", 1, 20));
		start.setFont(new java.awt.Font("標楷體", 1, 20));
		end.setFont(new java.awt.Font("標楷體", 1, 20));
		date.setFont(new java.awt.Font("標楷體", 1, 20));
	}
	
	private void createButton() {
		commit = new JButton("執行");
		commit.setBounds(230, 50, 100, 40);
		home = new JButton("回首頁");
		home.addActionListener(new ActionListener() {
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
		commit.setFont(new java.awt.Font("標楷體", 1, 20));
		home.setFont(new java.awt.Font("標楷體", 1, 16));
		commit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				String d = (String)date.getSelectedItem();
				String courtID = getCourtID((String) site.getSelectedItem());
				String stime = timeSet.getDatetime(d.substring(0, 5),(String) start.getSelectedItem());
				String etime = timeSet.getDatetime(d.substring(0, 5),(String) end.getSelectedItem());
				if(TimeSet.compare((String) start.getSelectedItem(), (String) end.getSelectedItem()) | TimeSet.isOverTime((String) start.getSelectedItem(), (String) end.getSelectedItem()) ) {
					JOptionPane.showMessageDialog(null, "錯誤時段", "Error",JOptionPane.ERROR_MESSAGE);
					return;
				}
//				System.out.println("現在日期：");
//				System.out.println(TimeSet.compareDate(d.substring(0, 5),(String) TimeSet.get_now_time()));
//				if (TimeSet.compareDate(d.substring(0, 5),(String) TimeSet.get_now_time())) {
//					
//					if (!TimeSet.isOverTime((String) TimeSet.get_now_moment(), (String) start.getSelectedItem())) {
//						
//						JOptionPane.showMessageDialog(null, "錯誤時段", "Error",JOptionPane.ERROR_MESSAGE);
//						return;
//					}
//				}
				if (operate.getSelectedItem().equals("加入")) {
					
//					System.out.println(d.substring(0, 5) + "5");
					query = String.format("SELECT start_time, end_time FROM record WHERE court_id = '%s'", courtID);
					try {
						sucess = stat.execute(query);
						if (sucess) {
							ResultSet result = stat.getResultSet();
//							System.out.println(isRepeated((String) start.getSelectedItem(), (String) end.getSelectedItem(), d.substring(0, 5), result));
							if(!isRepeated((String) start.getSelectedItem(), (String) end.getSelectedItem(), d.substring(0, 5), result)){
								System.out.println(((String) site.getSelectedItem()));
								query = String.format("INSERT INTO record (court_id, team_id, state, start_time, end_time) VALUES ('%s', '%s', 'Reserve', '%s', '%s')", courtID, team_id, stime, etime);
								sucess = stat.execute(query);
							}
							else {
								JOptionPane.showMessageDialog(null, "此時段已被預約", "Info",JOptionPane.INFORMATION_MESSAGE);
							}
							System.out.println("over\n");
						}
					}catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
				else if (operate.getSelectedItem().equals("刪除")) {
					try {
						query = String.format("DELETE FROM record WHERE start_time = '%s'AND end_time = '%s' AND team_id = '%s' AND court_id = '%s'", stime, etime, team_id, courtID);
						sucess = stat.execute(query);
						
					}catch(Exception e){
						JOptionPane.showMessageDialog(null, "無此時段的預約", "Info",JOptionPane.INFORMATION_MESSAGE);
						//e.printStackTrace();
					}
					
				}
				else if(operate.getSelectedItem().equals("重置")) {
					try {
						query = String.format("DELETE FROM record WHERE team_id = '%s' AND court_id = '%s'", team_id, courtID);
						sucess = stat.execute(query);
						
					}catch(Exception e){
//						JOptionPane.showMessageDialog(null, "無此時段的預約", "Info",JOptionPane.INFORMATION_MESSAGE);
						e.printStackTrace();
					}
				}
				try {
					
					query = "SELECT name, state, start_time, end_time FROM court JOIN record ON court.id = record.court_id WHERE team_id = "+team_id;
					sucess = stat.execute(query);
					System.out.println("e: "+ sucess);
					if (sucess) {
						ResultSet result = stat.getResultSet();
						output.setText(showResultSet(result));
						result.close();
					}
				}catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void createLabel() throws SQLException{
		violation = new JLabel("違規次數: ", JLabel.LEFT);
		String newVionum = "";
		String oriVionum = "";
		query = String.format("SELECT COUNT(*) FROM record WHERE state = 'Unregistered' AND start_time LIKE '%s'", timeSet.get_now_time());
		sucess = stat.execute(query);
		if (sucess) {
			ResultSet result = stat.getResultSet();
			newVionum = getOneData(result);
			result.close();
		}
		query = String.format("SELECT Violation FROM TeamList_oneteam WHERE ID = '%s'", team_id);
		sucess = stat.execute(query);
		if (sucess) {
			ResultSet result = stat.getResultSet();
			//
			oriVionum = getOneData(result);
			numVio = new JLabel(oriVionum, JLabel.RIGHT);
			//
			result.close();
		}
		int newnum = Integer.parseInt(newVionum);
		int orinum = Integer.parseInt(oriVionum);
		newnum += orinum;
		query = String.format("UPDATE TeamList_oneteam SET Violation = '%s'", newnum);
		sucess = stat.execute(query);
		
		query = String.format("SELECT TeamName FROM TeamList_oneteam WHERE ID = '%s'", team_id);
		sucess = stat.execute(query);
		if (sucess) {
			ResultSet result = stat.getResultSet();
			depName = new JLabel(getOneData(result), JLabel.LEFT);
			result.close();
		}
		
		time = new JLabel("",JLabel.CENTER);
		
		D = new JLabel("日期:", JLabel.CENTER);
		S = new JLabel("從: ", JLabel.CENTER);
		E = new JLabel("至: ", JLabel.CENTER);
		siteName = new JLabel("場地:", JLabel.CENTER);
		violation.setFont(new java.awt.Font("標楷體", 1, 26));
		numVio.setFont(new java.awt.Font("標楷體", 1, 26));
		depName.setFont(new java.awt.Font("標楷體", 1, 40));
		time.setFont(new java.awt.Font("標楷體", 1, 30));
		D.setFont(new java.awt.Font("標楷體", 1, 26));
		S.setFont(new java.awt.Font("標楷體", 1, 26));
		E.setFont(new java.awt.Font("標楷體", 1, 26));
		siteName.setFont(new java.awt.Font("標楷體", 1, 26));
		timeSet.setTimer(time);
		
	}
	
	private void createPanel() {
		scrollPane = new JScrollPane(output);;
		JPanel panel = new JPanel(new BorderLayout());
		JPanel upPanel = new JPanel(new GridLayout(1, 2));
		JPanel upRPanel = new JPanel(new BorderLayout());
		JPanel upLPanel = new JPanel();
		JPanel downPanel = new JPanel(new GridLayout(1, 2));
		JPanel rPanel = new JPanel(new GridLayout(4,1));
		JPanel rTopPanel = new JPanel();
		JPanel rUpPanel = new JPanel();
		JPanel rMidPanel = new JPanel();
		JPanel rDownPanel = new JPanel(null);
		rDownPanel.add(operate);
		rDownPanel.add(commit);
		rMidPanel.add(S);
		rMidPanel.add(start);
		rMidPanel.add(E);
		rMidPanel.add(end);
		rUpPanel.add(siteName);
		rUpPanel.add(site);
		rUpPanel.add(D);
		rUpPanel.add(date);
		rTopPanel.add(violation);
		rTopPanel.add(numVio);
		rPanel.add(rTopPanel);
		rPanel.add(rUpPanel);
		rPanel.add(rMidPanel);
		rPanel.add(rDownPanel);
		downPanel.add(scrollPane);
		downPanel.add(rPanel);
		upLPanel.add(depName);
		upRPanel.add(time, BorderLayout.CENTER);
		upRPanel.add(home, BorderLayout.EAST);
		upPanel.add(upLPanel);
		upPanel.add(upRPanel);
		panel.add(upPanel, BorderLayout.NORTH);
		panel.add(downPanel, BorderLayout.CENTER);
		add(panel);
	}
	public String getCourtID(String name) {
		if(name.equals("四期A")) {
			return "1";
		}
		else if (name.equals("四期B")) {
			return "2";
		}
		else if (name.equals("四期C")) {
			return "3";
		}
		else if (name.equals("五期A")) {
			return "4";
		}
		else if (name.equals("五期B")) {
			return "5";
		}
		else {
			return "";
		}
	}
	
	public static String getOneData(ResultSet result) throws SQLException{
		result.next();
		return result.getString(1);
	}
	
	public static String showResultSet(ResultSet result) throws SQLException {
		
		ResultSetMetaData metaData = result.getMetaData();
//		int columnCount = metaData.getColumnCount();
		String output = "";
		output += String.format("%-20s", metaData.getColumnLabel(1));
		output += String.format("%-30s", metaData.getColumnLabel(2));
		output += String.format("%-20s", metaData.getColumnLabel(3));
		output += String.format("%-20s", metaData.getColumnLabel(4));

		output += "\n";
		
		while (result.next()) {
			output += String.format("%-15s", result.getString(1));
			output += String.format("%-20s", result.getString(2));
			output += String.format("%-20s", result.getString(3).substring(5, 19));
			output += String.format("%-20s", result.getString(4).substring(5, 19));//11
			
			output += "\n";
		}
		return output;
	}
	
	public static boolean isRepeated(String s, String e, String d, ResultSet result) throws SQLException {
		
		ResultSetMetaData metaData = result.getMetaData();
		int columnCount = metaData.getColumnCount();
		
		boolean isRepeated = false;
		System.out.println("next "+result.next());
		System.out.println("in"+s+" "+e);
		
		
		
		if(!result.isFirst()){
			
//			System.out.println("1");
			return isRepeated;
		}
//		boolean p = result.previous();
//		boolean p1 = result.previous();
		while (result.next()) {
				
			String date = String.format("%s", result.getString(1).substring(5, 10));
			System.out.println(d + date +":"+date.equals(d));
			if(date.equals(d)) {
				String start = String.format("%s", result.getString(1).substring(11, 19));
				String end = String.format("%s", result.getString(2).substring(11, 19));
				System.out.println("db"+start+" "+end);
					
				TimeSet t = new TimeSet();
				System.out.println("cp"+t.compare(start, e)+" "+t.compare(s, end));//compare >= :true
				if(!(t.compare(start, e) || t.compare(s, end))) {
						
					isRepeated = true;
					System.out.println("result: "+isRepeated);
					return isRepeated;
				}
			}	
		}
		
		
		System.out.println("result: "+isRepeated);
		return isRepeated;
	}
	
	
		
}
