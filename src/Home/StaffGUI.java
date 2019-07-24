package Home;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
 * GUI Screen for Staff
 */
public class StaffGUI extends JFrame {

	protected JPanel contentPane, displayPanel, sidePnlBookings, sidePnlSales, sidePanel; //sidePnlSettings,
	protected JPanel pnlBookings, pnlBKMngmt, pnlSales;
	protected ArrayList<JPanel> sidePanelList;
	public final static Color darkBlue = new Color(17, 29, 127);
	public final static Color lightBlue = new Color(72,160,198);
	protected CardLayout cardLayout;
	protected int xOffset, yOffset;
	protected String firstName;
	protected int userID;
	protected SimpleDateFormat ft = new SimpleDateFormat ("EEE, yyyy-MM-dd");
	protected Date date = new Date();


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StaffGUI frame = new StaffGUI("Staff1", 2);
					frame.setUndecorated(true);
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public StaffGUI(String firstName, int userID) {
		
		this.firstName = firstName;
		this.userID = userID;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(StaffGUI.class.getResource("/Home/Images/icons8_badminton_2_60px_ru.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 931, 800);
		contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);	
				
		displayPanel = new JPanel();
		displayPanel.setBounds(256, 0, 645, 483);
				
		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerSize(0);
		splitPane.setBounds(0, 0, 980, 810);
		contentPane.add(splitPane);
		
		//Move program around
		splitPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xOffset = e.getX();
				yOffset = e.getY();		
			}
		});
		splitPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				
				StaffGUI.this.setLocation(x-xOffset, y-yOffset);
			}
		});
		
		splitPane.setRightComponent(displayPanel);
		displayPanel.setLayout(new CardLayout(0, 0));
		
		pnlBookings =  new BookingsPanel(this, userID);
		displayPanel.add(pnlBookings, "panelBookings");
		
		pnlBKMngmt = new BKManagementPanel(this, userID);
		displayPanel.add(pnlBKMngmt, "panelBKManagement");
		
		pnlSales = new SalesPanel(this, userID);
		displayPanel.add(pnlSales, "panelSales");;
				
		cardLayout = (CardLayout)(displayPanel.getLayout());
		
		sidePanel = new JPanel();
		splitPane.setLeftComponent(sidePanel);
		splitPane.setDividerLocation(270);
		createSidePanel();
		
	}

	protected void createSidePanel() {

		sidePanelList = new ArrayList();

		sidePanel.setLayout(null);
		sidePanel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		sidePanel.setBackground(darkBlue);
		
		sidePnlBookings = new JPanel();
		sidePanelList.add(sidePnlBookings);
		sidePnlBookings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setColor(sidePnlBookings);
				resetColor(sidePanelList, sidePnlBookings);
				cardLayout.show(displayPanel, "panelBookings");
				((BookingsPanel) pnlBookings).resetAll();
			}
		});
		sidePnlBookings.setLayout(null);
		sidePnlBookings.setForeground(Color.WHITE);
		sidePnlBookings.setBorder(null);
		sidePnlBookings.setBackground(lightBlue);
		sidePnlBookings.setBounds(0, 155, 270, 53);
		sidePanel.add(sidePnlBookings);
		
		JLabel iconBookings = new JLabel("");
		iconBookings.setIcon(new ImageIcon(StaffGUI.class.getResource("/Home/Images/icons8_calendar_plus_30px.png")));
		iconBookings.setHorizontalTextPosition(SwingConstants.CENTER);
		iconBookings.setHorizontalAlignment(SwingConstants.LEFT);
		iconBookings.setBounds(20, 0, 30, 53);
		sidePnlBookings.add(iconBookings);
		
		JLabel lblBooking = new JLabel("New Bookings");
		lblBooking.setHorizontalAlignment(SwingConstants.LEFT);
		lblBooking.setForeground(Color.WHITE);
		lblBooking.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblBooking.setBounds(60, 0, 210, 53);
		sidePnlBookings.add(lblBooking);
		
		JPanel sidePnlBKMngmt = new JPanel();
		sidePanelList.add(sidePnlBKMngmt);
		sidePnlBKMngmt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JPanel sidePnlBKMngmt = (JPanel) e.getSource();
				setColor(sidePnlBKMngmt);
				resetColor(sidePanelList, sidePnlBKMngmt);
				cardLayout.show(displayPanel, "panelBKManagement");
				((BKManagementPanel) pnlBKMngmt).resetAll2();
			}
		});
		sidePnlBKMngmt.setLayout(null);
		sidePnlBKMngmt.setForeground(Color.BLACK);
		sidePnlBKMngmt.setBorder(null);
		sidePnlBKMngmt.setBackground(new Color(17, 29, 127));
		sidePnlBKMngmt.setBounds(0, 208, 270, 53);
		sidePanel.add(sidePnlBKMngmt);
		
		JLabel iconBKMngmt = new JLabel("");
		iconBKMngmt.setIcon(new ImageIcon(StaffGUI.class.getResource("/Home/Images/icons8_timetable_30px_1.png")));
		iconBKMngmt.setHorizontalTextPosition(SwingConstants.CENTER);
		iconBKMngmt.setHorizontalAlignment(SwingConstants.LEFT);
		iconBKMngmt.setBounds(20, 0, 30, 53);
		sidePnlBKMngmt.add(iconBKMngmt);
		
		JLabel lblBKMngmt = new JLabel("Bookings Management");
		lblBKMngmt.setHorizontalAlignment(SwingConstants.LEFT);
		lblBKMngmt.setForeground(Color.WHITE);
		lblBKMngmt.setFont(new Font("Segoe UI", Font.BOLD, 17));
		lblBKMngmt.setBounds(60, 0, 210, 53);
		sidePnlBKMngmt.add(lblBKMngmt);

		
		JSeparator sidePnlSeparator = new JSeparator();
		sidePnlSeparator.setBounds(21, 142, 221, 16);
		sidePanel.add(sidePnlSeparator);
		
		JLabel lblWelcome = new JLabel("Welcome");
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setForeground(new Color(114, 218, 221));
		lblWelcome.setFont(new Font("Kalam", Font.BOLD, 22));
		lblWelcome.setBounds(0, 22, 270, 23);
		sidePanel.add(lblWelcome);
		
		JLabel lblFistName = new JLabel(firstName);
		lblFistName.setHorizontalAlignment(SwingConstants.CENTER);
		lblFistName.setForeground(new Color(114, 218, 221));
		lblFistName.setFont(new Font("Kalam", Font.BOLD, 24));
		lblFistName.setBounds(0, 48, 270, 35);
		sidePanel.add(lblFistName);
		
		JLabel lblTodayDate = new JLabel("Today is " + ft.format(date));
		lblTodayDate.setHorizontalAlignment(SwingConstants.CENTER);
		lblTodayDate.setForeground(new Color(114, 218, 221));
		lblTodayDate.setFont(new Font("Kalam", Font.BOLD, 15));
		lblTodayDate.setBounds(0, 84, 270, 39);
		sidePanel.add(lblTodayDate);
				
		sidePnlSales = new JPanel();
		sidePanelList.add(sidePnlSales);
		sidePnlSales.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setColor(sidePnlSales);
				resetColor(sidePanelList, sidePnlSales);
				cardLayout.show(displayPanel, "panelSales");
				((SalesPanel) pnlSales).resetAll1();
				
			}
		});
		sidePnlSales.setLayout(null);
		sidePnlSales.setForeground(Color.WHITE);
		sidePnlSales.setBorder(null);
		sidePnlSales.setBackground(darkBlue);
		sidePnlSales.setBounds(0, 261, 270, 53);
		sidePanel.add(sidePnlSales);
		
		JLabel iconSales = new JLabel("");
		iconSales.setIcon(new ImageIcon(StaffGUI.class.getResource("/Home/Images/icons8_cash_in_hand_30px.png")));
		iconSales.setHorizontalTextPosition(SwingConstants.CENTER);
		iconSales.setHorizontalAlignment(SwingConstants.LEFT);
		iconSales.setBounds(20, 0, 30, 53);
		sidePnlSales.add(iconSales);
		
		JLabel lblSales = new JLabel("Sales");
		lblSales.setHorizontalAlignment(SwingConstants.LEFT);
		lblSales.setForeground(Color.WHITE);
		lblSales.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblSales.setBounds(60, 0, 210, 53);
		sidePnlSales.add(lblSales);
		
		JPanel pnlLogOutlol = new JPanel();
		pnlLogOutlol.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				StaffGUI.this.setVisible(false);
				LoginScreen login = new LoginScreen();
				login.setUndecorated(true);
				login.setVisible(true);
				login.setLocationRelativeTo(null);
			}
		});
		pnlLogOutlol.setLayout(null);
		pnlLogOutlol.setForeground(Color.WHITE);
		pnlLogOutlol.setBorder(null);
		pnlLogOutlol.setBackground(new Color(17, 29, 127));
		pnlLogOutlol.setBounds(0, 668, 270, 53);
		sidePanel.add(pnlLogOutlol);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(StaffGUI.class.getResource("/Home/Images/icons8_logout_rounded_left_30px.png")));
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setBounds(20, 0, 30, 53);
		pnlLogOutlol.add(label);
		
		JLabel lblLogOut = new JLabel("Log Out");
		lblLogOut.setHorizontalAlignment(SwingConstants.LEFT);
		lblLogOut.setForeground(Color.WHITE);
		lblLogOut.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblLogOut.setBounds(60, 0, 210, 53);
		pnlLogOutlol.add(lblLogOut);
		
	}

	//Highlight selected sidePanel
	protected void setColor(JPanel panel) {
		panel.setBackground(lightBlue);
	}

	//Unhighlight others sidePanel 
	protected void resetColor(ArrayList<JPanel> panels, JPanel ignore) {
        for(JPanel panel : panels) {
        	if(!panel.equals(ignore))
        		panel.setBackground(darkBlue);
        }        
    }
}




