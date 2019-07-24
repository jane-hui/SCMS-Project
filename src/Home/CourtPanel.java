package Home;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

/*
 * Court Status Panel Only
 */
public abstract class CourtPanel extends JPanel implements CaretListener, MouseListener, ActionListener {
	
	protected JFrame frame;
	protected int userID;
	protected JPanel pnlHeader, pnlCourtStatus, pnlTimeSlotDisBtns, legendAvailable, legendBooked;
	protected JLabel lblHeader, lblCourtStatus, lblAvailable, lblBooked;
	protected JSeparator HeaderSeparator; 
	protected DatePickerSettings dateSettings;
	protected DatePicker datePicker;
	protected JTextField TFDatePicker;
	protected JButton datePickerButton;
	protected HashMap<Integer, LinkedList<Integer>> courtTimeSlots;
	protected LinkedList<Integer> buttonData;
	protected JButtonOwn[][] courtButtons;
	protected final int TimeSlotNums = 14;
	protected String selectedCourt;
    protected String query;
    protected PreparedStatement preparedStmt;
    protected Connection con;
	protected SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
	protected Date date = new Date();
	protected int BKPeriod;

	public CourtPanel() {
		setBackground(new Color(249, 250, 244));
		setLayout(null);
	
		// Header Setup
		pnlHeader = new JPanel();
		pnlHeader.setAlignmentY(1.0f);
		pnlHeader.setBackground(new Color(114, 218, 221));
		pnlHeader.setBounds(0, 26, 729, 62);
		pnlHeader.setLayout(null);
		add(pnlHeader);
		
		lblHeader = new JLabel("Title");
		lblHeader.setSize(643, 31);
		lblHeader.setForeground(new Color(255, 255, 255));
		lblHeader.setHorizontalAlignment(SwingConstants.CENTER);
		lblHeader.setBounds(10, 11, 629, 31);
		lblHeader.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 22));
		lblHeader.setAlignmentY(1.0f);
		pnlHeader.add(lblHeader);
		
		HeaderSeparator = new JSeparator();
		HeaderSeparator.setBackground(new Color(255, 255, 255));
		HeaderSeparator.setForeground(new Color(255, 255, 255));
		HeaderSeparator.setBounds(187, 45, 269, 17);
		pnlHeader.add(HeaderSeparator);
		
		//Minimize & Exit Buttons Setup
		JLabel lblX = new JLabel("X");
		lblX.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);
			}
		});
		lblX.setHorizontalAlignment(SwingConstants.CENTER);
		lblX.setForeground(Color.BLUE);
		lblX.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblX.setBounds(623, 0, 20, 25);
		add(lblX);
		
		JLabel lblMinimize = new JLabel("-");
		lblMinimize.addMouseListener(this);
		lblMinimize.setHorizontalAlignment(SwingConstants.CENTER);
		lblMinimize.setForeground(Color.BLUE);
		lblMinimize.setFont(new Font("Segoe UI", Font.BOLD, 25));
		lblMinimize.setBounds(600, 0, 20, 25);
		add(lblMinimize);
		
	}
    

	/**
	 * Create the panel.
	 */
	public CourtPanel(JFrame frame, int userID) {
		
		this();
		this.frame = frame;
		this.userID = userID;

		// Court Status Setup
		pnlCourtStatus = new JPanel();
		pnlCourtStatus.setBounds(10, 87, 423, 680);
		pnlCourtStatus.setLayout(null);
		add(pnlCourtStatus);
				
		lblCourtStatus = new JLabel("Court Selection");
		lblCourtStatus.setSize(382, 28);
		lblCourtStatus.setVerticalAlignment(SwingConstants.BOTTOM);
		lblCourtStatus.setLocation(20, 11);
		lblCourtStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblCourtStatus.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblCourtStatus.setForeground(StaffGUI.darkBlue);
		pnlCourtStatus.add(lblCourtStatus);
		
		dateSettings = new DatePickerSettings();
	    int newHeight = (int) (dateSettings.getSizeDatePanelMinimumHeight() * 1.6);
	    int newWidth = (int) (dateSettings.getSizeDatePanelMinimumWidth() * 1.6);
	    dateSettings.setSizeDatePanelMinimumHeight(newHeight);
	    dateSettings.setSizeDatePanelMinimumWidth(newWidth);
	    dateSettings.setAllowKeyboardEditing(false);
	    dateSettings.setFormatForDatesCommonEra("yyyy-MM-dd");
	    dateSettings.setFormatForDatesBeforeCommonEra("uuuu-MM-dd");
	                
		datePicker = new DatePicker(dateSettings);	
	    datePicker.getComponentToggleCalendarButton().setBorderPainted(false); 
	    datePicker.getComponentToggleCalendarButton().setBorder(null);
	    datePicker.getComponentDateTextField().setBounds(new Rectangle(0, 0, 0, 30));
	    datePicker.setSize(200, 30);
	    datePicker.setLocation(110, 50);
	    datePicker.setDateToToday();
	    datePickerButton = datePicker.getComponentToggleCalendarButton();
	    datePickerButton.setText("");
	    datePickerButton.setIcon(new ImageIcon(StaffGUI.class.getResource("/Home/Images/icons8_calendar_plus_30px.png")));      
		TFDatePicker = datePicker.getComponentDateTextField();
		TFDatePicker.addCaretListener(this);
		pnlCourtStatus.add(datePicker);		
		
		pnlTimeSlotDisBtns = new JPanel();
		pnlTimeSlotDisBtns.setBounds(10, 78, 403, 558);
		pnlTimeSlotDisBtns.setLayout(new GridLayout(15, 3, 5, 5));

		JLabel[] courtButtonLabels = new JLabel[3];				
		for(int i=0; i < courtButtonLabels.length; i++) {
			courtButtonLabels[i] = new JLabel("Court " + (i+1));
			courtButtonLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
			courtButtonLabels[i].setHorizontalTextPosition(SwingConstants.CENTER);
			courtButtonLabels[i].setFont(new Font("Segoe UI", Font.BOLD, 14));
			courtButtonLabels[i].setForeground(new Color(104, 134, 160));
			courtButtonLabels[i].setVerticalAlignment(SwingConstants.BOTTOM);
			pnlTimeSlotDisBtns.add(courtButtonLabels[i]);				
		} 

		courtTimeSlots = new HashMap<>();
		buttonData = new LinkedList<>();
		courtButtons = new JButtonOwn[TimeSlotNums][3];
		
		//Time Rows
		for (int i = 0; i < courtButtons.length; i++) {			
			//Court Columns
			for(int j = 0; j<courtButtons[i].length; j++) {
									
				courtTimeSlots.put(j+1, new LinkedList<Integer>()); 
					
				courtButtons[i][j] = new JButtonOwn((j+1), (i+8), (i+9));
				courtButtons[i][j].setBackground(new Color(173, 255, 47));		
				courtButtons[i][j].setFocusPainted(false);				
				courtButtons[i][j].addActionListener(this);
				pnlTimeSlotDisBtns.add(courtButtons[i][j]);				
			}
		}			
		pnlCourtStatus.add(pnlTimeSlotDisBtns);	
		
		legendAvailable = new JPanel();
		legendAvailable.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		legendAvailable.setBackground(new Color(173, 255, 47));
		legendAvailable.setBounds(20, 643, 60, 24);
		pnlCourtStatus.add(legendAvailable);
		legendAvailable.setLayout(new BorderLayout(0, 0));
		
		lblAvailable = new JLabel("Available");
		lblAvailable.setHorizontalAlignment(SwingConstants.CENTER);
		legendAvailable.add(lblAvailable, BorderLayout.CENTER);
		
		legendBooked = new JPanel();
		legendBooked.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		legendBooked.setBackground(new Color(143, 20, 3));
		legendBooked.setBounds(100, 643, 60, 24);
		pnlCourtStatus.add(legendBooked);
		legendBooked.setLayout(new BorderLayout(0, 0));
		
		lblBooked = new JLabel("Booked");
		lblBooked.setHorizontalAlignment(SwingConstants.CENTER);
		lblBooked.setForeground(Color.LIGHT_GRAY);
		legendBooked.add(lblBooked, BorderLayout.CENTER);
		
	}	
	
	
	public void resetCourtOutput(boolean setEnable) {

		for(JButtonOwn[] btn_i : courtButtons) {
			for(JButtonOwn btn : btn_i) {
				btn.setBackground(new Color(173, 255, 47));
				btn.setSelected(false);    
				btn.setEnabled(setEnable);
				btn.setText("<html><center>Court " + btn.getCourt() + "<br>"+ btn.getTimeSlotStr() + "</center></html>");
			}
		}
		selectedCourt = "";
		BKPeriod = 0;
		for(int key : courtTimeSlots.keySet()) {
			  courtTimeSlots.put(key, new LinkedList<Integer>());
			}
		buttonData.clear();
	}
	
	public Booking printSelectedCourt() {
		
		selectedCourt = "";
		BKPeriod = 0;
		
		for(int court : courtTimeSlots.keySet()) {
			LinkedList<Integer> list = courtTimeSlots.get(court);

			if(!list.isEmpty()) {
				
		        boolean start = false, skip = false;
				HashSet<Integer> skipNum = new HashSet<>();	
								
				for(int i=0; i<list.size(); i++) {					
					int time = (int)list.get(i);
					
					//skip the number if it occurs two times
		         	 if((i<list.size()-1 && list.get(i) == list.get(i+1))  || skipNum.contains(time)) {
		         		 skipNum.add(time);		
		         		 i++;
		         		 skip = true;
		         	 }	
		         	 else {
		         		 skip = false;
		         	 }
		 
		         	 //mark the number as start time
		         	 if(!skip && !start) {
		         		 selectedCourt += "Court " + court + String.format(" (%02d:00", time) + " - ";
		         		 start = true;
		         		 BKPeriod += time;
		         	 }
		         	 //mark the number as end time
		         	 else if (!skip && start){
		         		 selectedCourt += String.format("%02d:00", time) + ")\n";
		         		 start = false;
		         		 BKPeriod -= time;
		         	 }
		          }
				} 			
			}
        return new Booking(selectedCourt, Math.abs(BKPeriod));
	}

	public void loadCourtStatusSQL(String BK_Date) {
		
		con = SQLConn.getConSetup();		
		try {			
			query = "SELECT TIME_DATA, BK_ID,  CUST_NAME FROM BOOKINGS WHERE BK_DATE = ?";			
			preparedStmt = con.prepareStatement(query);  
			preparedStmt.setString(1, BK_Date);
			ResultSet rs = preparedStmt.executeQuery();
			
			while(rs.next()) {
				
				String timeSlotBooked = rs.getString(1).substring(1, rs.getString(1).length()-1);								
				String[] str = timeSlotBooked.split(", ");

				for(int k=0; k<str.length; k++) {
					int j = Integer.parseInt(str[k].substring(0, 1)) - 1; //court
					int i = Integer.parseInt(str[k].substring(1)) - 8;    //time
					
					//mark specific button as booked
					JButtonOwn btn = courtButtons[i][j];
					btn.setBackground(new Color(143, 20, 3));
					btn.setEnabled(false);
					btn.setText("<html><center>[" + rs.getInt(2) + "] " + rs.getString(3) + "<br>C" + btn.getCourt() + " : "+ btn.getTimeSlotStr() + "</center></html>");
				}					
			}		
			rs.close(); 
			preparedStmt.close();
			con.close();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(CourtPanel.this, "Court status couldn't be loaded. Please try again.");
		}  		
	}
	
	@Override
	public abstract void caretUpdate(CaretEvent e);	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		frame.setState(1);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() instanceof JButtonOwn) {			
			 JButtonOwn btn = (JButtonOwn) e.getSource();
	         LinkedList list = courtTimeSlots.get(btn.getCourt());  		
		     
		     if(!btn.isSelected()) {
		    	 btn.setBackground(Color.ORANGE);
				 btn.setSelected(true);
		         list.add(btn.getStartTime());
		         list.add(btn.getEndTime());
				 buttonData.add(btn.getData());
		         if(list.size() > 2) {
		        	 Collections.sort(list);
		         }
		         selectedCourt = printSelectedCourt().getOutput();
		         BKPeriod = printSelectedCourt().getPeriod();
		     }
		     
		     else if(btn.isSelected() && btn.getBookedStatus()) {   		    	 
				 btn.setBookedStatus(false);
		    	 btn.setBackground(new Color(173, 255, 47));
				 btn.setSelected(false);     
		         list.remove(list.indexOf(btn.getStartTime()));
		         list.remove(list.indexOf(btn.getEndTime()));
		         buttonData.remove(buttonData.indexOf(btn.getData()));
		         selectedCourt = printSelectedCourt().getOutput();
		         BKPeriod = printSelectedCourt().getPeriod();
		     } 
		     
		     else if(btn.isSelected() && !btn.getBookedStatus()){ 		     
		    	 btn.setBackground(new Color(173, 255, 47));
				 btn.setSelected(false);     	
		         list.remove(list.indexOf(btn.getStartTime()));
		         list.remove(list.indexOf(btn.getEndTime()));
		         buttonData.remove(buttonData.indexOf(btn.getData()));
		         selectedCourt = printSelectedCourt().getOutput();
		         BKPeriod = printSelectedCourt().getPeriod();
		     }  
		}		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
