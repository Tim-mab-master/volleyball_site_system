import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

//import com.mysql.jdbc.Statement;

public class Login extends JFrame{
	private static final int FRAME_WIDTH = 800;
	private static final int FRAME_HEIGHT = 600;
	private JTextField textFieldID, textFieldPW;
	private JLabel ID, PW, LoginAgain,howtouse,howtouse2,howtouse3,howtouse4,howtouse5,howtouse6,howtouse7,howtouse8;
	private JButton btnResetPW, btnLogin;
	private String userID;
	private String passWord;
	private String teamname;
	private String switchFrame;
	private resetPassword frame;
	private boolean inputTeamNameExist=false;
	private JPanel pane0,pane1,pane2,pane3,pane4,pane5,pane6,pane7,pane8,pane9,pane10,pane11,pane12;
	Connection conn;
//	private boolean login_successful = false;
	
	public Login(Connection conn, String switchFrame) throws Login.PasswordError, Login.UserError{
		this.setTitle("NCCU排球場協＿登入系統");
		this.switchFrame = switchFrame;
		this.conn = conn;
		createTextField();
		createLabel();
		createButton();
		createLayout();
		this.setSize(this.FRAME_WIDTH, this.FRAME_HEIGHT);
		
	}
	private void createTextField() {
		// Here is your code //
		final int FIELD_WIDTH = 10;
		textFieldID=new JTextField(FIELD_WIDTH);
		textFieldID.setText("");
		
		textFieldPW=new JTextField(FIELD_WIDTH);
		textFieldPW.setText("");	
	}
	public void setLoginAgain() {
		LoginAgain = new JLabel("請再次使用新密碼登入!");
		pane0.add(LoginAgain);
	}
	private void createLabel() {
		ID = new JLabel("User ID:");
		PW = new JLabel("Password:");
		howtouse = new JLabel("--------------------------|User ID|--------------------------");
		howtouse2 = new JLabel("Please input User ID! User ID is your departmentcode.");
		howtouse3 = new JLabel("For example: MAB 302, MIS 306. User ID should be 3 letters!");
		howtouse4 = new JLabel("-------------------------|Pass Word|-------------------------");
		howtouse5 = new JLabel("Password should be 8 letter.");
		howtouse6 = new JLabel("Before reset your password: Use the password system given.");
		howtouse7 = new JLabel("After reset your password: Please input your new pass word.");
		howtouse8 = new JLabel("-------------------------------------------------------------");
	}
	private void createButton()throws PasswordError, UserError{
		btnResetPW = new JButton("重新設定密碼");
		btnLogin = new JButton("登入");
		btnResetPW.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String UserID = textFieldID.getText();
				String pw = textFieldPW.getText();
				userID=textFieldID.getText();
				try {
					setTeamInfoormation();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					if (UserID.length() == 4) throw new UserError("Please input USER ID not Teamname!");
					if (UserID.length() != 3) throw new UserError("Wrong userID!userID should be 8 letter");		
					if (pw.length() != 8) throw new PasswordError("Password should be 8 letter");
					if (inputTeamNameExist==false) throw new UserError("The userID does not EXIST!");	
					if(passWord.compareTo(pw)!=0) throw new PasswordError("Wrong password!");
					if(inputTeamNameExist==true && passWord.compareTo(pw)==0) {
						dispose();
						frame = new resetPassword(conn, userID,teamname,switchFrame);
						frame.setVisible(true);
					}
					
				} catch (PasswordError | UserError e) {
					JOptionPane.showMessageDialog(null,e,"Error", JOptionPane.ERROR_MESSAGE);
					textFieldPW.setText("");
				} catch (resetPassword.PasswordError e) {
					e.printStackTrace();
				} catch (resetPassword.UserError e) {
					e.printStackTrace();
				}
			}
		});
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String UserID=textFieldID.getText();
				String pw = textFieldPW.getText();
				userID=textFieldID.getText();
				try {
					setTeamInfoormation();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					if (UserID.length() == 4) throw new UserError("Please input USER ID not Teamname!");
					if (UserID.length() != 3) throw new UserError("Wrong userID! UserID should be 3 letter");		
					if (pw.length() != 8) throw new PasswordError("Password should be 8 letter");
					if (inputTeamNameExist==false) throw new UserError("The user ID does not EXIST!");	
					if(passWord.compareTo(pw)!=0) throw new PasswordError("Wrong password!");
					
					
				
//					//連結到其他頁面
//					login_successful = true;
					setVisible(false);
					if(switchFrame == "reservation") {
						try {
//							Reserve frame = new Reserve((com.mysql.jdbc.Connection) conn,  UserID);
							Reserve frame = new Reserve( conn,  UserID);
							frame.setVisible(true);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}else if(switchFrame == "register") {
						RegisterFrame frame = new RegisterFrame(conn, UserID);
						frame.setVisible(true);
						
					}else if(switchFrame == "report") {
//						(com.mysql.jdbc.Connection)
						ReportFrame frame = new ReportFrame( conn, UserID);
						frame.setVisible(true);
						
					}
					
					
					
					
				
				} catch (PasswordError | UserError e) {
					JOptionPane.showMessageDialog(null,e,"Error", JOptionPane.ERROR_MESSAGE);
					textFieldPW.setText("");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
	}
//	public boolean getLoginSuccessful() {
//		return login_successful;
//	}
//	
	
	
	private void createLayout(){
		JPanel flow_panel = new JPanel(new GridLayout(20,1));
		pane0 =new JPanel();
		pane1=new JPanel();
		pane2=new JPanel();
		pane3=new JPanel();
		pane4=new JPanel();
		pane5=new JPanel();
		pane6=new JPanel();
		pane7=new JPanel();
		pane8=new JPanel();
		pane9=new JPanel();
		pane10=new JPanel();
		pane11=new JPanel();
		pane12=new JPanel();
		
		pane1.add(ID);
		pane1.add(textFieldID);
		pane2.add(PW);
		pane2.add(textFieldPW);
		pane3.add(btnResetPW);
		pane3.add(btnLogin);
		pane4.add(howtouse);
		pane5.add(howtouse2);
		pane6.add(howtouse3);
		pane7.add(howtouse4);
		pane8.add(howtouse5);
		pane9.add(howtouse6);
		pane10.add(howtouse7);
		pane11.add(howtouse8);
		
		flow_panel.add(pane0);
		flow_panel.add(pane1);
		flow_panel.add(pane2);
		flow_panel.add(pane3);
		flow_panel.add(pane12);
		flow_panel.add(pane4);
		flow_panel.add(pane5);
		flow_panel.add(pane6);
		flow_panel.add(pane7);
		flow_panel.add(pane8);
		flow_panel.add(pane9);
		flow_panel.add(pane10);
		flow_panel.add(pane11);
		
		add(flow_panel);
	}
	public void setTeamInfoormation() throws SQLException { 
		
			Statement stat = (Statement) conn.createStatement();
			String query;
			boolean success;
			query="SELECT TeamName,PassWord FROM `TeamList_oneteam` WHERE ID="+textFieldID.getText();
			success=stat.execute(query);
			if (success) {
				ResultSet result = stat.getResultSet();
				while (result.next()) {
					inputTeamNameExist=true;
					teamname=result.getString(1);
					passWord=result.getString(2);
				}
				result.close();
			}
			else {
				System.out.println("query failed");
			}
		
	}
	public String getTeamName() {
		return teamname;
	}
	public String getPassWord() {
		return passWord;
	}
	public String getID() {
		return userID;
	}
	class UserError extends Exception {
		public UserError(String Error){
			 super(Error);
		}
	}
	class PasswordError extends Exception {
		public PasswordError(String Error){
			 super(Error);
		}
	}
	class UserNameError extends Exception {
		public UserNameError(String Error){
			 super(Error);
		}
	}
	

}
