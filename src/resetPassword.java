import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
//import com.mysql.jdbc.Statement;

public class resetPassword extends JFrame{
	private static final int FRAME_WIDTH = 800;
	private static final int FRAME_HEIGHT = 600;
	private JTextField textFieldNewPW, textFieldPWAgain;
	private JLabel newPW, PWAgain,howtouse,howtouse2;
	private JButton btnReset;
	private String ID;
	private String teamname;
	private String switchFrame;
	private JPanel pane0,pane1,pane2,pane3,pane4,pane5,pane6;
	private Login LoginFrame;
	private boolean resetSuccess = false;
	Connection conn;
	Statement stat;

	public resetPassword(Connection conn, String ID,String teamname, String switchFrame) throws resetPassword.PasswordError, resetPassword.UserError {
		this.ID=ID;
		this.teamname=teamname;
		this.conn = conn;
		this.switchFrame = switchFrame;
		this.setTitle("NCCU排球場協系統_"+teamname+"_重設密碼");
		createTextField();
		createLabel();
		createButton();
		createLayout();
		this.setSize(this.FRAME_WIDTH, this.FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	private void createTextField() {
		// Here is your code //
		final int FIELD_WIDTH = 10;
		textFieldNewPW=new JTextField(FIELD_WIDTH);
		textFieldNewPW.setText("");
		
		textFieldPWAgain=new JTextField(FIELD_WIDTH);
		textFieldPWAgain.setText("");	
	}
	private void createLabel() {
		newPW = new JLabel("New Password:");
		PWAgain = new JLabel("New Password(Type Again):");
		howtouse=new JLabel("Please input your new pass word.");
		howtouse2=new JLabel("Password should be 8 letter!");
	}
	private void createButton()throws PasswordError, UserError{
		// Here is your code //
		btnReset = new JButton("重新設定密碼");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String newP = textFieldNewPW.getText();
				String pwAG = textFieldPWAgain.getText();
				try {
					if (newP.length() == 0||pwAG.length() != 8) throw new PasswordError("Password should be 8 letter");		
					if (newP.length() != 8||pwAG.length() != 8) throw new PasswordError("Password should be 8 letter");
					if(newP.compareTo(pwAG)!=0)throw new UserError("Two password input is different. Please type again!");
					setNewPassWord(ID);
					dispose();
					LoginFrame= new Login(conn, switchFrame);
					LoginFrame.setLoginAgain();
					LoginFrame.setTitle("NCCU排球場協＿登入系統_請再次登入！");
					LoginFrame.setVisible(true);
				}catch(UserError | PasswordError e) {
					JOptionPane.showMessageDialog(null,e,"Error", JOptionPane.ERROR_MESSAGE);
				} catch (Login.PasswordError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Login.UserError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
	}
	private void createLayout(){
		JPanel flow_panel = new JPanel(new GridLayout(20,1));
		pane0 =new JPanel();
		pane1=new JPanel();
		pane2=new JPanel();
		pane3=new JPanel();
		pane4=new JPanel();
		pane5=new JPanel();
		pane6=new JPanel();
		
		pane1.add(newPW);
		pane1.add(textFieldNewPW);
		pane2.add(PWAgain);
		pane2.add(textFieldPWAgain);
		pane3.add(btnReset);
		pane4.add(howtouse);
		pane5.add(howtouse2);
		
		flow_panel.add(pane0);
		flow_panel.add(pane1);
		flow_panel.add(pane2);
		flow_panel.add(pane3);
		flow_panel.add(pane6);
		flow_panel.add(pane4);
		flow_panel.add(pane5);
		
		add(flow_panel);
	}
	private void setNewPassWord(String ID) throws SQLException {

	
			Statement stat = (Statement) conn.createStatement();
			String query;
			boolean success;
			query= "UPDATE `TeamList_oneteam` SET `PassWord`='"+textFieldPWAgain.getText()+"'WHERE `ID`="+ID;
			success=stat.execute(query);
			if(success) {
				resetSuccess=true;
			}
		
		
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

}
