package Home;

import java.awt.*;
import java.awt.event.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.sql.*;
import java.util.Arrays;

/*
 * The First Screen Launched for BCMS System
 */
public class LoginScreen extends JFrame {

	private JPanel contentPane, pnlMain, pnlHeader;
	private JLabel lblUserName, lblPassword, lblSignIn, iconLogo, lblSportCourtManagement, lblX;
	private JTextField TFUsername;
	private JPasswordField PFPassword;
	private Button btnSignIn;
	private int xOffset, yOffset;
	private String FName;
    protected String query;
    protected PreparedStatement preparedStmt;
    protected Connection con;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginScreen frame = new LoginScreen();
					frame.setUndecorated(true);
					frame.setTitle("BCMS 1.0");
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
	public LoginScreen() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginScreen.class.getResource("/Home/Images/icons8_badminton_2_60px_ru.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 352, 439);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		pnlMain = new JPanel();
		pnlMain.setBackground(Color.WHITE);
		pnlMain.setBounds(0, 0, 358, 444);
		contentPane.add(pnlMain);
		pnlMain.setLayout(null);
		
		//Move program around
		pnlMain.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xOffset = e.getX();
				yOffset = e.getY();		
			}
		});
		pnlMain.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				
				LoginScreen.this.setLocation(x-xOffset, y-yOffset);
			}
		});
		
		iconLogo = new JLabel("");
		iconLogo.setIcon(new ImageIcon(LoginScreen.class.getResource("/Home/Images/icons8_badminton_2_48px_2.png")));
		iconLogo.setBounds(151, 23, 48, 48);
		pnlMain.add(iconLogo);
		
		lblSportCourtManagement = new JLabel("Sport Court Management System");
		lblSportCourtManagement.setHorizontalAlignment(SwingConstants.CENTER);
		lblSportCourtManagement.setForeground(Color.WHITE);
		lblSportCourtManagement.setFont(new Font("Segoe UI Black", Font.PLAIN, 16));
		lblSportCourtManagement.setBounds(20, 81, 311, 37);
		pnlMain.add(lblSportCourtManagement);
				
		pnlHeader = new JPanel();
		pnlHeader.setBackground(new Color(10, 121, 205));
		pnlHeader.setBounds(0, 0, 358, 142);
		pnlMain.add(pnlHeader);
		pnlHeader.setLayout(null);
		
		lblUserName = new JLabel("USERNAME");
		lblUserName.setBounds(50, 197, 174, 14);
		pnlMain.add(lblUserName);
		
		lblPassword = new JLabel("PASSWORD");
		lblPassword.setBounds(50, 273, 174, 14);
		pnlMain.add(lblPassword);
		
		lblSignIn = new JLabel("Sign In");
		lblSignIn.setFont(new Font("Segoe UI Black", Font.PLAIN, 16));
		lblSignIn.setBounds(148, 163, 54, 23);
		pnlMain.add(lblSignIn);
		
		TFUsername = new JTextField();
		TFUsername.setForeground(Color.BLACK);
		TFUsername.setBounds(50, 222, 250, 25);
		pnlMain.add(TFUsername);
		TFUsername.setColumns(10);
		
		PFPassword = new JPasswordField();
		PFPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER){
					if(TFUsername.getText().isEmpty() || PFPassword.getPassword().length == 0) {
						JOptionPane.showMessageDialog(LoginScreen.this, "Username and password cannot be empty.");
					}
					else {
						checkSQL();
					}
			    }
			}
		});
		PFPassword.setBounds(50, 298, 250, 25);
		pnlMain.add(PFPassword);
		
		
		btnSignIn = new Button("Sign In");
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(TFUsername.getText().isEmpty() || PFPassword.getPassword().length == 0) {
					JOptionPane.showMessageDialog(LoginScreen.this, "Username and password cannot be empty.");
				}
				else {
					checkSQL();	
				}
			}
		});
		btnSignIn.setActionCommand("");
		btnSignIn.setForeground(Color.BLACK);
		btnSignIn.setBackground(new Color(218, 242, 40));
		btnSignIn.setBounds(50, 347, 250, 30);
		pnlMain.add(btnSignIn);
		

		//Exit button
		lblX = new JLabel("X");
		lblX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.exit(0);
			}
		});
		lblX.setBounds(329, 0, 19, 37);
		pnlHeader.add(lblX);
		lblX.setForeground(Color.WHITE);
		lblX.setFont(new Font("Segoe UI Black", Font.PLAIN, 16));
		
	}
	
	private byte[] getHashPassword(byte[] salt){
		
		byte[] hash = null;
		SecureRandom random = new SecureRandom();
				
		KeySpec spec = new PBEKeySpec(PFPassword.getPassword(), salt, 65536, 128);
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = factory.generateSecret(spec).getEncoded();
		} 
		catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			JOptionPane.showMessageDialog(LoginScreen.this, "Generate Hash Password Error: NoSuchAlgorithmException");
		} 
		catch (InvalidKeySpecException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			JOptionPane.showMessageDialog(LoginScreen.this, "Generate Hash Password Error: InvalidKeySpecException");
		}
		return hash;	
	}
	
	private void checkSQL() {
		
		boolean flag = false;
		
		try {
			con = SQLConn.getConSetup();
			query = "SELECT FIRSTNAME, SALT, PASSWORD, POSITION, US_ID FROM USERS WHERE USERNAME = ?";
						
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, TFUsername.getText());
			ResultSet rs = preparedStmt.executeQuery();
			      
			while(rs.next()) {
				FName = rs.getString(1);
				byte[] salt = rs.getBytes(2);				
				byte[] password = getHashPassword(salt);		
				
				if(Arrays.equals(rs.getBytes(3), password)) {
					flag = true;
					
					if(rs.getString(4).equalsIgnoreCase("MANAGER")) {
						LoginScreen.this.setVisible(false);
						ManagerGUI mgr = new ManagerGUI(FName, rs.getInt(5));
						setupNextScreen(mgr);

					}
					
					else if(rs.getString(4).equalsIgnoreCase("STAFF")) {
						LoginScreen.this.setVisible(false);
						StaffGUI stf = new StaffGUI(FName, rs.getInt(5));
						setupNextScreen(stf);
					}
				}
			}
			
			if(!flag) {
				JOptionPane.showMessageDialog(LoginScreen.this, "Invalid Username or Password.");
				TFUsername.setText("");
				PFPassword.setText("");
				TFUsername.requestFocusInWindow();
			}			
			rs.close();
			preparedStmt.close();
			con.close();
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(LoginScreen.this, "Username or password couldn't be checked. Please try again.");
		}  	
	}
	
	private void setupNextScreen(JFrame frame) {
		frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

}
