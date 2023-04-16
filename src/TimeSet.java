
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.Timer;

public class TimeSet {
	
	public void setTimer(JLabel time) {

		final JLabel varTime = time;

		Timer timeAction = new Timer(100, new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				long timemillis = System.currentTimeMillis();

				// 转换日期显示格式

				SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");

				varTime.setText(df.format(new Date(timemillis)));

			}

		});

		timeAction.start();

	}
	
	public void stateCheck(JLabel time) {	
		final JLabel varTime = time;
		Timer timeAction = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long timemillis = System.currentTimeMillis();
				SimpleDateFormat format = new SimpleDateFormat("mm:ss");
				SimpleDateFormat output = new SimpleDateFormat("YYYY-MM-dd HH");
				String now = format.format(new Date(timemillis));
				Date start = new Date();
				Date end = new Date();
				Date nowtime = new Date();
				try {
					start = format.parse("00:00");
					end = format.parse("20:00");
					nowtime = format.parse(now);
					
					if (nowtime.getTime()>=start.getTime()&&nowtime.getTime()<=end.getTime()) {
						varTime.setText(output.format(new Date(timemillis))+":00:00");
						
					}
					else {
						varTime.setText("");
						
					}
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				

			}

		});
		timeAction.start();
		
		
		
	}
	
	public boolean reserveTime() {
		long timemillis = System.currentTimeMillis();
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date time1 = new Date();
		long h = 60*60*1000;
		
		try {
			time1 = format.parse(format.format(new Date(timemillis)));
			
			for (long i = getBegTime(); i < getFinalTime(); i+=h) {
				if(i - time1.getTime() > 0) {
					return true;
				}
			}	
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static String getWhatDaySomeday(long timeMills) {
		
		Date toDayDate = new Date(timeMills);
		SimpleDateFormat formatE = new SimpleDateFormat("E");
		String week = null;
		try {
			week = formatE.format(toDayDate);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return week;
	}
	
	public static ArrayList<String> getAllWeek(){
		long timeMillis = System.currentTimeMillis();
		ArrayList<String> list = new ArrayList<String>();
		long dayMiilis = 24*60*60*1000;
		long hourMillis = 60*60*1000;
		if(getNowTime() >= getFinalTime()-hourMillis) {
			timeMillis+=dayMiilis;
		}
		for(long i = timeMillis; i<timeMillis+7*dayMiilis; i+=dayMiilis ) {
			Date date = new Date(i);
			SimpleDateFormat df = new SimpleDateFormat("MM-dd");
			String day = df.format(date) + " " + getWhatDaySomeday(i);
			list.add(day);
		}
		return list;
	}
	
	public ArrayList<String> getAllTime(String type){
		
		long FtimeMillis = getFinalTime();
		long timeMillis = getNowTime();
		long hourMillis = 60*60*1000;
		if (timeMillis < getBegTime() | timeMillis > getFinalTime()-hourMillis) {
			timeMillis = getBegTime();
		}
		ArrayList<String> list = new ArrayList<String>();
		
//		System.out.println(timeMillis);
//		System.out.println(FtimeMillis);
		if(type.equals("s")) {
			for(long i = FtimeMillis-hourMillis; i >= timeMillis; i-=hourMillis ) {
				Date date = new Date(i);
				SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
				String day = df.format(date);
//				System.out.println(i);
				list.add(day);
			}
			return list;
		}
		else if(type.equals("e")) {
			for(long i = FtimeMillis; i >= timeMillis+hourMillis; i-=hourMillis ) {
				Date date = new Date(i);
				SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
				String day = df.format(date);
				list.add(day);
			}
			return list;
		}
		else if(type.equals("s1")) {
			for(long i = FtimeMillis-hourMillis; i >= getBegTime(); i-=hourMillis ) {
				Date date = new Date(i);
				SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
				String day = df.format(date);
//				System.out.println(i);
				list.add(day);
			}
			return list;
		}
		else if(type.equals("e1")) {
			for(long i = FtimeMillis; i >= getBegTime()+hourMillis; i-=hourMillis ) {
				Date date = new Date(i);
				SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
				String day = df.format(date);
				list.add(day);
			}
			return list;
		}
		return null;
	}
	
	public static long getFinalTime() {
		String now = "22:00:00";
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date time = new Date();
		try {
			time = format.parse(now);
			return time.getTime();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	public static long getBegTime() {
		String now = "08:00:00";//timeMillis = 0
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date time = new Date();
		try {
			time = format.parse(now);
			return time.getTime();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	//取得現在幾點幾分幾秒
	public static long getNowTime() {
		long timemillis = System.currentTimeMillis();
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		String now = format.format(new Date(timemillis));
		Date time = new Date();
		try {
			time = format.parse(now);
			return time.getTime();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public static String getDatetime(String md, String time) {
		long timemillis = System.currentTimeMillis();
		SimpleDateFormat format = new SimpleDateFormat("YYYY");
		String year = format.format(new Date(timemillis));
		return year+"-"+md+" "+time;
		
	}
	
	public static boolean compare(String s1, String s2) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date time1 = new Date();
		Date time2 = new Date();
		try {
			time1 = format.parse(s1);
			time2 = format.parse(s2);
			if(time1.getTime() >= time2.getTime()) {
				return true;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public static boolean compareDate(String s1, String s2) {
		SimpleDateFormat format = new SimpleDateFormat("MM-dd");
		Date time1 = new Date();
		Date time2 = new Date();
		try {
			time1 = format.parse(s1);
			time2 = format.parse(s2);
			if(time1.getTime() > time2.getTime()) {
				return true;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public static boolean isOverTime(String s1, String s2) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date time1 = new Date();
		Date time2 = new Date();
		try {
			time1 = format.parse(s1);
			time2 = format.parse(s2);
			long n = 60*60*1000*3;
			if((time2.getTime() - time1.getTime()) <= n) {
				return false;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public static boolean isTimeVio(String s) {
		long timemillis = System.currentTimeMillis();
		SimpleDateFormat format = new SimpleDateFormat("MM:dd HH:mm:ss");
		Date time1 = new Date();
		Date now = new Date();
		try {
			time1 = format.parse(s);
			now = format.parse(format.format(new Date(timemillis)));
			if(time1.getTime() < now.getTime()) {
				return true;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public static String get_now_time() {
		long timemillis = System.currentTimeMillis();

		// 转换日期显示格式

		SimpleDateFormat df = new SimpleDateFormat("MM-dd");

		return df.format(new Date(timemillis));
	}
	public static String get_now_moment() {
		long timemillis = System.currentTimeMillis();

		// 转换時刻显示格式

		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");

		return df.format(new Date(timemillis));
	}
	
}
