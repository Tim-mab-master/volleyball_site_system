import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GetWeekDate {
	private long currentTimeMillis = System.currentTimeMillis();
	private ArrayList<String> weekDateList;
	
	public GetWeekDate() {
//		currentTimeMillis = System.currentTimeMillis();
		weekDateList = getAllWeekDayDateByMillis(currentTimeMillis);
	}
	
	// �����e�@�P�������
	public ArrayList<String> getWeekDateList(){
		return weekDateList;
	}
	
	// ������w����@��ɶ�����g�@�P�������
	private ArrayList<String> getAllWeekDayDateByMillis(long timeMillis) {
		ArrayList<String> list = new ArrayList<String>();
		
		//�o����w�ɶ��O�g�X
		String whatDay = getWahtDaySomeday(timeMillis);
		System.out.println("�P��: "+ whatDay);
		//�����P�g�@�����j�Ѽ�
		int dayFromMonday = getHowManyDayFromMonday(whatDay);
		
		//����o�g�Ĥ@�Ѫ��@���
		long dayMillis = 24*60*60*1000;
		long firstOfWeekMillis = timeMillis - dayFromMonday * dayMillis;
		
		for(long i = firstOfWeekMillis; i < firstOfWeekMillis + 7*dayMillis; i += dayMillis) {
			Date targetDate = new Date(i);
			SimpleDateFormat format = new SimpleDateFormat("MM-dd");
			String targetDay = format.format(targetDate);
			list.add(targetDay);
		}
		
		return list;
		
		
	}
	
	//�o����w�ɶ��O�g�X
	private String getWahtDaySomeday(long timeMillis) {
		Date todayDate = new Date(timeMillis);
		SimpleDateFormat formatE = new SimpleDateFormat("E");
		String whatDay = "";
		try {
			whatDay = formatE.format(todayDate);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return whatDay;
	}
	
	//�ھڬP���X�A����P�P���@�ۮt�X��
	private static int getHowManyDayFromMonday(String someDay) {
		int day = 0;
		switch(someDay) {
			case "�g�@":
				day = 0;
				break;
			case "�g�G":
				day = 1;
				break;
			case "�g�T":
				day = 2;
				break;
			case "�g�|":
				day = 3;
				break;
			case "�g��":
				day = 4;
				break;
			case "�g��":
				day = 5;
				break;
			case "�g��":
				day = 6;
				break;
			default:
				//System.out.println("���s�b");
				break;
		}
		System.out.println("Day: "+day);
		return day;
	}

}
