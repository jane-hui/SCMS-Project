package Home;

import java.awt.*;
import java.awt.event.*;
import java.util.Collections;
import java.util.LinkedList;
import javax.swing.*;
import javax.swing.event.*;
import java.sql.*;
import javax.swing.border.EtchedBorder;

/*
 * New Bookings Panel in GUI, consists of Court Status Panel and New Booking Details Panel
 */
public class BookingsPanel extends CourtPanel implements CaretListener {
	
	private JPanel pnlCustomer, pnlCourtDetails;
	private JLabel lblDateOutput;
	private JTextPane textPaneCourtOutput;
	private JTextField TFCustName, TFContactNo;
	private JTextField TFBKPeriod;

	/**
	 * Create the panel.
	 */
	public BookingsPanel(JFrame frame, int userID) {
		
		super(frame, userID);	
		HeaderSeparator.setSize(211, 17);
		HeaderSeparator.setLocation(220, 45);
		lblHeader.setBounds(10, 11, 630, 31);
		
		loadCourtStatusSQL(datePicker.getText());
		
		//Header Setup
		lblHeader.setText("New Bookings");
		pnlHeader.setBackground(new Color(0, 191, 225));
		
		//Customer Details Setup
		pnlCustomer = new JPanel();
		add(pnlCustomer);
		createPanelCustomer();
		
		JSeparator CustSeparator = new JSeparator();
		CustSeparator.setBounds(443, 300, 183, 17);
		add(CustSeparator);
		CustSeparator.setForeground(new Color(104, 134, 160));
		
		//Court Details Setup
		pnlCourtDetails = new JPanel();
		pnlCourtDetails.setBounds(443, 315, 197, 452);
		pnlCourtDetails.setLayout(null);
		pnlCourtDetails.setBorder(null);
		pnlCourtDetails.setBackground(new Color(249, 250, 244));
		add(pnlCourtDetails);      
		
        lblDateOutput = new JLabel();
        lblDateOutput.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblDateOutput.setForeground(new Color(232, 17, 35));
        lblDateOutput.setBounds(15, 71, 172, 23);
		lblDateOutput.setText(datePicker.getText());
	    pnlCourtDetails.add(lblDateOutput);
		
		JLabel lblSelectCourt = new JLabel("Selected Court : ");
		lblSelectCourt.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblSelectCourt.setBounds(15, 116, 172, 14);
		pnlCourtDetails.add(lblSelectCourt);
		
		JLabel lblCourt = new JLabel("Court Details");
		lblCourt.setHorizontalAlignment(SwingConstants.CENTER);
		lblCourt.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblCourt.setBounds(57, 11, 96, 17);
		lblCourt.setForeground(StaffGUI.darkBlue);
		pnlCourtDetails.add(lblCourt);
		
		JLabel lblDate = new JLabel("Selected Date :");
		lblDate.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblDate.setBounds(15, 50, 172, 14);
		pnlCourtDetails.add(lblDate);
		
		textPaneCourtOutput = new JTextPane();
		textPaneCourtOutput.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textPaneCourtOutput.setFont(new Font("Segoe UI", Font.BOLD, 13));
		textPaneCourtOutput.setForeground(new Color(232, 17, 35));
		textPaneCourtOutput.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 141, 168, 187);
		scrollPane.setViewportView(textPaneCourtOutput);
		pnlCourtDetails.add(scrollPane);
				
		JButton btnReset = new JButton("Reset");
		btnReset.setBounds(0, 408, 89, 33);
		pnlCourtDetails.add(btnReset);
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetAll();		
			}			
		});
		btnReset.setForeground(StaffGUI.darkBlue);
		btnReset.setFocusPainted(false);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(108, 408, 89, 33);
		pnlCourtDetails.add(btnSubmit);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(TFCustName.getText().isEmpty() || TFContactNo.getText().isEmpty()) {
					JOptionPane.showMessageDialog(BookingsPanel.this, "Customer name or contact number cannot be empty");
				}
				else if (textPaneCourtOutput.getText().isEmpty()) {
					JOptionPane.showMessageDialog(BookingsPanel.this, "No court selected.");
				}
				else {
					enterDataSQL();
					resetAll();
				}
			} 
		});
		btnSubmit.setBackground(StaffGUI.darkBlue);
		btnSubmit.setForeground(Color.WHITE);
		btnSubmit.setFocusPainted(false);
		
		JLabel lblBookingPeriod = new JLabel("Booking Period :");
		lblBookingPeriod.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblBookingPeriod.setBounds(15, 339, 128, 14);
		pnlCourtDetails.add(lblBookingPeriod);
		
		TFBKPeriod = new JTextField();
		TFBKPeriod.setEditable(false);
		TFBKPeriod.setColumns(10);
		TFBKPeriod.setBounds(15, 364, 168, 30);
		pnlCourtDetails.add(TFBKPeriod);
	}
	
	private void createPanelCustomer() {
		pnlCustomer.setBounds(443, 87, 197, 202);		
		pnlCustomer.setBackground(new Color(249, 250, 244));
		pnlCustomer.setBorder(null);
		pnlCustomer.setLayout(null);
		
		JLabel lblCustName = new JLabel("Customer Name :");
		lblCustName.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblCustName.setBounds(15, 55, 113, 14);
		pnlCustomer.add(lblCustName);
		
		JLabel lblContactNo = new JLabel("Contact Number :");
		lblContactNo.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblContactNo.setBounds(15, 127, 128, 14);
		pnlCustomer.add(lblContactNo);
				
		TFCustName = new JTextField();
		TFCustName.addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent event) {
				// TextField is added to its parent => request focus in Event Dispatch Thread
		        SwingUtilities.invokeLater(new Runnable() {
		            @Override
		            public void run() {
		                TFCustName.requestFocusInWindow();
		            }
		        });
			}
			public void ancestorMoved(AncestorEvent event) {
			}
			public void ancestorRemoved(AncestorEvent event) {
			}
		}); 
		TFCustName.isRequestFocusEnabled();
		TFCustName.setColumns(10);
		TFCustName.setBounds(15, 80, 168, 30);
		pnlCustomer.add(TFCustName);
		
		JLabel lblCustomer = new JLabel("Customer Details");
		lblCustomer.setHorizontalAlignment(SwingConstants.CENTER);
		lblCustomer.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblCustomer.setForeground(StaffGUI.darkBlue);
		lblCustomer.setBounds(15, 16, 181, 17);
		pnlCustomer.add(lblCustomer);
		
		TFContactNo = new JTextField();
		TFContactNo.setBounds(15, 152, 168, 30);
		pnlCustomer.add(TFContactNo);
		TFContactNo.setColumns(10);
	}
	
	private void enterDataSQL() {
		
		Collections.sort(buttonData);
		con = SQLConn.getConSetup();	
		try {
			query = "INSERT INTO BOOKINGS (CUST_NAME, CUST_CTNO, BK_DATE, TIME_DATA, TIME_DETAIL, BK_PERIOD, US_ID, DATE_CREATED)" + " VALUES (?,?,?,?,?,?,?,?);";
			preparedStmt = con.prepareStatement(query);	
			preparedStmt.setString(1, TFCustName.getText());
			preparedStmt.setString(2, TFContactNo.getText());
			preparedStmt.setString(3, datePicker.getText());
			preparedStmt.setString(4, buttonData.toString());
			preparedStmt.setString(5, selectedCourt);
			preparedStmt.setInt(6, BKPeriod);
			preparedStmt.setInt(7, userID);
			preparedStmt.setString(8, ft.format(date));
			preparedStmt.execute();
			
			query = "SELECT BK_ID, CUST_NAME, CUST_CTNO, BK_DATE, TIME_DETAIL, BK_PERIOD FROM BOOKINGS WHERE CUST_NAME = ? AND BK_DATE = ? AND TIME_DATA = ?;";
			preparedStmt = con.prepareStatement(query);	
			preparedStmt.setString(1, TFCustName.getText());
			preparedStmt.setString(2, datePicker.getText());
			preparedStmt.setString(3, buttonData.toString());
			ResultSet rs = preparedStmt.executeQuery();
			
			while(rs.next()) {
				String message = "Booking CONFIRMED!\n<html><h1 style='font-size: 14pt;'>Booking ID : " + rs.getInt(1) + "</html>" + "\n" + rs.getString(2) + " ( " + rs.getString(3) + " )\n" + rs.getString(4) + "\n" + rs.getString(5) + "<" + rs.getInt(6) + " slots>";  
				JOptionPane.showMessageDialog(BookingsPanel.this, message, "Booking CONFIRMED", JOptionPane.INFORMATION_MESSAGE);

			}
			rs.close();
			preparedStmt.close();
			con.close();	 	
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(BookingsPanel.this, "Booking couldn't be added. Please try again.");
		}  
	}
 
		
	protected void resetAll() {
		
		TFCustName.setText("");
		TFContactNo.setText("");
		selectedCourt = "";
		BKPeriod = 0;
		textPaneCourtOutput.setText(selectedCourt);
		TFBKPeriod.setText("");

		for(int key : courtTimeSlots.keySet()) {
			  courtTimeSlots.put(key, new LinkedList<Integer>());
			}
		buttonData.clear();
		datePicker.setDateToToday();
		resetCourtOutput(true);
		loadCourtStatusSQL(datePicker.getText());
	}
	
	
	//Override DatePicker Method in Court Panel (Superclass)
	@Override
	public void caretUpdate(CaretEvent e) {
		if(e.getSource() instanceof JTextField) {
			JTextField tf = (JTextField) e.getSource();
			resetCourtOutput(true);	
			textPaneCourtOutput.setText(selectedCourt);
			TFBKPeriod.setText(String.valueOf(BKPeriod) + " slots");
			loadCourtStatusSQL(tf.getText());
			lblDateOutput.setText(tf.getText());
		}		
	}
	
	//Override Court Button Method in Court Panel (Superclass)
	@Override
	public void actionPerformed(ActionEvent e) {	
		if(e.getSource() instanceof JButtonOwn) {	
			super.actionPerformed(e);
			textPaneCourtOutput.setText(selectedCourt);    
			TFBKPeriod.setText(String.valueOf(BKPeriod) + " slots");
		}
	} 
}