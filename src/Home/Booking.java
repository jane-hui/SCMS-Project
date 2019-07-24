package Home;

/*
 * A booking class consists of selected court string and booking period time slot.
 */
public class Booking {
	
	private String output;
	private int period;

	public Booking(String output, int period) {
		this.output = output;
		this.period = period;
	}
	
	public String getOutput() {
		return this.output;
	}
	
	public int getPeriod() {
		return this.period;
	}
	
	public void setOutput(String output) {
		this.output = output;
	}
	
	public void setPeriod(int period) {
		this.period = period;
	}
}
