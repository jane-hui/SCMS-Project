package Home;

import java.awt.*;
import javax.swing.*;

/*
 * Booking Management Panel in GUI, consists of Court Status Panel & Booking Search Panel
 */
public class BKManagementPanel extends SearchPanel {	
	
	/**
	 * Create the panel.
	 */
	public BKManagementPanel(JFrame frame, int userID) {		
		super(frame, userID);
		lblHeader.setText("Bookings Management / Modification");
		pnlHeader.setBackground(new Color(36, 184, 141));
		HeaderSeparator.setBounds(111, 45, 427, 17);
	}
}

