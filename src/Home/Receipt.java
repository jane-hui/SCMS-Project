package Home;

import java.awt.EventQueue;
//import javax.swing.*;
import java.awt.Font;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Color;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.LineBorder;


/*
 * Standalone Receipt Frame 
 */
public class Receipt extends JFrame {

	private JPanel contentPane;
	protected SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
	protected Date date = new Date();
    protected String query;
    protected PreparedStatement preparedStmt;
    protected Connection con;
    private int saleID = 0;
    private JTextField TFGrandTotal;
    private JTextField TFItemSales;
    private JTextField TFCourtFee;
    private JTextField TFMoneyReceived;
    private JTextField TFBalance;
    private JLabel lblCheckInput, lblTime;
    private double courtFeeTotal, itemSalesTotal, grandTotal = 0, moneyReceived = 0, balance = 0;
	private DecimalFormat dc = new DecimalFormat("0.00");
	private int xOffset, yOffset;
	private int BK_ID, userID;
	private String selectedItem;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Receipt frame = new Receipt(0, 0, "", 0, 1);
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
	public Receipt(int BK_ID, double courtFeeTotal, String selectedItem, double itemSalesTotal, int userID) {
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 432, 495);
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(Color.ORANGE, 4, true));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(Color.white);
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xOffset = e.getX();
				yOffset = e.getY();		
			}
		});
		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				
				Receipt.this.setLocation(x-xOffset, y-yOffset);
			}
		});

		loadReceiptIDSQL();
		this.BK_ID = BK_ID;
		this.courtFeeTotal = courtFeeTotal;
		this.selectedItem = selectedItem;
		this.itemSalesTotal = itemSalesTotal;
		grandTotal = courtFeeTotal + itemSalesTotal;
		this.userID = userID;

		
		JPanel pnlHeader = new JPanel();
		pnlHeader.setLayout(null);
		pnlHeader.setBackground(new Color(114, 218, 221));
		pnlHeader.setAlignmentY(1.0f);
		pnlHeader.setBounds(5, 26, 423, 62);
		contentPane.add(pnlHeader);
		
		JLabel lblHeader = new JLabel("Receipt");
		lblHeader.setHorizontalAlignment(SwingConstants.CENTER);
		lblHeader.setForeground(Color.WHITE);
		lblHeader.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 22));
		lblHeader.setAlignmentY(1.0f);
		lblHeader.setBounds(0, 11, 424, 31);
		pnlHeader.add(lblHeader);
		
		JSeparator separatorHeader = new JSeparator();
		separatorHeader.setForeground(Color.WHITE);
		separatorHeader.setBackground(Color.WHITE);
		separatorHeader.setBounds(130, 45, 165, 17);
		pnlHeader.add(separatorHeader);
		
		//implement X button
		JLabel lblX = new JLabel("X");
		lblX.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Receipt.this.setVisible(false);
				resetAll();
			}
		});
		lblX.setHorizontalAlignment(SwingConstants.CENTER);
		lblX.setForeground(Color.BLUE);
		lblX.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblX.setBounds(398, 0, 20, 25);
		getContentPane().add(lblX);

		
		lblTime = new JLabel();
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblTime.setForeground(Color.BLACK);
		lblTime.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblTime.setBounds(0, 94, 424, 20);
		lblTime.setText(ft.format(date));
		contentPane.add(lblTime);
		
		JLabel lblReceiptID = new JLabel();
		lblReceiptID.setText("Receipt ID : " + (++saleID));
		lblReceiptID.setHorizontalAlignment(SwingConstants.CENTER);
		lblReceiptID.setForeground(new Color(17, 29, 127));
		lblReceiptID.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblReceiptID.setBounds(0, 119, 424, 20);
		contentPane.add(lblReceiptID);
		
		TFGrandTotal = new JTextField();
		TFGrandTotal.setEditable(false);
		TFGrandTotal.setColumns(10);
		TFGrandTotal.setBounds(174, 250, 216, 28);
		contentPane.add(TFGrandTotal);
		TFGrandTotal.setText(dc.format(grandTotal));
		
		JLabel lblGrandTotal = new JLabel("Grand Total : RM");
		lblGrandTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGrandTotal.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblGrandTotal.setBounds(44, 256, 110, 16);
		contentPane.add(lblGrandTotal);
		
		JLabel lblItemSales = new JLabel("Item Sales : RM");
		lblItemSales.setHorizontalAlignment(SwingConstants.RIGHT);
		lblItemSales.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblItemSales.setBounds(44, 209, 110, 16);
		contentPane.add(lblItemSales);
		
		TFItemSales = new JTextField();
		TFItemSales.setEditable(false);
		TFItemSales.setColumns(10);
		TFItemSales.setBounds(174, 203, 216, 28);
		contentPane.add(TFItemSales);
		TFItemSales.setText(dc.format(itemSalesTotal));
		
		JLabel lblCourtFee = new JLabel("Court Fee : RM");
		lblCourtFee.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCourtFee.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblCourtFee.setBounds(44, 162, 110, 16);
		contentPane.add(lblCourtFee);
		
		TFCourtFee = new JTextField();
		TFCourtFee.setEditable(false);
		TFCourtFee.setColumns(10);
		TFCourtFee.setBounds(174, 156, 216, 28);
		contentPane.add(TFCourtFee);
		TFCourtFee.setText(dc.format(courtFeeTotal));
		
		JLabel lblMoneyReceived = new JLabel("Money Received : RM");
		lblMoneyReceived.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMoneyReceived.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblMoneyReceived.setBounds(30, 316, 124, 16);
		contentPane.add(lblMoneyReceived);
		
		TFMoneyReceived = new JTextField();
		TFMoneyReceived.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					getMoney();
				}
					
			}
		});
		TFMoneyReceived.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				try {
					if(TFMoneyReceived.getText().length() > 0) {
						double i = Double.parseDouble((TFMoneyReceived.getText()));
						lblCheckInput.setText("");	
					}
					else {
						lblCheckInput.setText("");	
					}
				} catch (NumberFormatException e1) {
					lblCheckInput.setText("Invalid number");
				}
			}
		});
		TFMoneyReceived.setColumns(10);
		TFMoneyReceived.setBounds(174, 310, 124, 28);
		contentPane.add(TFMoneyReceived);
		
		JLabel lblBalanceRm = new JLabel("Balance : RM");
		lblBalanceRm.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBalanceRm.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblBalanceRm.setBounds(44, 363, 110, 16);
		contentPane.add(lblBalanceRm);
		
		TFBalance = new JTextField();
		TFBalance.setEditable(false);
		TFBalance.setColumns(10);
		TFBalance.setBounds(174, 357, 216, 28);
		contentPane.add(TFBalance);
		
		JButton btnConfirmAndPrint = new JButton("Confirm and Print");
		btnConfirmAndPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(TFMoneyReceived.getText().isEmpty() || TFBalance.getText().isEmpty()) {
					JOptionPane.showMessageDialog(Receipt.this, "No payment received yet.");
				}
				else {
					boolean success = enterReceiptSQL();
					if(success) {
						JOptionPane.showMessageDialog(Receipt.this, "Sales record has beeen added successfully.");
						resetAll();
						Receipt.this.setVisible(false);
					}
					else {
						JOptionPane.showMessageDialog(Receipt.this, "Sales record couldn't be added. Please try again.");
					}
				}

			}
		});
		btnConfirmAndPrint.setForeground(Color.WHITE);
		btnConfirmAndPrint.setFocusPainted(false);
		btnConfirmAndPrint.setBackground(new Color(17, 29, 127));
		btnConfirmAndPrint.setBounds(221, 413, 136, 33);
		contentPane.add(btnConfirmAndPrint);
		
		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getMoney();
			}
		});
		btnEnter.setBounds(308, 310, 82, 28);
		contentPane.add(btnEnter);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(104, 134, 160));
		separator.setBounds(30, 293, 360, 20);
		contentPane.add(separator);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetAll();
			}
		});
		btnReset.setForeground(new Color(17, 29, 127));
		btnReset.setFocusPainted(false);
		btnReset.setBounds(56, 413, 136, 33);
		contentPane.add(btnReset);
		
		lblCheckInput = new JLabel("");
		lblCheckInput.setForeground(Color.RED);
		lblCheckInput.setBounds(174, 339, 124, 14);
		contentPane.add(lblCheckInput);
	}
	
	private void loadReceiptIDSQL() {
		con = SQLConn.getConSetup();		
		try {			
			query = "SELECT MAX(SALE_ID) FROM SALES";			
			preparedStmt = con.prepareStatement(query);  
			ResultSet rs = preparedStmt.executeQuery();
			
			while(rs.next()) {
				saleID = rs.getInt(1);					
			}		
			rs.close(); 
			preparedStmt.close();
			con.close(); 
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(Receipt.this, "Receipt ID couldn't be loaded. Please try again.");
		}
	}
	
	private boolean enterReceiptSQL() {
		
		con = SQLConn.getConSetup();

		try {
			if(BK_ID <= 0) {
				query = "INSERT INTO SALES (ITEM_LIST, ITEM_TOTAL, GRAND_TOTAL, MONEY_RECEIVED, BALANCE, US_ID, DATE_CREATED) VALUES (?,?,?,?,?,?,?)";			
				preparedStmt = con.prepareStatement(query);  
				preparedStmt.setString(1, selectedItem);
				preparedStmt.setDouble(2, itemSalesTotal);
				preparedStmt.setDouble(3, grandTotal);
				preparedStmt.setDouble(4, moneyReceived);
				preparedStmt.setDouble(5, balance);
				preparedStmt.setInt(6, userID);
				preparedStmt.setString(7, lblTime.getText());

			}
			else if(itemSalesTotal <= 0) {
				query = "INSERT INTO SALES (BK_ID, COURTFEE_TOTAL, GRAND_TOTAL, MONEY_RECEIVED, BALANCE, US_ID, DATE_CREATED) VALUES (?,?,?,?,?,?,?)";			
				preparedStmt = con.prepareStatement(query);  
				preparedStmt.setInt(1, BK_ID);
				preparedStmt.setDouble(2, courtFeeTotal);
				preparedStmt.setDouble(3, grandTotal);
				preparedStmt.setDouble(4, moneyReceived);
				preparedStmt.setDouble(5, balance);
				preparedStmt.setInt(6, userID);
				preparedStmt.setString(7, lblTime.getText());
			}
			else {
				query = "INSERT INTO SALES (BK_ID, COURTFEE_TOTAL, ITEM_LIST, ITEM_TOTAL, GRAND_TOTAL, MONEY_RECEIVED, BALANCE, US_ID, DATE_CREATED) VALUES (?,?,?,?,?,?,?,?,?)";			
				preparedStmt = con.prepareStatement(query);  
				preparedStmt.setInt(1, BK_ID);
				preparedStmt.setDouble(2, courtFeeTotal);
				preparedStmt.setString(3, selectedItem);
				preparedStmt.setDouble(4, itemSalesTotal);
				preparedStmt.setDouble(5, grandTotal);
				preparedStmt.setDouble(6, moneyReceived);
				preparedStmt.setDouble(7, balance);
				preparedStmt.setInt(8, userID);
				preparedStmt.setString(9, lblTime.getText());
			}
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
	
	private void getMoney() {
		try {
			if(TFMoneyReceived.getText().length() > 0) {
				moneyReceived = Double.parseDouble((TFMoneyReceived.getText()));
				TFMoneyReceived.setText(dc.format(moneyReceived));
				balance = moneyReceived - grandTotal;
				balance = Double.valueOf(dc.format(balance)); //round to 2 decimal places
				TFBalance.setText(dc.format(balance));

				if(balance < 0) {
					JOptionPane.showMessageDialog(Receipt.this, "Not enough money received. Please enter again");
					resetAll();
					Receipt.this.setVisible(false);
				}
			}
		} catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(Receipt.this, "Invalid number.");
			resetAll();
		}
	}
	
	protected void resetAll() {
		TFMoneyReceived.setText("");
		TFBalance.setText("");
	}
}
