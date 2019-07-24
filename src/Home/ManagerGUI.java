package Home;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
 * GUI Screen for Manager / Administrator
 */
public class ManagerGUI extends StaffGUI {

	private JPanel pnlSettings, pnlRecords, sidePnlSettings, sidePnlRecords;
	private JLabel iconSettings, lblSettings, iconRecords, lblRecords;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerGUI frame = new ManagerGUI("Admin", 1);
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
	public ManagerGUI(String FName, int userID) {
		//staffGUI
		super(FName, userID);	
		
		pnlSettings = new SettingsPanel(this, userID);
		displayPanel.add(pnlSettings, "panelSettings");
		
		pnlRecords = new RecordsPanel(this, userID);
		displayPanel.add(pnlRecords, "panelRecords");
		
		cardLayout = (CardLayout)(displayPanel.getLayout());	
	}
	
	@Override
	protected void createSidePanel() {
		//staffGUI
		super.createSidePanel();	
		
		//Records side panel
		sidePnlRecords = new JPanel();
		sidePanelList.add(sidePnlRecords);
		sidePnlRecords.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setColor(sidePnlRecords);
				resetColor(sidePanelList, sidePnlRecords);
				cardLayout.show(displayPanel, "panelRecords");
				((RecordsPanel) pnlRecords).resetAll();
			}
		});
		sidePnlRecords.setLayout(null);
		sidePnlRecords.setForeground(Color.WHITE);
		sidePnlRecords.setBorder(null);
		sidePnlRecords.setBackground(darkBlue);
		sidePnlRecords.setBounds(0, 314, 270, 53);
		sidePanel.add(sidePnlRecords);
		
		lblRecords = new JLabel("Records");
		lblRecords.setHorizontalAlignment(SwingConstants.LEFT);
		lblRecords.setForeground(Color.WHITE);
		lblRecords.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblRecords.setBounds(60, 0, 210, 53);
		sidePnlRecords.add(lblRecords);
		
		iconRecords = new JLabel("");
		iconRecords.setBounds(20, 0, 30, 53);
		sidePnlRecords.add(iconRecords);
		iconRecords.setIcon(new ImageIcon(StaffGUI.class.getResource("/Home/Images/icons8_dossier_folder_30px.png")));
		iconRecords.setHorizontalTextPosition(SwingConstants.CENTER);
		iconRecords.setHorizontalAlignment(SwingConstants.LEFT);
		
		//Settings side panel
		sidePnlSettings = new JPanel();
		sidePanelList.add(sidePnlSettings);
		sidePnlSettings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setColor(sidePnlSettings);
				resetColor(sidePanelList, sidePnlSettings);
				cardLayout.show(displayPanel, "panelSettings");
				((SettingsPanel) pnlSettings).resetAll();
			}
		});
		sidePnlSettings.setLayout(null);
		sidePnlSettings.setForeground(Color.WHITE);
		sidePnlSettings.setBorder(null);
		sidePnlSettings.setBackground(darkBlue);
		sidePnlSettings.setBounds(0, 367, 270, 53);
		sidePanel.add(sidePnlSettings);
		
		iconSettings = new JLabel("");
		iconSettings.setIcon(new ImageIcon(ManagerGUI.class.getResource("/Home/Images/icons8_settings_3_30px.png")));
		iconSettings.setHorizontalTextPosition(SwingConstants.CENTER);
		iconSettings.setHorizontalAlignment(SwingConstants.LEFT);
		iconSettings.setBounds(20, 0, 30, 53);
		sidePnlSettings.add(iconSettings);
		
		lblSettings = new JLabel("Settings");
		lblSettings.setHorizontalAlignment(SwingConstants.LEFT);
		lblSettings.setForeground(Color.WHITE);
		lblSettings.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblSettings.setBounds(60, 0, 210, 53);
		sidePnlSettings.add(lblSettings);
		

	}

}
