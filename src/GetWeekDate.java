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
	
	// 獲取當前一星期的日期
	public ArrayList<String> getWeekDateList(){
		return weekDateList;
	}
	
	// 獲取指定日期毫秒時間的當週一星期的日期
	private ArrayList<String> getAllWeekDayDateByMillis(long timeMillis) {
		ArrayList<String> list = new ArrayList<String>();
		
		//得到指定時間是週幾
		String whatDay = getWahtDaySomeday(timeMillis);
		System.out.println("星期: "+ whatDay);
		//紀錄與週一的間隔天數
		int dayFromMonday = getHowManyDayFromMonday(whatDay);
		
		//獲取這週第一天的毫秒值
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
	
	//得到指定時間是週幾
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
	
	//根據星期幾，獲取與星期一相差幾天
	private static int getHowManyDayFromMonday(String someDay) {
		int day = 0;
		switch(someDay) {
			case "週一":
				day = 0;
				break;
			case "週二":
				day = 1;
				break;
			case "週三":
				day = 2;
				break;
			case "週四":
				day = 3;
				break;
			case "週五":
				day = 4;
				break;
			case "週六":
				day = 5;
				break;
			case "週日":
				day = 6;
				break;
			default:
				//System.out.println("不存在");
				break;
		}
		System.out.println("Day: "+day);
		return day;
	}

}
