import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Schedule {
	private String team;
	private Date start_time;
	private Date end_time;
	private int duration;
	private int start_hour;

	@SuppressWarnings("deprecation")
	public Schedule(String team, String start_time, String end_time) {
		this.team = team;
		this.start_time = StringToDateTime(start_time);
		this.end_time = StringToDateTime(end_time);
		duration = (this.end_time.getHours() - this.start_time.getHours());
		start_hour = this.start_time.getHours();

	}

	public String getTeam() {
		return team;
	}

	public int getStartHour() {
		return start_hour;
	}

	public int getDuration() {
		return duration;
	}

	public Date StringToDateTime(String date_time) {
		Date date = null;

		SimpleDateFormat dateParser = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		{
			try {
				date = dateParser.parse(date_time);
//	                System.out.println(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}

}
