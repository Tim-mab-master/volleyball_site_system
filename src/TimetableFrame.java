import java.awt.GridLayout;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Font;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TimetableFrame extends JFrame {

	private JPanel court_panel, time_panel, button_panel, north_panel, panel, schedule_panel, mon_panel, tue_panel,
			wed_panel, thur_panel, fri_panel, sat_panel, sun_panel;
	private JLabel court, time_label, current_time;
	private JTextArea outputArea;
	private JScrollPane scrollPane;
	private JComboBox courtCombo;
	private JButton search, reserve, register, report, rule;
	private GetWeekDate dates;
	private static ScheduleHandler scheduleHandler;
	private String id;
	Connection conn;
	Statement stat;
	TimeSet timeSet;

	public TimetableFrame(Connection conn, String id) throws SQLException {
		this.conn = conn;
		this.id = id;
		createTextArea();
		court = new JLabel("     四期A 場地使用狀況", JLabel.CENTER);
		court.setFont(new Font("新細明體", Font.BOLD, 36));

		creamteNowTimeLabel();
		createCombo();
		createButton();

		createLayout();

		setTitle("NCCU場協系統-首頁");
		setSize(800, 600);

//		dates = new GetWeekDate();
//		System.out.println("start");
//		for(String s: dates.getWeekDateList()) {
//			System.out.println(s);
//		}

	}

	private void creamteNowTimeLabel() {
		current_time = new JLabel("", JLabel.RIGHT);
		current_time.setForeground(Color.BLUE);
//		current_time.setBounds(30, 0, 900, 130);
		current_time.setFont(new Font("新細明體", Font.BOLD, 16));
		add(current_time);
		setTimer(current_time);
	}

	// 设置Timer 1000ms实现一次动作 实际是一个线程

	private void setTimer(JLabel time) {
		final JLabel varTime = time;
		Timer timeAction = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long timemillis = System.currentTimeMillis();
				// 转换日期显示格式
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				varTime.setText("      現在時間： " + df.format(new Date(timemillis)));
			}
		});

		timeAction.start();
	}

	private void createTextArea() throws SQLException {
		outputArea = new JTextArea();
		outputArea.setText(String.format("行事曆會加在這裡"));
		scrollPane = new JScrollPane(outputArea);
		scrollPane.setBounds(0, 0, 800, 480);
		outputArea.setEditable(false);

		// Here is your code //

	}

	private void createCombo() {
		courtCombo = new JComboBox();

		courtCombo.addItem("四期A");
		courtCombo.addItem("四期B");
		courtCombo.addItem("四期C");
		courtCombo.addItem("五期A");
		courtCombo.addItem("五期B");
		courtCombo.setFont(new Font("新細明體", Font.PLAIN, 32));

	}

	private void createButton() throws SQLException {

		// 場地查詢button
		search = new JButton("查詢");
//		search.setSize(100, 120);
		search.setFont(new Font("新細明體", Font.PLAIN, 32));
		search.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {

				String courtString = (String) courtCombo.getSelectedItem();
				court.setText("     " + courtString + " 場地使用狀況");
//				current_time.setText("  現在時間：" + dtf.format(LocalDateTime.now()));

				// Here is your code //
				getSchedule(courtString);
			}
		});

		// 預約button
		reserve = new JButton("預約");
		reserve.setSize(200, 120);
		reserve.setFont(new Font("新細明體", Font.PLAIN, 32));
		reserve.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				setVisible(false);
				if (id == "0") {
					try {
						Login frame = new Login(conn, "reservation");
						frame.setVisible(true);
					} catch (Login.PasswordError e) {
						e.printStackTrace();
					} catch (Login.UserError e) {
						e.printStackTrace();
					}
				} else {
					// 直接切預約介面 id 也要跟著進去
					try {
//						(com.mysql.jdbc.Connection)
						Reserve frame = new Reserve( conn, id);
						frame.setVisible(true);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
		});

		// 報到button
		register = new JButton("報到");
//		register.setSize(200, 120);
		register.setFont(new Font("新細明體", Font.PLAIN, 32));
		register.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				setVisible(false);
				if (id == "0") {
					try {
						Login frame = new Login(conn, "register");
						frame.setVisible(true);
					} catch (Login.PasswordError e) {
						e.printStackTrace();
					} catch (Login.UserError e) {
						e.printStackTrace();
					}
				} else {
					// 直接切報到介面 id 也要跟著進去
					RegisterFrame frame;
					try {
						frame = new RegisterFrame(conn, id);
						frame.setVisible(true);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		});

		// 檢舉button
		report = new JButton("檢舉");
//		rule.setSize(200, 120);
		report.setFont(new Font("新細明體", Font.PLAIN, 32));
		report.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				setVisible(false);
				if (id == "0") {
					try {
						Login frame = new Login(conn, "report");
						frame.setVisible(true);
					} catch (Login.PasswordError e) {
						e.printStackTrace();
					} catch (Login.UserError e) {
						e.printStackTrace();
					}
				} else {
					// 直接切檢舉介面 id 也要跟著進去
//					(com.mysql.jdbc.Connection)
					ReportFrame frame = new ReportFrame( conn, id);
					frame.setVisible(true);
				}
			}
		});

		// 規則button
		rule = new JButton("規則");
//		rule.setSize(200, 120);
		rule.setFont(new Font("新細明體", Font.PLAIN, 32));
		rule.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				setVisible(false);
				RuleFrame frame = new RuleFrame(conn, id);
				frame.setVisible(true);
			}
		});
	}

	public void createSchedulePanel() {
		schedule_panel = new JPanel(new GridLayout(1, 7));
		mon_panel = new JPanel(new GridLayout(15, 1));
		tue_panel = new JPanel(new GridLayout(15, 1));
		wed_panel = new JPanel(new GridLayout(15, 1));
		thur_panel = new JPanel(new GridLayout(15, 1));
		fri_panel = new JPanel(new GridLayout(15, 1));
		sat_panel = new JPanel(new GridLayout(15, 1));
		sun_panel = new JPanel(new GridLayout(15, 1));

		dates = new GetWeekDate();
		mon_panel.add(new JLabel(String.format("  %s (一)", dates.getWeekDateList().get(0))));
		tue_panel.add(new JLabel(String.format("  %s (二)", dates.getWeekDateList().get(1))));
		wed_panel.add(new JLabel(String.format("  %s (三)", dates.getWeekDateList().get(2))));
		thur_panel.add(new JLabel(String.format("  %s (四)", dates.getWeekDateList().get(3))));
		fri_panel.add(new JLabel(String.format("  %s (五)", dates.getWeekDateList().get(4))));
		sat_panel.add(new JLabel(String.format("  %s (六)", dates.getWeekDateList().get(5))));
		sun_panel.add(new JLabel(String.format("  %s (日)", dates.getWeekDateList().get(6))));

		getFirstSchedule();

		schedule_panel.add(mon_panel);
		schedule_panel.add(tue_panel);
		schedule_panel.add(wed_panel);
		schedule_panel.add(thur_panel);
		schedule_panel.add(fri_panel);
		schedule_panel.add(sat_panel);
		schedule_panel.add(sun_panel);
	}

	private void createLayout() {
		panel = new JPanel(new BorderLayout());
		court_panel = new JPanel(new GridLayout(1, 2));

		time_panel = new JPanel(new GridLayout(15, 1));
		time_label = new JLabel("    時段 \\ 日期  ");
		time_panel.add(time_label);
		for (int i = 8; i < 22; i++) {
			JLabel t = new JLabel(String.format("    %d:00 ~ %d:00    ", i, i + 1));
			time_panel.add(t);
		}
		button_panel = new JPanel(new FlowLayout());

		north_panel = new JPanel();
		north_panel.add(court);
		north_panel.add(current_time);

		court_panel.add(courtCombo);
		court_panel.add(search);

		button_panel.add(court_panel);
		button_panel.add(reserve);
		button_panel.add(register);
		button_panel.add(report);
		button_panel.add(rule);

		createSchedulePanel();
		panel.add(time_panel, BorderLayout.WEST);
		panel.add(north_panel, BorderLayout.NORTH);
		panel.add(schedule_panel, BorderLayout.CENTER);
		panel.add(button_panel, BorderLayout.SOUTH);

		add(panel);
	}

	public String getCourtID(String name) {
		if (name.equals("四期A")) {
			return "1";
		} else if (name.equals("四期B")) {
			return "2";
		} else if (name.equals("四期C")) {
			return "3";
		} else if (name.equals("五期A")) {
			return "4";
		} else if (name.equals("五期B")) {
			return "5";
		} else {
			return "";
		}
	}

	private void getFirstSchedule() {
		try {

			stat = conn.createStatement();
			String query;
			boolean sucess;

			query = String.format(
					"SELECT TeamName, start_time, end_time FROM TeamList_oneteam, record,court WHERE court.id = record.court_id AND record.team_id = TeamList_oneteam.ID AND court.id = %s AND start_time > '2023-%s 00:00:00' AND start_time < '2023-%s 23:00:00';",
					"1", dates.getWeekDateList().get(0), dates.getWeekDateList().get(0));
			sucess = stat.execute(query);
			if (sucess) {
				ResultSet result = stat.getResultSet();
//				System.out.println(showResultSet2(result));
				for (String s : showResultSet(result)) {
					mon_panel.add(new JLabel("    " + s));
				}
				result.close();
			}
			query = String.format(
					"SELECT TeamName, start_time, end_time FROM TeamList_oneteam, record,court WHERE court.id = record.court_id AND record.team_id = TeamList_oneteam.ID AND court.id = %s AND start_time > '2023-%s 00:00:00' AND start_time < '2023-%s 23:00:00';",
					"1", dates.getWeekDateList().get(1), dates.getWeekDateList().get(1));
			sucess = stat.execute(query);
			if (sucess) {
				ResultSet result = stat.getResultSet();
//				System.out.println(showResultSet2(result));
				for (String s : showResultSet(result)) {
					tue_panel.add(new JLabel("    " + s));
				}
				result.close();
			}
			query = String.format(
					"SELECT TeamName, start_time, end_time FROM TeamList_oneteam, record,court WHERE court.id = record.court_id AND record.team_id = TeamList_oneteam.ID AND court.id = %s AND start_time > '2023-%s 00:00:00' AND start_time < '2023-%s 23:00:00';",
					"1", dates.getWeekDateList().get(2), dates.getWeekDateList().get(2));
			sucess = stat.execute(query);
			if (sucess) {
				ResultSet result = stat.getResultSet();
//				System.out.println(showResultSet2(result));
				for (String s : showResultSet(result)) {
					wed_panel.add(new JLabel("    " + s));
				}
				result.close();
			}
			query = String.format(
					"SELECT TeamName, start_time, end_time FROM TeamList_oneteam, record,court WHERE court.id = record.court_id AND record.team_id = TeamList_oneteam.ID AND court.id = %s AND start_time > '2023-%s 00:00:00' AND start_time < '2023-%s 23:00:00';",
					"1", dates.getWeekDateList().get(3), dates.getWeekDateList().get(3));
			sucess = stat.execute(query);
			if (sucess) {
				ResultSet result = stat.getResultSet();
//				System.out.println(showResultSet2(result));
				for (String s : showResultSet(result)) {
					thur_panel.add(new JLabel("    " + s));
				}
				result.close();
			}
			query = String.format(
					"SELECT TeamName, start_time, end_time FROM TeamList_oneteam, record,court WHERE court.id = record.court_id AND record.team_id = TeamList_oneteam.ID AND court.id = %s AND start_time > '2023-%s 00:00:00' AND start_time < '2023-%s 23:00:00';",
					"1", dates.getWeekDateList().get(4), dates.getWeekDateList().get(4));
			sucess = stat.execute(query);
			if (sucess) {
				ResultSet result = stat.getResultSet();
//				System.out.println(showResultSet2(result));
				for (String s : showResultSet(result)) {
					fri_panel.add(new JLabel("    " + s));
				}
				result.close();
			}
			query = String.format(
					"SELECT TeamName, start_time, end_time FROM TeamList_oneteam, record,court WHERE court.id = record.court_id AND record.team_id = TeamList_oneteam.ID AND court.id = %s AND start_time > '2023-%s 00:00:00' AND start_time < '2023-%s 23:00:00';",
					"1", dates.getWeekDateList().get(5), dates.getWeekDateList().get(5));
			sucess = stat.execute(query);
			if (sucess) {
				ResultSet result = stat.getResultSet();
//				System.out.println(showResultSet2(result));
				for (String s : showResultSet(result)) {
					sat_panel.add(new JLabel("    " + s));
				}
				result.close();
			}
			query = String.format(
					"SELECT TeamName, start_time, end_time FROM TeamList_oneteam, record,court WHERE court.id = record.court_id AND record.team_id = TeamList_oneteam.ID AND court.id = %s AND start_time > '2023-%s 00:00:00' AND start_time < '2023-%s 23:00:00';",
					"1", dates.getWeekDateList().get(6), dates.getWeekDateList().get(6));
			sucess = stat.execute(query);
			if (sucess) {
				ResultSet result = stat.getResultSet();
//				System.out.println(showResultSet2(result));
				for (String s : showResultSet(result)) {
					sun_panel.add(new JLabel("    " + s));
				}
				result.close();
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 依照所選場地，於行事曆上顯示系隊預約狀況
	private void getSchedule(String court) {
		try {
			String court_id = getCourtID(court);

			stat = conn.createStatement();
			String query;
			boolean sucess;

			mon_panel.removeAll();
			tue_panel.removeAll();
			wed_panel.removeAll();
			thur_panel.removeAll();
			fri_panel.removeAll();
			sat_panel.removeAll();
			sun_panel.removeAll();

			mon_panel.add(new JLabel(String.format("  %s (一)", dates.getWeekDateList().get(0))));
			tue_panel.add(new JLabel(String.format("  %s (二)", dates.getWeekDateList().get(1))));
			wed_panel.add(new JLabel(String.format("  %s (三)", dates.getWeekDateList().get(2))));
			thur_panel.add(new JLabel(String.format("  %s (四)", dates.getWeekDateList().get(3))));
			fri_panel.add(new JLabel(String.format("  %s (五)", dates.getWeekDateList().get(4))));
			sat_panel.add(new JLabel(String.format("  %s (六)", dates.getWeekDateList().get(5))));
			sun_panel.add(new JLabel(String.format("  %s (日)", dates.getWeekDateList().get(6))));

			query = String.format(
					"SELECT TeamName, start_time, end_time FROM TeamList_oneteam, record,court WHERE court.id = record.court_id AND record.team_id = TeamList_oneteam.ID AND court.id = %s AND start_time > '2023-%s 00:00:00' AND start_time < '2023-%s 23:00:00';",
					court_id, dates.getWeekDateList().get(0), dates.getWeekDateList().get(0));
			sucess = stat.execute(query);
			if (sucess) {
				ResultSet result = stat.getResultSet();
//				System.out.println(showResultSet2(result));
				for (String s : showResultSet(result)) {
					mon_panel.add(new JLabel("    " + s));
				}
				result.close();
			}

			query = String.format(
					"SELECT TeamName, start_time, end_time FROM TeamList_oneteam, record,court WHERE court.id = record.court_id AND record.team_id = TeamList_oneteam.ID AND court.id = %s AND start_time > '2023-%s 00:00:00' AND start_time < '2023-%s 23:00:00';",
					court_id, dates.getWeekDateList().get(1), dates.getWeekDateList().get(1));
			sucess = stat.execute(query);
			if (sucess) {
				ResultSet result = stat.getResultSet();
//				System.out.println(showResultSet2(result));
				for (String s : showResultSet(result)) {
					tue_panel.add(new JLabel("    " + s));
				}
				result.close();
			}

			query = String.format(
					"SELECT TeamName, start_time, end_time FROM TeamList_oneteam, record,court WHERE court.id = record.court_id AND record.team_id = TeamList_oneteam.ID AND court.id = %s AND start_time > '2023-%s 00:00:00' AND start_time < '2023-%s 23:00:00';",
					court_id, dates.getWeekDateList().get(2), dates.getWeekDateList().get(2));
			sucess = stat.execute(query);
			if (sucess) {
				ResultSet result = stat.getResultSet();
//				System.out.println(showResultSet2(result));
				for (String s : showResultSet(result)) {
					wed_panel.add(new JLabel("    " + s));
				}
				result.close();
			}

			query = String.format(
					"SELECT TeamName, start_time, end_time FROM TeamList_oneteam, record,court WHERE court.id = record.court_id AND record.team_id = TeamList_oneteam.ID AND court.id = %s AND start_time > '2023-%s 00:00:00' AND start_time < '2023-%s 23:00:00';",
					court_id, dates.getWeekDateList().get(3), dates.getWeekDateList().get(3));
			sucess = stat.execute(query);
			if (sucess) {
				ResultSet result = stat.getResultSet();
//				System.out.println(showResultSet2(result));
				for (String s : showResultSet(result)) {
					thur_panel.add(new JLabel("    " + s));
				}
				result.close();
			}

			query = String.format(
					"SELECT TeamName, start_time, end_time FROM TeamList_oneteam, record,court WHERE court.id = record.court_id AND record.team_id = TeamList_oneteam.ID AND court.id = %s AND start_time > '2023-%s 00:00:00' AND start_time < '2023-%s 23:00:00';",
					court_id, dates.getWeekDateList().get(4), dates.getWeekDateList().get(4));
			sucess = stat.execute(query);
			if (sucess) {
				ResultSet result = stat.getResultSet();
//				System.out.println(showResultSet2(result));
				for (String s : showResultSet(result)) {
					fri_panel.add(new JLabel("    " + s));
				}
				result.close();
			}

			query = String.format(
					"SELECT TeamName, start_time, end_time FROM TeamList_oneteam, record,court WHERE court.id = record.court_id AND record.team_id = TeamList_oneteam.ID AND court.id = %s AND start_time > '2023-%s 00:00:00' AND start_time < '2023-%s 23:00:00';",
					court_id, dates.getWeekDateList().get(5), dates.getWeekDateList().get(5));
			sucess = stat.execute(query);
			if (sucess) {
				ResultSet result = stat.getResultSet();
//				System.out.println(showResultSet2(result));
				for (String s : showResultSet(result)) {
					sat_panel.add(new JLabel("    " + s));
				}
				result.close();
			}

			query = String.format(
					"SELECT TeamName, start_time, end_time FROM TeamList_oneteam, record,court WHERE court.id = record.court_id AND record.team_id = TeamList_oneteam.ID AND court.id = %s AND start_time > '2023-%s 00:00:00' AND start_time < '2023-%s 23:00:00';",
					court_id, dates.getWeekDateList().get(6), dates.getWeekDateList().get(6));
			sucess = stat.execute(query);
			if (sucess) {
				ResultSet result = stat.getResultSet();
//				System.out.println(showResultSet2(result));
				for (String s : showResultSet(result)) {
					sun_panel.add(new JLabel("    " + s));
				}
				result.close();
			}
			/// 加到個個頁面 更新
			TimeSet timeSet = new TimeSet();
			query = String.format("SELECT start_time FROM record WHERE team_id = '%s'", id);
			sucess = stat.execute(query);
			if (sucess) {
				ResultSet result = stat.getResultSet();
				if (getDeadline(result).size() != 0) {
					for (int i = 0; i < getDeadline(result).size(); i++) {
						if (timeSet.isTimeVio(getDeadline(result).get(i))) {
							query = String.format(
									"UPDATE record SET state = 'Unregistered' WHERE team_id = '%s' AND start_time = '%s'",
									id, getTime(result).get(i));
							sucess = stat.execute(query);
						}
					}
					result.close();
				}
			}

			///

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String[] showResultSet(ResultSet result) throws SQLException {
		scheduleHandler = new ScheduleHandler();
//		System.out.println("1");

		while (result.next()) {
//			System.out.println("2");
//			System.out.println(result.getString(1) + " " + result.getString(2) + " " + result.getString(3));

			scheduleHandler.addSchedule(new Schedule(result.getString(1), result.getString(2), result.getString(3)));
		}
		scheduleHandler.transformSchedule();

		return scheduleHandler.getNewScheduleList();
	}

	/// 加到各個頁面
	public static ArrayList<String> getDeadline(ResultSet result) throws SQLException {
		ArrayList<String> list = new ArrayList<String>();
		while (result.next()) {
			list.add(result.getString(1).substring(5, 14) + "20:00");
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

//	public static String showResultSet2(ResultSet result) throws SQLException {
//
//		ResultSetMetaData metaData = result.getMetaData();
//		int columnCount = metaData.getColumnCount();
//		String output = "";
//
//		for (int i = 1; i <= columnCount; i++) {
//			output += String.format("%35s", metaData.getColumnLabel(i));
//		}
//		output += "\n";
//
//		while (result.next()) {
//			for (int i = 1; i <= columnCount; i++) {
//				output += String.format("%35s", result.getString(i));
//			}
//			output += "\n";
//		}
//		return output;
//	}
}

//}
