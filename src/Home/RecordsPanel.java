package Home;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

/*
 * Records viewing panel in GUI
 */
public class RecordsPanel extends JPanel implements MouseListener {
	
	protected JFrame frame;
	protected int userID;
    protected String query;
    protected Connection con;
    protected PreparedStatement preparedStmt;
	private ArrayList<String> listBookings;
	private JTable tblRecords;
	private DefaultTableModel tblModel;
	private JComboBox CBRecord; 

	/**
	 * Create the panel.
	 */
	public RecordsPanel(JFrame frame, int userID) {
		this.frame = frame;
		this.userID = userID;
		
		setBackground(new Color(249, 250, 244));
		setLayout(null);
		
		headerSetup();		
		viewerSetup();
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(236, 12, 67));
		separator.setBounds(15, 196, 609, 12);
		add(separator);
		
		implementXControls(frame);

	}
	
	
	private void headerSetup() {
		JPanel pnlHeader = new JPanel();
		pnlHeader.setAlignmentY(1.0f);
		pnlHeader.setBackground(new Color(127, 52, 250));
		pnlHeader.setBounds(0, 26, 729, 62);
		add(pnlHeader);
		pnlHeader.setLayout(null);
		
		JLabel lblBookingDetails = new JLabel("Records");
		lblBookingDetails.setSize(629, 31);
		lblBookingDetails.setForeground(new Color(255, 255, 255));
		lblBookingDetails.setHorizontalAlignment(SwingConstants.CENTER);
		lblBookingDetails.setLocation(10, 11);
		lblBookingDetails.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 22));
		lblBookingDetails.setAlignmentY(1.0f);
		pnlHeader.add(lblBookingDetails);
		
		JSeparator HeaderSeparator = new JSeparator();
		HeaderSeparator.setBackground(new Color(255, 255, 255));
		HeaderSeparator.setForeground(new Color(255, 255, 255));
		HeaderSeparator.setBounds(264, 45, 121, 17);
		pnlHeader.add(HeaderSeparator);
	}

	private void viewerSetup() {
		
		JLabel lblBookingsRecord = new JLabel("Record Viewing");
		lblBookingsRecord.setHorizontalAlignment(SwingConstants.CENTER);
		lblBookingsRecord.setForeground(new Color(17, 29, 127));
		lblBookingsRecord.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblBookingsRecord.setBounds(15, 97, 636, 32);
		add(lblBookingsRecord);
		
		JLabel lblRecord = new JLabel("Record :");
		lblRecord.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblRecord.setBounds(20, 154, 79, 14);
		add(lblRecord);
		
		CBRecord = new JComboBox(new Object[]{});
		CBRecord.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(CBRecord.getSelectedIndex() > 0) {
					loadBookingsSQL(CBRecord.getSelectedItem());
				}
			}
		});
		CBRecord.setModel(new DefaultComboBoxModel(new String[] {"<Select Record>", "Bookings Record", "Sales Record", "Users Record"}));
		CBRecord.setBounds(110, 145, 514, 33);
		add(CBRecord);
			
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 219, 604, 527);
		add(scrollPane);
		
		tblRecords = new JTable();
		tblRecords.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblRecords.setFillsViewportHeight(true);
		scrollPane.setViewportView(tblRecords);
		tblRecords.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblRecords.setDefaultEditor(Object.class, null);
		
		tblModel = new DefaultTableModel();
		tblRecords.setModel(tblModel);

	}
	
	
	private void loadBookingsSQL(Object selectedItem) {
		
		tblModel.setRowCount(0);
		con = SQLConn.getConSetup();
		ResultSet rs = null;

				
		try {
			if(selectedItem.equals("Bookings Record")) {
				
				tblModel.setColumnIdentifiers(new Object[] {"Booking ID", "Customer Name", "Contact Number", "Booking Date", "Time Slot Data", "Time Slot Details", "Booking Period", "User ID", "Created Date", "Modified Date"});
				Object[] rowDatas = new Object[10];	
				
				query = "SELECT * FROM BOOKINGS ORDER BY BK_ID DESC";			
				preparedStmt = con.prepareStatement(query);  
				rs = preparedStmt.executeQuery();
				
				while(rs.next()) {
					rowDatas[0] = rs.getInt(1); //BK_ID
					rowDatas[1] = rs.getString(2); //CUST_NAME
					rowDatas[2] = rs.getString(3); //CUST_CTNO
					rowDatas[3] = rs.getString(4); //BK_DATE
					rowDatas[4] = rs.getString(5); //TIME_DATA
					rowDatas[5] = rs.getString(6); //TIME_DETAIL
					rowDatas[6] = rs.getInt(7); //BK_PERIOD
					rowDatas[7] = rs.getInt(8); //US_ID
					rowDatas[8] = rs.getString(9); //DATE_CREATED
					rowDatas[9] = rs.getString(10); //DATE_MODIFIED
					
					tblModel.addRow(rowDatas);			
				}
			}			
			else if(selectedItem.equals("Sales Record")) {
				tblModel.setColumnIdentifiers(new Object[] {"Sales ID", "Booking ID", "Court Fee Total", "Item Purchase List", "Item Sale Total", "Grand Total", "Money Received", "Balance", "User ID", "Created Date", "Modified Date"});			
				Object[] rowDatas = new Object[11];	
				
				query = "SELECT * FROM SALES ORDER BY SALE_ID DESC";			
				preparedStmt = con.prepareStatement(query);  
				rs = preparedStmt.executeQuery();
				
				while(rs.next()) {
					rowDatas[0] = rs.getInt(1); //SALE_ID
					rowDatas[1] = rs.getInt(2); //BK_ID
					rowDatas[2] = rs.getDouble(3); //COURTFEE_TOTAL
					rowDatas[3] = rs.getString(4); //ITEM_LIST
					rowDatas[4] = rs.getDouble(5); //ITEM_TOTAL
					rowDatas[5] = rs.getDouble(6); //GRAND_TOTAL
					rowDatas[6] = rs.getDouble(7); //MONEY_RECEIVED
					rowDatas[7] = rs.getDouble(8); //BALANCE
					rowDatas[8] = rs.getInt(9); //US_ID
					rowDatas[9] = rs.getString(10); //DATE_CREATED
					rowDatas[10] = rs.getString(11); //DATE_MODIFIED
					
					tblModel.addRow(rowDatas);			
				}
			}
			else if(selectedItem.equals("Users Record")) {
				tblModel.setColumnIdentifiers(new Object[] {"User ID", "Username", "First Name", "Last Name", "Email Address", "Position", "Created Date"});			
				Object[] rowDatas = new Object[7];	
				
				query = "SELECT US_ID, USERNAME, FIRSTNAME, LASTNAME, EMAIL, POSITION, DATE_CREATED FROM USERS ORDER BY US_ID DESC";			
				preparedStmt = con.prepareStatement(query);  
				rs = preparedStmt.executeQuery();
				
				while(rs.next()) {
					rowDatas[0] = rs.getInt(1); //US_ID
					rowDatas[1] = rs.getString(2); //USERNAME
					rowDatas[2] = rs.getString(3); //FIRSTNAME
					rowDatas[3] = rs.getString(4); //LASTNAME
					rowDatas[4] = rs.getString(5); //EMAIL
					rowDatas[5] = rs.getString(6); //POSITION
					rowDatas[6] = rs.getString(7); //DATE_CREATED
					
					tblModel.addRow(rowDatas);			
				}
			}
			rs.close(); 
			preparedStmt.close();
			con.close();

		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(RecordsPanel.this, "Booking records couldn't be loaded. Please try again.");
		}  		
	}
	
	protected void resetAll() {
		tblModel.setRowCount(0);
		CBRecord.setSelectedIndex(0);
	}
	
	
	private void implementXControls(JFrame frame) {
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
	

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		frame.setState(1);
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
