package Home;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.*;

/*
 * Booking Search Panel Only >> SearchPanel(), using method1
 * Booking Search Panel integrated with Court Status Panel >> SearchPanel(frame, userID), using method2
 */
public class SearchPanel extends CourtPanel implements ActionListener, KeyListener {
	
	protected JPanel pnlSearch, pnlSearchCriteria, pnlSearchOutput, pnlThis;
	protected JTextField TFSearch, TFCustName, TFCTNo, TFBKDate;
	protected JRadioButton RBBKID, RBCustName, RBCTNo;
	protected ButtonGroup RBGroup;
	protected JLabel lblSearch, lblSearchCriteria, lblCheckInput, lblThis, lblSearchResult, lblDate, lblSelectCourt, lblCustName, lblContactNumber, lblBookingPeriod;
	protected JList<String> listBKDate;
	protected DefaultListModel<String> listDLM;
	protected boolean searched;
	protected String BK_Date = "2019", tsBooked;
	protected String[] timeSlotBooked;
	protected int BK_ID;
	protected ArrayList<String> custNames, contactNos, BKDates, timeDatas, timeDetails;
	protected ArrayList<Integer> BKIDs, BKPeriods;
	protected JTextPane textPaneCourtOutput;
	protected JCheckBox chckbxName, chckbxContact;
	protected JSeparator separatorSearch;
	protected JButton btnReset, btnSearch, btnCancel, btnSubmit;
	protected JScrollPane scrollPaneBKDate, scrollPaneCourtOutput;
	protected JTextField TFBKPeriod;


	/**
	 * Create the panel.
	 */
	public SearchPanel() {		
		
		super();	
		
		//Booking Search Setup
		pnlSearch = new JPanel();
		pnlSearch.setBounds(10, 87, 217, 680);	
		setupPnlSearch();
		RBCustName.setVisible(false);
		RBCTNo.setVisible(false);
		
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searched = false;
				resetAll1();	
			}
		});	
		TFSearch.addKeyListener(this);		
		btnSearch.addActionListener(this);
		//Search Output Setup
		setupPnlSearchOutput1();	
	}  
	
	//Integrated with superclass Court Panel
	public SearchPanel(JFrame frame, int userID) {
				
		super(frame, userID);
		searched = false;
		resetCourtOutput(searched);
		
		loadCourtStatusSQL(datePicker.getText());
			
		//Court Status Setup
		pnlThis = new JPanel();
		pnlThis.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		pnlThis.setBackground(Color.WHITE);
		pnlThis.setBounds(180, 643, 160, 26);
		pnlCourtStatus.add(pnlThis);
		pnlThis.setLayout(new BorderLayout(0, 0));
		
		lblThis = new JLabel("Booking for this customer");
		lblThis.setHorizontalAlignment(SwingConstants.CENTER);
		lblThis.setAlignmentY(0.0f);
		pnlThis.add(lblThis, BorderLayout.CENTER);
		
		//Booking Search Setup
		pnlSearch = new JPanel();
		pnlSearch.setBounds(433, 87, 217, 680);	
		setupPnlSearch();

		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searched = false;
				resetAll2();	
			}
		});
		
		TFSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER){
					searchMethod2();
				}
			}
		});
		
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchMethod2();							
				}			
		});
		
		//Search Output Setup
		setupPnlSearchOutput2();
		
	}
	
	protected void setupPnlSearch() {
		
		pnlSearchCriteria = new JPanel();			
		lblSearch = new JLabel("Booking Search");
		lblSearchCriteria = new JLabel("Search by :");
		RBBKID = new JRadioButton("ID", true);
		RBCustName = new JRadioButton("Name");
		RBCTNo = new JRadioButton("Contact");
		RBGroup = new ButtonGroup();
		TFSearch = new JTextField();
		lblCheckInput = new JLabel("");			
		btnReset = new JButton("Reset");
		btnSearch = new JButton("Search");
		separatorSearch = new JSeparator();
		
		
		pnlSearch.setLayout(null);
		pnlSearch.setBackground(new Color(249, 250, 244));
		add(pnlSearch);
			

		pnlSearchCriteria.setBounds(10, 0, 197, 200);		
		pnlSearchCriteria.setBackground(new Color(249, 250, 244));
		pnlSearchCriteria.setBorder(null);
		pnlSearchCriteria.setLayout(null);
		pnlSearch.add(pnlSearchCriteria);	
		
		
		lblSearch.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearch.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblSearch.setForeground(StaffGUI.darkBlue);
		lblSearch.setBounds(15, 16, 168, 17);
		pnlSearchCriteria.add(lblSearch);
		

		lblSearchCriteria.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblSearchCriteria.setBounds(15, 44, 172, 14);
		pnlSearchCriteria.add(lblSearchCriteria);
		

		RBBKID.setBounds(11, 65, 42, 23);
		RBBKID.setBackground(new Color(249, 250, 244));
		pnlSearchCriteria.add(RBBKID);
		
	
		RBCustName.setBackground(new Color(249, 250, 244));
		RBCustName.setBounds(55, 65, 62, 23);
		pnlSearchCriteria.add(RBCustName);		
		RBCTNo.setBackground(new Color(249, 250, 244));
		RBCTNo.setBounds(116, 65, 72, 23);
		pnlSearchCriteria.add(RBCTNo);	
		

		RBGroup.add(RBBKID);
		RBGroup.add(RBCustName);
		RBGroup.add(RBCTNo);
			
					

		TFSearch.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				try {
					if(RBBKID.isSelected() && TFSearch.getText().length() > 0) {
						int i = Integer.parseInt(TFSearch.getText());
						lblCheckInput.setText("");
					}
					
					else if(RBCTNo.isSelected() && TFSearch.getText().length() > 0) {
						lblCheckInput.setText("Format : ###-#######");
					}
					
					else {
						lblCheckInput.setText("");	
					}
										
				} catch (NumberFormatException e1) {
					lblCheckInput.setText("Invalid number");
				}
			}
		});
		

		TFSearch.addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent event) {
		        SwingUtilities.invokeLater(new Runnable() {
		            @Override
		            public void run() {
		                TFSearch.requestFocusInWindow();
		            }
		        });
			}
			public void ancestorMoved(AncestorEvent event) {
			}
			public void ancestorRemoved(AncestorEvent event) {
			}
		}); 
		TFSearch.isRequestFocusEnabled();
		TFSearch.setColumns(10);
		TFSearch.setBounds(15, 97, 168, 30);
		pnlSearchCriteria.add(TFSearch);
		

		lblCheckInput.setForeground(Color.RED);
		lblCheckInput.setBounds(15, 130, 168, 14);
		pnlSearchCriteria.add(lblCheckInput);

		
		btnReset.setForeground(new Color(17, 29, 127));
		btnReset.setFocusPainted(false);
		btnReset.setBounds(0, 155, 89, 33);
		pnlSearchCriteria.add(btnReset);	
				

		btnSearch.setBounds(108, 155, 89, 33);
		btnSearch.setBackground(StaffGUI.darkBlue);
		btnSearch.setForeground(Color.WHITE);
		btnSearch.setFocusPainted(false);	
		pnlSearchCriteria.add(btnSearch);
		
		separatorSearch.setBounds(10, 208, 197, 12);
		separatorSearch.setForeground(new Color(104, 134, 160));
		pnlSearch.add(separatorSearch);
	}
 	
	protected void setupPnlSearchOutput() {
		
		pnlSearchOutput = new JPanel();
		lblSearchResult = new JLabel("Search Result");
		lblDate = new JLabel("Booking Date :");
		lblSelectCourt = new JLabel("Selected Court : ");
		textPaneCourtOutput = new JTextPane();
		scrollPaneCourtOutput = new JScrollPane();
		TFCustName = new JTextField();
		TFCTNo = new JTextField();
        listDLM = new DefaultListModel<>(); 
		TFBKPeriod = new JTextField();
		
		pnlSearchOutput.setBounds(10, 217, 197, 463);
		pnlSearchOutput.setLayout(null);
		pnlSearchOutput.setBorder(null);
		pnlSearchOutput.setBackground(new Color(249, 250, 244));
		pnlSearch.add(pnlSearchOutput);      
		
		lblSearchResult.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearchResult.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblSearchResult.setBounds(15, 11, 168, 17);
		lblSearchResult.setForeground(StaffGUI.darkBlue);
		pnlSearchOutput.add(lblSearchResult);		

		
		TFCustName.setEditable(false);
		TFCustName.setColumns(10);
		TFCustName.setBounds(15, 65, 168, 28);
		pnlSearchOutput.add(TFCustName);
		
		TFCTNo.setEditable(false);
		TFCTNo.setColumns(10);
		TFCTNo.setBounds(15, 125, 168, 28);
		pnlSearchOutput.add(TFCTNo);		
		
		lblDate = new JLabel("Booking Date :");
		lblDate.setBounds(15, 164, 168, 14);
		lblDate.setFont(new Font("Segoe UI", Font.BOLD, 12));
		pnlSearchOutput.add(lblDate);
		
		lblBookingPeriod = new JLabel("Booking Period :");
		lblBookingPeriod.setBounds(15, 365, 128, 14);
		lblBookingPeriod.setFont(new Font("Segoe UI", Font.BOLD, 12));
		pnlSearchOutput.add(lblBookingPeriod);
		
		TFBKPeriod.setEditable(false);
		TFBKPeriod.setColumns(10);
		TFBKPeriod.setBounds(15, 388, 168, 28);
		pnlSearchOutput.add(TFBKPeriod);
			
	}
	
	protected void setupPnlSearchOutput1() {
			
		setupPnlSearchOutput();
		
		lblCustName = new JLabel("Customer Name :");
		lblCustName.setBounds(15, 42, 168, 14);
		pnlSearchOutput.add(lblCustName);
		lblCustName.setFont(new Font("Segoe UI", Font.BOLD, 12));
		
		lblContactNumber = new JLabel("Contact Number :");
		lblContactNumber.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblContactNumber.setBounds(15, 102, 168, 14);
		pnlSearchOutput.add(lblContactNumber);
	
		TFBKDate = new JTextField();
		TFBKDate.setBounds(15, 187, 168, 28);
		pnlSearchOutput.add(TFBKDate);
		TFBKDate.setEditable(false);
		TFBKDate.setColumns(10);
		
		lblSelectCourt.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblSelectCourt.setBounds(15, 228, 168, 14);
		pnlSearchOutput.add(lblSelectCourt);
		
		textPaneCourtOutput.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textPaneCourtOutput.setFont(new Font("Segoe UI", Font.BOLD, 14));
		textPaneCourtOutput.setForeground(new Color(232, 17, 35));
		textPaneCourtOutput.setEditable(false);	
		
		scrollPaneCourtOutput.setBounds(15, 251, 168, 103);
		pnlSearchOutput.add(scrollPaneCourtOutput);
		scrollPaneCourtOutput.setViewportView(textPaneCourtOutput);				
	}
		
	protected void setupPnlSearchOutput2() {
		
		setupPnlSearchOutput();
				
		chckbxName = new JCheckBox("Customer Name :");
		chckbxContact = new JCheckBox("Contact Number :");
		btnCancel = new JButton("<html><center>Cancel<br>Booking</center></html>");
		btnSubmit = new JButton("Submit");
		listBKDate = new JList<>(listDLM);
		scrollPaneBKDate = new JScrollPane();
		
		chckbxName.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					TFCustName.setEditable(searched);
				}
				else {
					TFCustName.setEditable(false);
				}
			}
		});
		chckbxName.setFont(new Font("Segoe UI", Font.BOLD, 12));
		chckbxName.setBackground(new Color(249, 250, 244));
		chckbxName.setBounds(15, 42, 168, 23);
		pnlSearchOutput.add(chckbxName);
		

		chckbxContact.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					TFCTNo.setEditable(searched);
				}
				else {
					TFCTNo.setEditable(false);
				}
			}
		});
		chckbxContact.setFont(new Font("Segoe UI", Font.BOLD, 12));
		chckbxContact.setBackground(new Color(249, 250, 244));
		chckbxContact.setBounds(15, 102, 168, 23);
		pnlSearchOutput.add(chckbxContact);
		
		listBKDate.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(listBKDate.getSelectedIndex() > -1) {
					TFCustName.setText(custNames.get(listBKDate.getSelectedIndex()));
					TFCTNo.setText(contactNos.get(listBKDate.getSelectedIndex()));			
					BK_Date = BKDates.get(listBKDate.getSelectedIndex());			 
					tsBooked = timeDatas.get(listBKDate.getSelectedIndex()).substring(1, timeDatas.get(listBKDate.getSelectedIndex()).length()-1);
					timeSlotBooked = tsBooked.split(", ");
					BK_ID = BKIDs.get(listBKDate.getSelectedIndex());
					
					datePicker.setDate(LocalDate.parse(BK_Date));			 
					resetCourtOutput(searched);
					loadCourtStatusSQL(BK_Date);
					markButtonsSpecificBooking(timeSlotBooked);
					textPaneCourtOutput.setText(timeDetails.get(listBKDate.getSelectedIndex()));
					
				}
			}
		});
		listBKDate.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		listBKDate.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPaneBKDate.setViewportView(listBKDate);
		scrollPaneBKDate.setBounds(15, 187, 168, 60);
		pnlSearchOutput.add(scrollPaneBKDate);
		
		lblSelectCourt.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblSelectCourt.setBounds(15, 258, 168, 14);
		pnlSearchOutput.add(lblSelectCourt);
		
		textPaneCourtOutput.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textPaneCourtOutput.setFont(new Font("Segoe UI", Font.BOLD, 13));
		textPaneCourtOutput.setForeground(new Color(232, 17, 35));
		textPaneCourtOutput.setEditable(false);	
		
		scrollPaneCourtOutput.setBounds(15, 280, 168, 74);
		pnlSearchOutput.add(scrollPaneCourtOutput);
		scrollPaneCourtOutput.setViewportView(textPaneCourtOutput);
		

		btnCancel.setBounds(0, 430, 89, 33);
		pnlSearchOutput.add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int option = JOptionPane.showConfirmDialog(SearchPanel.this, "Do you like to CANCEL this booking as shown below?\n\nBooking ID : " + BK_ID + "\n"+ TFCustName.getText() + " [" + TFCTNo.getText() + "]\n" + datePicker.getText() + "\n" + selectedCourt + "<" + BKPeriod + " slots>");
				
				if(option == 0) { //Yes
					deleteDataSQL();
					searched=false;
					resetAll2();
				}
			}		
		});
		btnCancel.setForeground(StaffGUI.darkBlue);
		btnCancel.setFocusPainted(false);
		
		
		btnSubmit.setBounds(108, 430, 89, 33);
		pnlSearchOutput.add(btnSubmit);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				if (textPaneCourtOutput.getText().isEmpty()) {
					JOptionPane.showMessageDialog(SearchPanel.this, "No court selected.");
				}						
				else {
					int option = JOptionPane.showConfirmDialog(SearchPanel.this, "Do you like to UPDATE this booking as shown below?\n\nBooking ID : " + BK_ID + "\n"+ TFCustName.getText() + " [" + TFCTNo.getText() + "]\n" + datePicker.getText() + "\n" + selectedCourt + "<" + BKPeriod + " slots>");				
					if(option == 0) { //Yes
						updateDataSQL();
						searched=false;
						resetAll2();
					}
				}
			} 
		});
		btnSubmit.setBackground(StaffGUI.darkBlue);
		btnSubmit.setForeground(Color.WHITE);
		btnSubmit.setFocusPainted(false);			
	}
	
	
	protected void searchMethod2() {
		if(TFSearch.getText().isEmpty()) {
			JOptionPane.showMessageDialog(SearchPanel.this, "Search keyword cannot be empty");				
	}			
		else {
			try {
				if(RBBKID.isSelected()) {
					searched = checkSQL2(1, Integer.parseInt(TFSearch.getText()), "");
					if(!searched) {
						JOptionPane.showMessageDialog(SearchPanel.this, "Invalid ID : This booking is cancelled / is not existed");
						resetAll2();
					}
				}
				
				else if(RBCustName.isSelected()) {
					searched = checkSQL2(2, 0, TFSearch.getText());
					if(!searched) {
						JOptionPane.showMessageDialog(SearchPanel.this, "Invalid name : This customer cancelled / did not book any bookings");
						resetAll2();
					}
				}
				
				else if(RBCTNo.isSelected()) {
					searched = checkSQL2(3, 0, TFSearch.getText());
					if(!searched) {
						JOptionPane.showMessageDialog(SearchPanel.this, "Invalid contact no : This customer cancelled / did not book any bookings");
						resetAll2();
					}
				}
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(SearchPanel.this, "Invalid number");
				resetAll2();
			}
		}
	}
	
	protected void resetAll1() {
			
			TFSearch.setText("");
			TFCustName.setText("");
			TFCTNo.setText("");
			TFBKDate.setText("");
			textPaneCourtOutput.setText("");
			BK_ID = 0;
			TFBKPeriod.setText("");
			BKPeriod = 0;			
	}
	
	protected void resetAll2() {
		
		TFSearch.setText("");
		textPaneCourtOutput.setText("");
		TFBKPeriod.setText("");
		selectedCourt = "";
		BKPeriod = 0;
		for(int key : courtTimeSlots.keySet()) {
			  courtTimeSlots.put(key, new LinkedList<Integer>());
			}
		buttonData.clear();
		datePicker.setDateToToday();
		resetCourtOutput(false);
		loadCourtStatusSQL(datePicker.getText()); 
		listDLM.clear();
		TFCustName.setText("");
		TFCTNo.setText("");
		if(chckbxName.isSelected()) {
			chckbxName.setSelected(false);
		}
		if(chckbxContact.isSelected()) {
			chckbxContact.setSelected(false);
		}
		BK_ID = 0;
}
	

	protected boolean checkSQL(int sel, int BKID, String searchStr) {
		
		boolean found = false;
		ResultSet rs = null;
		custNames = new ArrayList<>();
		contactNos = new ArrayList<>();
		BKDates = new ArrayList<>();
		timeDatas = new ArrayList<>();
		timeDetails = new ArrayList<>();
		BKIDs = new ArrayList<>();
        listDLM.clear(); 
        BKPeriods = new ArrayList<>();
        
        con = SQLConn.getConSetup();		
		try {
			
			if(sel == 1) {			
				query = "SELECT CUST_NAME, CUST_CTNO, BK_DATE, TIME_DATA, TIME_DETAIL, BK_PERIOD, BK_ID FROM BOOKINGS WHERE BK_ID = ?";			
				preparedStmt = con.prepareStatement(query);  
				preparedStmt.setInt(1, BKID);
				rs = preparedStmt.executeQuery();
			}
			
			else if(sel == 2) {
				query = "SELECT CUST_NAME, CUST_CTNO, BK_DATE, TIME_DATA, TIME_DETAIL, BK_PERIOD, BK_ID FROM BOOKINGS WHERE CUST_NAME LIKE ? ORDER BY BK_DATE DESC LIMIT 5";
				preparedStmt = con.prepareStatement(query);  
				preparedStmt.setString(1, "%" + searchStr + "%");
				rs = preparedStmt.executeQuery();
			}
			else if(sel == 3) {
				query = "SELECT CUST_NAME, CUST_CTNO, BK_DATE, TIME_DATA, TIME_DETAIL, BK_PERIOD, BK_ID FROM BOOKINGS WHERE CUST_CTNO LIKE ? ORDER BY BK_DATE DESC LIMIT 5";
				preparedStmt = con.prepareStatement(query);  
				preparedStmt.setString(1, "%" + searchStr + "%");
				rs = preparedStmt.executeQuery();
			}
			
			while(rs.next()) {	
				
				found = true;
				custNames.add(rs.getString(1));
				contactNos.add(rs.getString(2));
				BKDates.add(rs.getString(3));	
		        listDLM.addElement(rs.getString(3));
				timeDatas.add(rs.getString(4));		
				timeDetails.add(rs.getString(5));
				BKPeriods.add(rs.getInt(6));
				BKIDs.add(rs.getInt(7));			
			}

			rs.close(); 
			preparedStmt.close();
			con.close();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(SearchPanel.this, "Booking couldn't be checked. Please try again.");
		}	
		return found;
	}

	protected boolean checkSQL1(int sel, int BKID) {
		
			boolean found = checkSQL(sel, BKID, "");

			if(found) {
				TFCustName.setText(custNames.get(0));
				TFCTNo.setText(contactNos.get(0));			
				BK_Date = BKDates.get(0);			 
				tsBooked = timeDatas.get(0).substring(1, timeDatas.get(0).length()-1);	
				timeSlotBooked = tsBooked.split(", ");
				
				TFBKDate.setText(BK_Date);
				textPaneCourtOutput.setText(timeDetails.get(0));
				BKPeriod = BKPeriods.get(0);
				TFBKPeriod.setText(String.valueOf(BKPeriod) + " slots");
				BK_ID = BKIDs.get(0);
			}
	return found;
	}
	
	protected boolean checkSQL2(int sel, int BKID, String searchStr) {
		
		boolean found = checkSQL(sel, BKID, searchStr);

		if(found) {
			TFCustName.setText(custNames.get(0));
			TFCTNo.setText(contactNos.get(0));			
			BK_Date = BKDates.get(0);			 
			tsBooked = timeDatas.get(0).substring(1, timeDatas.get(0).length()-1);	
			timeSlotBooked = tsBooked.split(", ");
			
	        listBKDate.setSelectedValue(BK_Date, true);
			datePicker.setDate(LocalDate.parse(BK_Date));		
			resetCourtOutput(found);
			loadCourtStatusSQL(BK_Date);
			markButtonsSpecificBooking(timeSlotBooked);
			textPaneCourtOutput.setText(timeDetails.get(0));
			BKPeriod = BKPeriods.get(0);
			TFBKPeriod.setText(String.valueOf(BKPeriod) + " slots");
			BK_ID = BKIDs.get(0);
		}
		return found;
	}

		private void deleteDataSQL() {
			
			con = SQLConn.getConSetup();
			
			try {
				query = "DELETE FROM BOOKINGS WHERE BK_ID = ?";	
				preparedStmt = con.prepareStatement(query);
				preparedStmt.setInt(1, BK_ID);
				preparedStmt.execute();
				
				JOptionPane.showMessageDialog(SearchPanel.this, "Booking has been deleted / cancelled successfully.");
				preparedStmt.close();
				con.close();	 	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(SearchPanel.this, "Booking couldn't be deleted. Please try again.");
			} 	 		
	}
	
	
	private void updateDataSQL() {
		
		Collections.sort(buttonData);
		con = SQLConn.getConSetup();
		try {
			query = "UPDATE BOOKINGS SET CUST_NAME = ?, CUST_CTNO = ?, BK_DATE = ?, TIME_DATA = ?, TIME_DETAIL = ?, BK_PERIOD = ?, US_ID = ?, DATE_MODIFIED = ? WHERE BK_ID = ?";
			preparedStmt = con.prepareStatement(query);	
			preparedStmt.setString(1, TFCustName.getText());
			preparedStmt.setString(2, TFCTNo.getText());
			preparedStmt.setString(3, datePicker.getText());
			preparedStmt.setString(4, buttonData.toString());
			preparedStmt.setString(5, selectedCourt);
			preparedStmt.setInt(6, BKPeriod);
			preparedStmt.setInt(7, userID);
			preparedStmt.setString(8, ft.format(date));
			preparedStmt.setInt(9, BK_ID);
			preparedStmt.execute();
			
			JOptionPane.showMessageDialog(SearchPanel.this, "Booking has been changed successfully.");
			preparedStmt.close();
			con.close();	 	
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(SearchPanel.this, "Booking couldn't be updated. Please try again.");
		}  		
		
	}
	
	
	protected void markButtonsSpecificBooking(String[] str) { 
				
		for(int k=0; k<str.length; k++) {
			int j = Integer.parseInt(str[k].substring(0, 1)) - 1; //court
			int i = Integer.parseInt(str[k].substring(1)) - 8;    //time
			
			JButtonOwn btn = courtButtons[i][j];
			btn.setBookedStatus(true);
			btn.setSelected(true);
			btn.setBackground(Color.WHITE);
			btn.setEnabled(true);
			
			LinkedList list = courtTimeSlots.get(btn.getCourt()); 	
			list.add(btn.getStartTime());
	        list.add(btn.getEndTime());
	        buttonData.add(btn.getData());					
	        selectedCourt = printSelectedCourt().getOutput();
	        BKPeriod = printSelectedCourt().getPeriod();
		}
	}
		
	//Override DatePicker Method in Court Panel (Superclass)
	@Override 
	public void caretUpdate(CaretEvent e) {

		if(e.getSource() instanceof JTextField) {
			JTextField tf = (JTextField) e.getSource();
			resetCourtOutput(searched);
			loadCourtStatusSQL(tf.getText());
			
			if(searched && tf.getText().equals(BK_Date)) {
				markButtonsSpecificBooking(timeSlotBooked);
			} 
			textPaneCourtOutput.setText(selectedCourt);
			TFBKPeriod.setText(String.valueOf(BKPeriod) + " slots");
			
		}		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//Override Court Button Method in Court Panel (Superclass)
		if(e.getSource() instanceof JButtonOwn) {
			super.actionPerformed(e);
			textPaneCourtOutput.setText(selectedCourt); 
			TFBKPeriod.setText(String.valueOf(BKPeriod) + " slots");
		} 
		//For SearchPanel() 
		else if(e.getSource() == btnSearch) {		
			searchMethod1();
		}					
	}

	//For SearchPanel() 
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub	
		if (e.getKeyCode()==KeyEvent.VK_ENTER && e.getSource() == TFSearch){
			searchMethod1();							
		}
	}
	
	private void searchMethod1() {

		if(TFSearch.getText().isEmpty()) {
			JOptionPane.showMessageDialog(SearchPanel.this, "Search keyword cannot be empty");				
		}			
		else {
			try {
				if(RBBKID.isSelected()) {
					searched = checkSQL1(1, Integer.parseInt(TFSearch.getText()));
					if(!searched) {
						JOptionPane.showMessageDialog(SearchPanel.this, "Invalid ID : This booking is cancelled / is not existed");
						resetAll1();
					}
				}
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(SearchPanel.this, "Invalid number");
				resetAll1();
			}
		}
	}
	

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub		
	} 
}
