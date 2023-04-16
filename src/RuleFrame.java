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
			this.setTitle("NCCU�Ʋy����ĨϥγW�h");
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
		outputArea.append("�ЧQ�ΤU����s��ܱ��d�ݪ������ϥγW�h�I\n\n|����n�J�t��|\n�d�ݵn�J�t�ΥH�αK�X���]�ϥγW�h\n\n|���󭺭�-��ƾ�|\n�d�ݭ���-��ƾ�ϥγW�h\n\n|����w���t��|\n�d�ݦp��ϥΨt�������H�ιw���t��\n\n|����ñ��t��|\n�d�ݦp��ϥγ��añ��\��\n\n|�������|�t��|\n�d�ݦp��ϥ����|�t��");
	}
	private void createButton(){
		btnBacktoHome = new JButton("�^����");
		btnLogin = new JButton("����n�J�t��");
		btnHomepage = new JButton("���󭺭�-��ƾ�");
		btnReserve = new JButton("����w���t��");
		btnCheckIn = new JButton("�������t��");
		btnReport = new JButton("�������|�t��");
		
		btnHomepage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String info="�U����-��ƾ�U\n"
						+"���������A�D�n�O�H�U�ӳ��a���ϥΪ��p�@���D�n����T�e�{�A\n"
						+ "���ϥΪ̥i�H���P�x����P�C�@�ѨC�p�ɬO���Өt���ϥΡC\r\n"
						+ "�k�W���]�Y����ܥX�{�b�ɶ��A���ϥΪ̤�K��ӡC\r\n"
						+ "�����U�観���ӫ��s�A���O�����줣�P�����ѨϥΪ̰����P�γ~�A�H�U�N²�椶�СG\r\n"
						+ "\r\n"
						+ "1. ���a�u�d�ߡv���s�G\n"
						+ "���H�U�Ԧ���������d�ߤ����a�A���U�d�߫��s����A�Y�|��ܸӳ��a��P�C�@�ѨC�p�ɪ��t���ϥΪ��p�C\r\n"
						+ "\r\n"
						+ "2. �u�w���v���s�G\n"
						+ "���U��A�Y�|���n�J�h�|���X�n�J�����C�n�J������Y�|�����u�w���t�Ρv�����ѨϥΪ̰����a�w���ʧ@�C\r\n"
						+ "\r\n"
						+ "3. �u����v���s�G\n"
						+ "���U��A�Y�|���n�J�h�|���X�n�J�����C�n�J������Y�|�����u����t�Ρv�����ѨϥΪ̰����a�w���ʧ@�C\r\n"
						+ "\r\n"
						+ "4. �u���|�v���s�G\n"
						+ "���U��A�Y�|���n�J�h�|���X�n�J�����C�n�J������Y�|�����u���|�t�Ρv�����ѨϥΪ̰����a�w���ʧ@�C\r\n"
						+ "\r\n"
						+ "5. �u�W�h�v���s�G\n"
						+ "���U��|�����ܳW�h�����ѨϥΪ̬d�\������t�ά����W�w�C\r\n"
						+ ""
						;
				outputArea.setText(info);
			}
		});	
		
		btnReserve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String info="�U�w���t�ΨϥγW�h�U\r\n"
						+ "\n"
						+ "1.�i�w���ɶ����G\r\n"
						+ "�w����U�ɶ����U�@�Ӿ��I��w����ѩ����7�Ѥ��C\r\n"
						+ "�Ҧp�G\r\n"
						+ "�Y�z�b6/1��16:05�i��w��\r\n"
						+ "�i�w�����ɶ���6/1��17:00��6/7��17:00�C\r\n"
						+ "\n"
						+ "2.�w���ɬq�H1�Ӥp�ɬ����A�@���̦h�i�H�w��3�Ӯɬq�C\r\n"
						+ "\n"
						+ "3.�Y�Q�R���w���G\r\n"
						+ "�n�N����H�ήɶ��վ㬰�P�z�Q�������w��������B�ɶ��@�P\r\n"
						+ "�~�i�H���\�R���w��\r\n"
						+ "�Ҧp�G\r\n"
						+ "�Y�z�Q����6/1 16�G00��19�G00���w���A\r\n"
						+ "�����N����վ㬰6/1�A�ɶ��վ㬰16:00�P19:00�A\r\n"
						+ "�èϥΤU�Ԧ�����ܡu�R���v�ﶵ�A�~�i�H�R�����\�C\r\n"
						+ "\n"
						+ "4.�Y�Q�R�����v�����G\n"
						+ "�i��ܷQ�R�����������a�A"
						+ "������m���s���U��A�Y�i�R���ӳ��a�Ҧ������C"
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
				String info="�U�n�J�t�ΨϥγW�h�U"
						+"\n"
						+"�n�J�t�έ����D�n���T�ӭn���G�b���]User ID�^�B�K�X�]Pass Word�^�B���]�K�X(Reset)\n"
						+"\n"
						+"�b���]User ID)�G\n"
						+ "�C�Өt���@�ձb��\n"
						+ "�U�t�k�k�Ʀ@�ΦP�@�ձb���K�X\n"
						+ "�b�����U�t���t�O�N�X\n"
						+ "�Ҧp�G���Ĩt 302 / ��ިt 306\n"
						+ "\n"
						+ "�K�X�G\n"
						+ "�K�X��8��r��A�^��P�Ʀr�V��\n"
						+ "�i�����Ҭ��^��]�i�H�����Ҭ��Ʀr\n"
						+ "�t�Φb��l�ƮɡA�|���t���C�Өt���@�ձK�X\n"
						+ "�ӱK�X���üƲ��ͤ�8��^�ƲV�����r��\n"
						+ "\n"
						+ "���]�K�X�G\n"
						+ "�I�ﭫ�]�K�X���ﶵ�Y�i���s�]�w�K�X\n"
						+ "�Цb�������X��A��J�s���K�X\n"
						+ "�K�X�@�ˬ�8��A�^��P�Ʀr�V��\n"
						+ "�b���\���K�X��A�|�A�����^�n�J����\n"
						+ "�·Хηs���K�X�A���n�J�A�Y�i�ާ@��L�t��\n";
				outputArea.setText(info);
			}
		});	
		btnCheckIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String info="�U���a����t�ΨϥγW�h�U\r\n"
						+ "�I���d�߫��s�i�d�ݹw�������O�_�w����쪺�ɶ�\r\n"
						+ "�Y�L�|�������ɶ��B�S�w�������γ���ɶ��w�L�h�|��ܳ��쥢��\r\n"
						+ "��y���b�w�����ɶ���F���a��\r\n"
						+ "�̱߻ݩ�w���ɶ���20����ñ��A�_�h���P����\r\n"
						+ "����N�O�H�W�A��\r\n"
						+ "�H�W��3���h�Өt�����v�@��§���A�H�ܤ���\r\n"
						+ "\r\n"
						+ "����覡�G\r\n"
						+ "�����d�ߵM��t�δN�|���X�i�H�Өt����ɥi�H���쪺�ɶ�\r\n"
						+ "�M���I��u����v���s�N�i�H���즨�\�I\r\n"
						+ "\r\n"
						+ "����覡�w�]����ءG\r\n"
						+ "1.���y���aQRcode\r\n"
						+ "2.������a�Ӥ��äW�Ǩ�t�Τ� \r\n"
						+ "\r\n"
						+ "�W�ǧ���A���u����v���s\r\n"
						+ "���X�u���즨�\�I�v�r�˫K��ܳ��즨�\\r\n"
						+ "";
				outputArea.setText(info);
			}
		});	
		btnReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String info="�U���|�t�ΨϥγW�h�U\n"
						+ "�Y�b�s�W�B�ʳ����t���o�{��ƾ���ܳ��a�w�Q�w��\n"
						+ "���b�w���ɶ���20������\n"
						+ "�ӳ��a���M�O�L�H�ϥΪ����A��\n"
						+ "�i�H�i�J���|����\n"
						+ "�w��Ӯɬq�w���ӳ��a���H�i�����|\n"
						+ "\n"
						+ "���|�ɽФW�ǩ��ᤧ�ų��a���Ӥ��@���s��\n"
						+ "�t�αN�|�O���줧�t���H�W�A��\n"
						+ "�Y���@�t���H�W��3���h�Өt���N���v�@��§���A�H�ܤ���\n"
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
