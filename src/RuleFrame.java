import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.*;

public class RuleFrame extends JFrame{
	private static final int FRAME_WIDTH = 800;
	private static final int FRAME_HEIGHT = 600;
	private JButton btnLogin, btnHomepage, btnReserve, btnCheckIn, btnReport,btnBacktoHome;
	private JTextArea outputArea;
	private JScrollPane scrollPane;
	private String id;
	private JPanel pane0,pane1,pane2,pane3,pane4,pane5,pane6,pane7,pane8,pane9,pane10;
	Connection conn;
	
	public RuleFrame(Connection conn, String id) {
			this.setTitle("NCCU排球場協＿使用規則");
			this.setSize(this.FRAME_WIDTH, this.FRAME_HEIGHT);
			this.id = id;
			this.conn = conn;
			createButton();
			createTextArea();
			createLayout();
	}
	private void createTextArea() {
		outputArea = new JTextArea();
		outputArea.setEditable(false);
		outputArea.append("請利用下方按鈕選擇欲查看的頁面使用規則！\n\n|關於登入系統|\n查看登入系統以及密碼重設使用規則\n\n|關於首頁-行事曆|\n查看首頁-行事曆使用規則\n\n|關於預約系統|\n查看如何使用系隊頁面以及預約系統\n\n|關於簽到系統|\n查看如何使用場地簽到功能\n\n|關於檢舉系統|\n查看如何使用檢舉系統");
	}
	private void createButton(){
		btnBacktoHome = new JButton("回首頁");
		btnLogin = new JButton("關於登入系統");
		btnHomepage = new JButton("關於首頁-行事曆");
		btnReserve = new JButton("關於預約系統");
		btnCheckIn = new JButton("關於報到系統");
		btnReport = new JButton("關於檢舉系統");
		
		btnHomepage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String info="｜首頁-行事曆｜\n"
						+"首頁部分，主要是以各個場地的使用狀況作為主要的資訊呈現，\n"
						+ "讓使用者可以輕鬆掌握當周每一天每小時是哪個系隊使用。\r\n"
						+ "右上角也即時顯示出現在時間，讓使用者方便對照。\r\n"
						+ "首頁下方有五個按鈕，分別切換到不同介面供使用者做不同用途，以下將簡單介紹：\r\n"
						+ "\r\n"
						+ "1. 場地「查詢」按鈕：\n"
						+ "先以下拉式選單選取欲查詢之場地，按下查詢按鈕之後，即會顯示該場地當周每一天每小時的系隊使用狀況。\r\n"
						+ "\r\n"
						+ "2. 「預約」按鈕：\n"
						+ "按下後，若尚未登入則會跳出登入介面。登入完畢後即會跳掉「預約系統」介面供使用者做場地預約動作。\r\n"
						+ "\r\n"
						+ "3. 「報到」按鈕：\n"
						+ "按下後，若尚未登入則會跳出登入介面。登入完畢後即會跳掉「報到系統」介面供使用者做場地預約動作。\r\n"
						+ "\r\n"
						+ "4. 「檢舉」按鈕：\n"
						+ "按下後，若尚未登入則會跳出登入介面。登入完畢後即會跳掉「檢舉系統」介面供使用者做場地預約動作。\r\n"
						+ "\r\n"
						+ "5. 「規則」按鈕：\n"
						+ "按下後會切換至規則介面供使用者查閱此場協系統相關規定。\r\n"
						+ ""
						;
				outputArea.setText(info);
			}
		});	
		
		btnReserve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String info="｜預約系統使用規則｜\r\n"
						+ "\n"
						+ "1.可預約時間為：\r\n"
						+ "預約當下時間的下一個整點到預約當天往後推7天內。\r\n"
						+ "例如：\r\n"
						+ "若您在6/1的16:05進行預約\r\n"
						+ "可預約的時間為6/1的17:00到6/7的17:00。\r\n"
						+ "\n"
						+ "2.預約時段以1個小時為單位，一次最多可以預約3個時段。\r\n"
						+ "\n"
						+ "3.若想刪除預約：\r\n"
						+ "要將日期以及時間調整為與您想取消的預約之日期、時間一致\r\n"
						+ "才可以成功刪除預約\r\n"
						+ "例如：\r\n"
						+ "若您想取消6/1 16：00到19：00的預約，\r\n"
						+ "必須將日期調整為6/1，時間調整為16:00與19:00，\r\n"
						+ "並使用下拉式選單選擇「刪除」選項，才可以刪除成功。\r\n"
						+ "\n"
						+ "4.若想刪除歷史紀錄：\n"
						+ "可選擇想刪除紀錄的場地，"
						+ "選取重置按鈕按下後，即可刪除該場地所有紀錄。"
						+ ""
						;
				outputArea.setText(info);
			}
		});	
		
		
		
		
		btnBacktoHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setVisible(false);
				TimetableFrame frame;
				try {
					frame = new TimetableFrame(conn, id);
					frame.setVisible(true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});	
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String info="｜登入系統使用規則｜"
						+"\n"
						+"登入系統頁面主要有三個要素：帳號（User ID）、密碼（Pass Word）、重設密碼(Reset)\n"
						+"\n"
						+"帳號（User ID)：\n"
						+ "每個系有一組帳號\n"
						+ "各系男女排共用同一組帳號密碼\n"
						+ "帳號為各系的系別代碼\n"
						+ "例如：金融系 302 / 資管系 306\n"
						+ "\n"
						+ "密碼：\n"
						+ "密碼為8位字串，英文與數字混雜\n"
						+ "可全部皆為英文也可以全部皆為數字\n"
						+ "系統在初始化時，會分配給每個系隊一組密碼\n"
						+ "該密碼為亂數產生之8位英數混雜的字串\n"
						+ "\n"
						+ "重設密碼：\n"
						+ "點選重設密碼的選項即可重新設定密碼\n"
						+ "請在頁面跳出後，輸入新的密碼\n"
						+ "密碼一樣為8位，英文與數字混雜\n"
						+ "在成功更改密碼後，會再次跳回登入頁面\n"
						+ "麻煩用新的密碼再次登入，即可操作其他系統\n";
				outputArea.setText(info);
			}
		});	
		btnCheckIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String info="｜場地報到系統使用規則｜\r\n"
						+ "點擊查詢按鈕可查看預約場次是否已到報到的時間\r\n"
						+ "若無尚未到報到時間、沒預約場次或報到時間已過則會顯示報到失敗\r\n"
						+ "當球隊在預約的時間抵達場地後\r\n"
						+ "最晚需於預約時間後20分鐘簽到，否則視同未到\r\n"
						+ "未到將記違規乙次\r\n"
						+ "違規滿3次則該系隊停權一個禮拜，以示公平\r\n"
						+ "\r\n"
						+ "報到方式：\r\n"
						+ "先按查詢然後系統就會跳出可以該系隊當時可以報到的時間\r\n"
						+ "然後點選「報到」按鈕就可以報到成功！\r\n"
						+ "\r\n"
						+ "報到方式預設為兩種：\r\n"
						+ "1.掃描場地QRcode\r\n"
						+ "2.拍攝場地照片並上傳到系統中 \r\n"
						+ "\r\n"
						+ "上傳完後，按「報到」按鈕\r\n"
						+ "跳出「報到成功！」字樣便表示報到成功\r\n"
						+ "";
				outputArea.setText(info);
			}
		});	
		btnReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String info="｜檢舉系統使用規則｜\n"
						+ "若在山上運動場的系隊發現行事曆顯示場地已被預約\n"
						+ "但在預約時間後20分鐘後\n"
						+ "該場地仍然是無人使用的狀態時\n"
						+ "可以進入檢舉頁面\n"
						+ "針對該時段預約該場地的人進行檢舉\n"
						+ "\n"
						+ "檢舉時請上傳拍攝之空場地的照片作為存證\n"
						+ "系統將會記未到之系隊違規乙次\n"
						+ "若任一系隊違規滿3次則該系隊將停權一個禮拜，以示公平\n"
						+ "";
				outputArea.setText(info);
			}
		});	
	}
	private void createLayout(){
		JPanel flow_panel = new JPanel();
		pane0 =new JPanel();
		pane0.setPreferredSize(new Dimension(800,10));
		
		pane1=new JPanel();
		pane1.add(btnLogin);
		pane1.add(btnHomepage);
		pane1.add(btnReserve);
		pane1.add(btnCheckIn);
		pane1.add(btnReport);
		pane1.add(btnBacktoHome);

		scrollPane = new JScrollPane(outputArea );
		scrollPane.setPreferredSize(new Dimension(650,400));
		
		flow_panel.add(pane0);
		flow_panel.add(scrollPane);
		flow_panel.add(pane1);
		
		add(flow_panel);
	}
	

}
