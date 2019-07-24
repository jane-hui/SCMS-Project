package Home;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.awt.event.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/*
 * Settings Panel in GUI, consists of New User Registration Panel, New Item Registration Panel, and Prices Management Panel
 */
public class SettingsPanel extends JPanel implements MouseListener {

	protected JFrame frame;
	protected int userID;
	private JTextField TFUsername;
	private JPasswordField PFPassword, PFPasswordRe;
	private JTextField TFEmail;
	private byte[] salt;
	private JTextField TFFirstName;
	private JTextField TFLastName;
	private JComboBox CBPost;
	private JTextField TFChangePrice;
	private JTable TBLPrice;
	private JTextField TFChangeItem;
    protected String query;
    protected Connection con;
    protected PreparedStatement preparedStmt;
    protected SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
	protected Date date = new Date();
	private JTextField TFNewItem;
	private JTextField TFNewItemPrice;
	private JLabel lblCheckInputNP, lblCheckInputCP;
	private ArrayList<Price> listPrices;
	private DefaultTableModel tableModel;
	private int itemID = 0;
	private DecimalFormat dc = new DecimalFormat("0.00");

	/**
	 * Create the panel.
	 */	
	public SettingsPanel(JFrame frame, int userID) {
		
		this.frame = frame;
		this.userID = userID;
		
		setBackground(new Color(249, 250, 244));
		setLayout(null);
		
		headerSetup();		
		pnlUserSetup();
		pnlItemSetup();
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(236, 12, 67));
		separator.setBounds(10, 389, 610, 12);
		add(separator);
		
		implementXControls(frame);
	}
	
	private void headerSetup() {
		JPanel pnlHeader = new JPanel();
		pnlHeader.setAlignmentY(1.0f);
		pnlHeader.setBackground(new Color(254, 141, 46));
		pnlHeader.setBounds(0, 26, 729, 62);
		add(pnlHeader);
		pnlHeader.setLayout(null);
		
		JLabel lblBookingDetails = new JLabel("Settings");
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
	
	private void pnlUserSetup() {
		JPanel pnlUser = new JPanel();
		pnlUser.setBackground(new Color(249, 250, 244));
		pnlUser.setBounds(10, 87, 610, 291);
		add(pnlUser);
		pnlUser.setLayout(null);
		
		TFUsername = new JTextField();
		TFUsername.setBounds(130, 60, 152, 30);
		pnlUser.add(TFUsername);
		TFUsername.setText("");
		TFUsername.setColumns(10);
		
		PFPassword = new JPasswordField();
		PFPassword.setBounds(130, 101, 152, 30);
		pnlUser.add(PFPassword);
		
		TFEmail = new JTextField();
		TFEmail.setBounds(130, 183, 152, 30);
		pnlUser.add(TFEmail);
		TFEmail.setText("");
		TFEmail.setColumns(10);
		
		JLabel lblNewUserDetails = new JLabel("New User Registration");
		lblNewUserDetails.setBounds(0, 11, 636, 32);
		pnlUser.add(lblNewUserDetails);
		lblNewUserDetails.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewUserDetails.setForeground(new Color(17, 29, 127));
		lblNewUserDetails.setFont(new Font("Segoe UI", Font.BOLD, 18));
		
		JLabel lblUsername = new JLabel("Username :");
		lblUsername.setBounds(15, 68, 113, 14);
		pnlUser.add(lblUsername);
		lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 13));
		
		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setBounds(15, 109, 113, 14);
		pnlUser.add(lblPassword);
		lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 13));
		
		JLabel lblRetypePassword = new JLabel("Re-type Password :");
		lblRetypePassword.setBounds(304, 109, 131, 14);
		pnlUser.add(lblRetypePassword);
		lblRetypePassword.setFont(new Font("Segoe UI", Font.BOLD, 13));
		
		PFPasswordRe = new JPasswordField();
		PFPasswordRe.setBounds(440, 101, 152, 30);
		pnlUser.add(PFPasswordRe);
		
		JLabel lblFirstName = new JLabel("First Name :");
		lblFirstName.setBounds(15, 150, 113, 14);
		pnlUser.add(lblFirstName);
		lblFirstName.setFont(new Font("Segoe UI", Font.BOLD, 13));
		
		TFFirstName = new JTextField();
		TFFirstName.setBounds(130, 142, 152, 30);
		pnlUser.add(TFFirstName);
		TFFirstName.setText("");
		TFFirstName.setColumns(10);
		
		JLabel lblLastName = new JLabel("Last Name :");
		lblLastName.setBounds(304, 150, 131, 14);
		pnlUser.add(lblLastName);
		lblLastName.setFont(new Font("Segoe UI", Font.BOLD, 13));
		
		TFLastName = new JTextField();
		TFLastName.setBounds(440, 142, 152, 30);
		pnlUser.add(TFLastName);
		TFLastName.setText("");
		TFLastName.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email Address :");
		lblEmail.setBounds(15, 191, 113, 14);
		pnlUser.add(lblEmail);
		lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 13));
		
		JLabel lblPosition = new JLabel("Position :");
		lblPosition.setBounds(304, 68, 131, 14);
		pnlUser.add(lblPosition);
		lblPosition.setFont(new Font("Segoe UI", Font.BOLD, 13));
		
		CBPost = new JComboBox(new String[] {"<Select Position>", "MANAGER", "STAFF"}) ;
		CBPost.setBounds(440, 60, 152, 30);
		pnlUser.add(CBPost);
		
		JButton btnSubmitUser = new JButton("Submit");
		btnSubmitUser.setBounds(357, 240, 126, 33);
		pnlUser.add(btnSubmitUser);
		btnSubmitUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(TFUsername.getText().isEmpty() || PFPassword.getPassword().length == 0 || PFPasswordRe.getPassword().length == 0 || TFEmail.getText().isEmpty() || CBPost.getSelectedIndex()==0) {
					JOptionPane.showMessageDialog(SettingsPanel.this, "User details incomplete. Please enter again.");
				}
				else if (checkUsernameSQL()) {
					JOptionPane.showMessageDialog(SettingsPanel.this, "This username is already taken, Please choose another one.");
				}
				else if (!Arrays.equals(PFPassword.getPassword(), PFPasswordRe.getPassword())) {
					JOptionPane.showMessageDialog(SettingsPanel.this, "Passwords do not matched.");
				}				
				else {
					salt = generateSalt();
					insertUserSQL(setHashPassword(salt, PFPassword.getPassword()));
					JOptionPane.showMessageDialog(SettingsPanel.this, "New user has been added!");
					resetUser();
					salt = new byte[16];												
				}
			}
		});
		btnSubmitUser.setBackground(StaffGUI.darkBlue);
		btnSubmitUser.setForeground(Color.WHITE);
		btnSubmitUser.setFocusPainted(false);
		
		JButton btnReset = new JButton("Reset");
		btnReset.setBounds(169, 240, 126, 33);
		pnlUser.add(btnReset);
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetUser();
			}
			
		});
		btnReset.setForeground(StaffGUI.darkBlue);
		btnReset.setFocusPainted(false);
	}

	private void pnlItemSetup() {
		JPanel pnlItem = new JPanel();
		pnlItem.setBackground(new Color(249, 250, 244));
		pnlItem.setBounds(10, 400, 610, 379);
		add(pnlItem);
		pnlItem.setLayout(null);
		
		JSeparator separatorItem = new JSeparator();
		separatorItem.setOrientation(SwingConstants.VERTICAL);
		separatorItem.setForeground(new Color(104, 134, 160));
		separatorItem.setBounds(259, 21, 8, 347);
		pnlItem.add(separatorItem);
		
		JPanel pnlPricesMngment = new JPanel();
		pnlPricesMngment.setBackground(new Color(249, 250, 244));
		pnlPricesMngment.setBounds(259, 0, 353, 379);
		pnlItem.add(pnlPricesMngment);
		pnlPricesMngment.setLayout(null);
		
		JLabel lblPriceList = new JLabel("Price List :");
		lblPriceList.setBounds(21, 58, 121, 16);
		pnlPricesMngment.add(lblPriceList);
		lblPriceList.setFont(new Font("Segoe UI", Font.BOLD, 12));
		
		JLabel lblItem = new JLabel("Prices Management");
		lblItem.setBounds(0, 11, 377, 32);
		pnlPricesMngment.add(lblItem);
		lblItem.setHorizontalAlignment(SwingConstants.CENTER);
		lblItem.setForeground(new Color(17, 29, 127));
		lblItem.setFont(new Font("Segoe UI", Font.BOLD, 18));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 81, 309, 159);
		pnlPricesMngment.add(scrollPane);
		
		TBLPrice = new JTable();
		TBLPrice.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(TBLPrice.getSelectedRow() >= 0) {
					Price p = listPrices.get(TBLPrice.getSelectedRow());
					TFChangeItem.setText(p.getItemName());
					TFChangePrice.setText(String.valueOf(p.getItemPrice()));
					itemID = p.getItemID();
				}			
			}
		});
		TBLPrice.setFillsViewportHeight(true);
		TBLPrice.setLocation(35, 0);
		TBLPrice.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		TBLPrice.setDefaultEditor(Object.class, null);
		scrollPane.setViewportView(TBLPrice);
			
		tableModel = new DefaultTableModel();
		tableModel.setColumnIdentifiers(new Object[] {"Item Name", "Item Price"});
		TBLPrice.setModel(tableModel);
		loadPricesSQL();
				
		
		JLabel lblNewPrice = new JLabel("New Price : RM");
		lblNewPrice.setBounds(178, 251, 102, 16);
		pnlPricesMngment.add(lblNewPrice);
		lblNewPrice.setFont(new Font("Segoe UI", Font.BOLD, 12));
		
		TFChangePrice = new JTextField();
		TFChangePrice.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				TFChangePrice.setText("");
			}
		});
		TFChangePrice.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				try {
					if(TFChangePrice.getText().length() > 0) {
						double i = Double.parseDouble((TFChangePrice.getText()));
						lblCheckInputCP.setText("");	
					}
					else {
						lblCheckInputCP.setText("");	
					}
				} catch (NumberFormatException e1) {
					lblCheckInputCP.setText("Invalid number");
				}				
			}
		});
		TFChangePrice.setBounds(178, 274, 152, 28);
		pnlPricesMngment.add(TFChangePrice);
		TFChangePrice.setColumns(10);
		
		JButton btnRemoveItem = new JButton("Remove Item");
		btnRemoveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(itemID < 0) {
					JOptionPane.showMessageDialog(SettingsPanel.this, "No item selected.");
				}
				else {			
						boolean success = deleteItemSQL();
						if(success) {
							JOptionPane.showMessageDialog(SettingsPanel.this, "This item has been removed successfully.");
							TFChangeItem.setText("");
							TFChangePrice.setText("");
							loadPricesSQL();
						}
						else {
							JOptionPane.showMessageDialog(SettingsPanel.this, "This item couldn't be removed. Please try again.");
						}								
				}
			}
		});
		btnRemoveItem.setBounds(34, 335, 126, 33);
		pnlPricesMngment.add(btnRemoveItem);
		btnRemoveItem.setForeground(new Color(17, 29, 127));
		btnRemoveItem.setFocusPainted(false);
		
		JButton btnChange = new JButton("Change");
		btnChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(TFChangeItem.getText().isEmpty() || TFChangePrice.getText().isEmpty()) {
					JOptionPane.showMessageDialog(SettingsPanel.this, "Item name or price cannot be empty");
				}
				else {
					try {				
						boolean success = updateItemSQL(Double.parseDouble((TFChangePrice.getText())));

						if(success) {
							JOptionPane.showMessageDialog(SettingsPanel.this, "This item has been updated successfully.");
							TFChangeItem.setText("");
							TFChangePrice.setText("");
							loadPricesSQL();
						}
						else {
							JOptionPane.showMessageDialog(SettingsPanel.this, "This item couldn't be updated. Please try again.");
						}
						
					} catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(SettingsPanel.this, "Invalid number");
					}								
				}
			}
		});
		btnChange.setBounds(191, 335, 126, 33);
		pnlPricesMngment.add(btnChange);
		btnChange.setForeground(Color.WHITE);
		btnChange.setFocusPainted(false);
		btnChange.setBackground(new Color(17, 29, 127));
		
		TFChangeItem = new JTextField();
		TFChangeItem.setBounds(21, 274, 152, 28);
		pnlPricesMngment.add(TFChangeItem);
		TFChangeItem.setColumns(10);
		
		JLabel lblDescription = new JLabel("Item Name :");
		lblDescription.setBounds(21, 251, 102, 16);
		pnlPricesMngment.add(lblDescription);
		lblDescription.setFont(new Font("Segoe UI", Font.BOLD, 12));
		
		lblCheckInputCP = new JLabel("");
		lblCheckInputCP.setForeground(Color.RED);
		lblCheckInputCP.setBounds(191, 306, 139, 14);
		pnlPricesMngment.add(lblCheckInputCP);
		
		JPanel pnlNewItem = new JPanel();
		pnlNewItem.setBackground(new Color(249, 250, 244));
		pnlNewItem.setBounds(0, 0, 259, 304);
		pnlItem.add(pnlNewItem);
		pnlNewItem.setLayout(null);
		
		JLabel label = new JLabel("New Item Registration");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(new Color(17, 29, 127));
		label.setFont(new Font("Segoe UI", Font.BOLD, 18));
		label.setBounds(0, 11, 259, 32);
		pnlNewItem.add(label);
		
		JLabel lblItemName = new JLabel("Item Name : ");
		lblItemName.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblItemName.setBounds(45, 58, 102, 16);
		pnlNewItem.add(lblItemName);
		
		TFNewItem = new JTextField();
		TFNewItem.setColumns(10);
		TFNewItem.setBounds(45, 81, 168, 28);
		pnlNewItem.add(TFNewItem);
		
		JLabel lblItemPrice = new JLabel("Item Price : RM");
		lblItemPrice.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblItemPrice.setBounds(45, 132, 102, 16);
		pnlNewItem.add(lblItemPrice);
		
		TFNewItemPrice = new JTextField();
		TFNewItemPrice.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER){
					if(TFNewItem.getText().isEmpty() || TFNewItemPrice.getText().isEmpty()) {
						JOptionPane.showMessageDialog(SettingsPanel.this, "Information details incomplete. Please enter again.");
					}
					else {
						try {				
							boolean success = insertItemSQL(Double.parseDouble((TFNewItemPrice.getText())));

							if(success) {
								JOptionPane.showMessageDialog(SettingsPanel.this, "New item has been added successfully.");
								TFNewItem.setText("");
								TFNewItemPrice.setText("");
							}
							else {
								JOptionPane.showMessageDialog(SettingsPanel.this, "New item couldn't be added. Please try again.");
							}
							
						} catch (NumberFormatException e1) {
							JOptionPane.showMessageDialog(SettingsPanel.this, "Invalid number");
						}			
					}
				}
			}
		});
		
		TFNewItemPrice.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				try {
					if(TFNewItemPrice.getText().length() > 0) {
						double i = Double.parseDouble((TFNewItemPrice.getText()));
						lblCheckInputNP.setText("");	
					}
					else {
						lblCheckInputNP.setText("");	
					}
				} catch (NumberFormatException e1) {
					lblCheckInputNP.setText("Invalid number");
				}
			}
		});
		TFNewItemPrice.setColumns(10);
		TFNewItemPrice.setBounds(45, 155, 168, 28);
		pnlNewItem.add(TFNewItemPrice);
		
		
		
		JButton btnSubmitItem = new JButton("Submit");
		btnSubmitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(TFNewItem.getText().isEmpty() || TFNewItemPrice.getText().isEmpty()) {
					JOptionPane.showMessageDialog(SettingsPanel.this, "Information details incomplete. Please enter again.");
				}
				else {
					try {				
						boolean success = insertItemSQL(Double.parseDouble((TFNewItemPrice.getText())));

						if(success) {
							JOptionPane.showMessageDialog(SettingsPanel.this, "New item has been added successfully.");
							TFNewItem.setText("");
							TFNewItemPrice.setText("");
							loadPricesSQL();
						}
						else {
							JOptionPane.showMessageDialog(SettingsPanel.this, "New item couldn't be added. Please try again.");
						}
						
					} catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(SettingsPanel.this, "Invalid number");
					}			
				}
			}
		});
		btnSubmitItem.setForeground(Color.WHITE);
		btnSubmitItem.setFocusPainted(false);
		btnSubmitItem.setBackground(new Color(17, 29, 127));
		btnSubmitItem.setBounds(66, 220, 126, 33);
		pnlNewItem.add(btnSubmitItem);
		
		lblCheckInputNP = new JLabel("");		
		lblCheckInputNP.setForeground(Color.RED);
		pnlNewItem.add(lblCheckInputNP);
		lblCheckInputNP.setBounds(45, 187, 168, 14);
	}
	
	
	private byte[] generateSalt() {		
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return salt;
	}
	
	private byte[] setHashPassword(byte[] salt, char[] password){
		
		byte[] hash = null;
				
		KeySpec spec = new PBEKeySpec(password, salt, 65536, 128);
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = factory.generateSecret(spec).getEncoded();
		} 
		catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		catch (InvalidKeySpecException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return hash;
	}
		
	private boolean checkUsernameSQL() {
		boolean result = false;
		
		con = SQLConn.getConSetup();
		try {		
			query = "SELECT USERNAME FROM USERS WHERE USERNAME = ?";

			preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, TFUsername.getText());
			ResultSet rs = preparedStmt.executeQuery();
			      
			if(rs.next()) {
				result = true;
			}		
			rs.close();
			preparedStmt.close();
			con.close();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(SettingsPanel.this, "New username couldn't be checked. Please try again.");
		}
		return result;
	}
	
	private void insertUserSQL(byte[] password) {
	
		con = SQLConn.getConSetup();
		try {		
			query = "INSERT INTO USERS (USERNAME, PASSWORD, SALT, FIRSTNAME, LASTNAME, EMAIL, POSITION, DATE_CREATED)" + " VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, TFUsername.getText());
			preparedStmt.setBytes(2, password);
			preparedStmt.setBytes(3, salt);
			preparedStmt.setString(4, TFFirstName.getText());
			preparedStmt.setString(5, TFLastName.getText());
			preparedStmt.setString(6, TFEmail.getText());
			preparedStmt.setString(7, CBPost.getSelectedItem().toString());
			preparedStmt.setString(8, ft.format(date));
			preparedStmt.execute();
			      		
			preparedStmt.close();
			con.close();
			}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(SettingsPanel.this, "New user couldn't be added. Please try again.");
		}
	}
	
	private boolean insertItemSQL(double price) {
				
		con = SQLConn.getConSetup();
		try {
			query = "INSERT INTO PRICES (ITEM_NAME, PRICE, DATE_CREATED)" + " VALUES (?, ?, ?);";
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, TFNewItem.getText());
			preparedStmt.setDouble(2, Double.valueOf(dc.format(price)));
			preparedStmt.setString(3, ft.format(date));
			preparedStmt.execute();
			      		
			preparedStmt.close();
			con.close();	
			return true;
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	private void loadPricesSQL() {
		
		listPrices = new ArrayList<Price>();
		Object[] rowDatas = new Object[2];	
		tableModel.setRowCount(0);
		
		con = SQLConn.getConSetup();
		
		try {			
			query = "SELECT ITEM_NAME, PRICE, IT_ID FROM PRICES";			
			preparedStmt = con.prepareStatement(query);  
			ResultSet rs = preparedStmt.executeQuery();
			
			while(rs.next()) {
				listPrices.add(new Price(rs.getString(1), rs.getDouble(2), rs.getInt(3)));				
			}		
			rs.close(); 
			preparedStmt.close();
			con.close();
						
			for(int i=0; i< listPrices.size(); i++) {
				rowDatas[0] = listPrices.get(i).getItemName();
				rowDatas[1] = listPrices.get(i).getItemPrice();
				tableModel.addRow(rowDatas);
			}		

		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(SettingsPanel.this, "Prices list couldn't be loaded. Please try again.");
		}  		
	}
	
	private boolean updateItemSQL(double price) {

		con = SQLConn.getConSetup();
		try {
			query = "UPDATE PRICES SET ITEM_NAME = ?, PRICE = ?, DATE_MODIFIED = ? WHERE IT_ID = ?";
			preparedStmt = con.prepareStatement(query);	
			preparedStmt.setString(1, TFChangeItem.getText());
			preparedStmt.setDouble(2, Double.valueOf(dc.format(price)));
			preparedStmt.setString(3, ft.format(date));
			preparedStmt.setInt(4, itemID);
			preparedStmt.execute();
			
			preparedStmt.close();
			con.close();	
			return true;
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}  				
	}
	
	private boolean deleteItemSQL() {
		
		con = SQLConn.getConSetup();		
		try {
			query = "DELETE FROM PRICES WHERE IT_ID = ?";	
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, itemID);
			preparedStmt.execute();
			
			preparedStmt.close();
			con.close();	 	
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}  	
}
	
	private void resetUser() { 
		
		TFUsername.setText("");
		PFPassword.setText("");
		PFPasswordRe.setText("");
		TFFirstName.setText("");
		TFLastName.setText("");
		TFEmail.setText("");
		CBPost.setSelectedIndex(0);
	}
	
	protected void resetAll() {
		resetUser();
		TFNewItem.setText("");
		TFNewItemPrice.setText("");
		loadPricesSQL();
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
