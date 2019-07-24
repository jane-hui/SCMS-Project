package Home;

import javax.swing.JButton;

/*
 * JButton Own Class
 */
public class JButtonOwn extends JButton implements Comparable<JButtonOwn> {
	
	private int court, startTime, endTime;
	private String startTime2D, endTime2D;
	private boolean booked;
	
	JButtonOwn(int court, int startTime ,int endTime) {
		super();
		this.court = court;
		this.startTime = startTime;
		this.endTime = endTime;
		this.booked = false;
	
		startTime2D = String.format("%02d:00", startTime);
		endTime2D = String.format("%02d:00", endTime);		
		setText("<html><center>Court " + court + "<br>"+ startTime2D + " - " + endTime2D + " </center></html>");
	}
		
	public int getCourt() {
		return court;	
	}
	
	public int getStartTime() {
		return startTime;
	}
	
	public int getEndTime() {
		return endTime;
	}
	
	public String getStartTimeStr() {
		return startTime2D;
	}
	
	public String getEndTimeStr() {
		return endTime2D;
	}
	
	public String getTimeSlotStr() {
		return (startTime2D + " - " + endTime2D);
	}
	
	public int getData() {
		return (court*100) + startTime;
	}
	
	public boolean getBookedStatus() {
		return booked;
	}
	
	
	public void setCourt(int court) {
		this.court = court;
	}
	
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	
	public void setBookedStatus(boolean booked) {
		this.booked = booked;
	}
	public String toString() {
		return "Court " + court + " (" + startTime2D + " - " + endTime2D + ")";
	}

	@Override
	public int compareTo(JButtonOwn o) {
		// TODO Auto-generated method stub
		return this.getData() - o.getData();
	}

}
