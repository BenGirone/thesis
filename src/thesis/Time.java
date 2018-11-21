package thesis;

public class Time {
	
	public static long current() {
		return System.currentTimeMillis() % 600000;
	}
	
	public Time() {
		// TODO Auto-generated constructor stub
	}

}
