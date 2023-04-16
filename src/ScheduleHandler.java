import java.util.ArrayList;

public class ScheduleHandler {
	private ArrayList<Schedule> schedule_list;
	private String[] new_schedule_list;

	public ScheduleHandler() {
		schedule_list = new ArrayList<Schedule>();
		new_schedule_list = new String[14];
		for (int i = 0; i < 14; i++) {
			new_schedule_list[i] = "       -";
		}
	}

	public void addSchedule(Schedule s) {
		schedule_list.add(s);
	}

	public void transformSchedule() {
		for (Schedule s : schedule_list) {
			for (int i = 0; i < s.getDuration(); i++) {
				if(s.getStartHour()<=8) {
					new_schedule_list[s.getStartHour()  + i] = s.getTeam();
				}else {
					new_schedule_list[s.getStartHour()-8  + i] = s.getTeam();
				}
				
			}
		}
	}

	public String[] getNewScheduleList() {
		return new_schedule_list;
	}

}
