package Home;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.EtchedBorder;

/*
 * New Sales Panel in GUI, consists of Booking Search Panel & Sale Details Panel 
 */
public class SalesPanel extends SearchPanel {
	
	protected JFrame frame;
	protected int userID;
	protected DefaultListModel<String> listDLM;
	private JTextField TFCPrice, TFCFee, TFITPrice, TFAmount, TFTSales;
	private ArrayList<Price> listPrices;
	private JList<String> listItems;
	private JComboBox CBQty;
	private double courtFeeTotal = 0, itemSalesTotal = 0, itemPrice = 0, itemAmount = 0;
	private int itemQty = 0;
	private JLabel lblCheckInputSale;
	private String selectedItem = "";
	private JTextPane TPPurchase;
	private DecimalFormat dc = new DecimalFormat("0.00");
	private JButton btnSubmit;
	
	/**
	 * Create the panel.
	 */
	public SalesPanel(JFrame frame, int userID) {
		
		super();		
		this.frame = frame;
		this.userID = userID;
		TFBKPeriod.setSize(179, 28);
		selectedItem = ""; 
		itemSalesTotal = 0;
		
		HeaderSeparator.setBounds(151, 45, 346, 17);
		btnReset.setEnabled(false);
		btnReset.setVisible(false);
		textPaneCourtOutput.setForeground(Color.DARK_GRAY);
		TFBKDate.setLocation(15, 205);
		lblContactNumber.setLocation(15, 120);
		lblDate.setLocation(15, 182);
		TFCTNo.setLocation(15, 143);
		TFCustName.setLocation(15, 83);
		textPaneCourtOutput.setLocation(0, 65);
		lblCustName.setLocation(15, 60);
		lblSearchResult.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblSearchResult.setSize(624, 32);
		lblSearchResult.setLocation(0, 11);
		separatorSearch.setBounds(15,115,577,10);
		separatorSearch.setForeground(new Color(104, 134, 160));
		lblSearch.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblSearch.setBounds(0, 11, 624, 32);
		btnReset.setSize(112, 33);
		btnSearch.setSize(112, 33);
		lblSearchCriteria.setBounds(15, 65, 72, 14);
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetAll();
			}
		});

		btnSearch.setLocation(355, 56);
		btnReset.setLocation(477, 57);
		pnlSearchCriteria.setBounds(0, 0, 634, 107);
		TFSearch.setLocation(159, 57);
		RBBKID.setLocation(93, 61);
		scrollPaneCourtOutput.setSize(193, 150);
		pnlSearch.setBounds(20, 87, 612, 382);
		scrollPaneCourtOutput.setLocation(207, 83);
		lblSelectCourt.setLocation(207, 60);
		pnlSearchOutput.setBounds(0, 123, 634, 248);
		lblSearchResult.setText("Booking Details");
		
		JLabel lblBookingHours = new JLabel("Booking Period :");
		lblBookingHours.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblBookingHours.setBounds(424, 59, 200, 16);
		pnlSearchOutput.add(lblBookingHours);
		
		TFBKPeriod.setLocation(424, 83);
		
		JLabel lblPricePerCout = new JLabel("Price Per Cout : RM");
		lblPricePerCout.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblPricePerCout.setBounds(424, 120, 179, 16);
		pnlSearchOutput.add(lblPricePerCout);
		
		TFCPrice = new JTextField();
		TFCPrice.setEditable(false);
		TFCPrice.setColumns(10);
		TFCPrice.setBounds(424, 143, 179, 28);
		pnlSearchOutput.add(TFCPrice);
		
		JLabel lblTotalCourtFee = new JLabel("Total Court Fee : RM");
		lblTotalCourtFee.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblTotalCourtFee.setBounds(424, 182, 179, 16);
		pnlSearchOutput.add(lblTotalCourtFee);
		
		TFCFee = new JTextField();
		TFCFee.setEditable(false);
		TFCFee.setColumns(10);
		TFCFee.setBounds(424, 205, 179, 28);
		pnlSearchOutput.add(TFCFee);
		lblSearch.setText("Court Booking Search");
		this.frame = frame;
		this.userID = userID;
		
		//Header Setup
		pnlHeader.setBackground(new Color(236, 12, 67));	
		lblHeader.setText("New Sales / Court Payments");
		
		JPanel pnlSaleDetails = new JPanel();
		pnlSaleDetails.setBounds(20, 480, 612, 306);
		add(pnlSaleDetails);
		pnlSaleDetails.setBackground(new Color(249, 250, 244));
		pnlSaleDetails.setLayout(null);
		
		JLabel lblItems = new JLabel("Items Choice :");
		lblItems.setBounds(15, 54, 121, 16);
		lblItems.setFont(new Font("Segoe UI", Font.BOLD, 12));
		pnlSaleDetails.add(lblItems);
		
		JLabel lblSalesDetails = new JLabel("Sale Details");
		lblSalesDetails.setHorizontalAlignment(SwingConstants.CENTER);
		lblSalesDetails.setForeground(new Color(17, 29, 127));
		lblSalesDetails.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblSalesDetails.setBounds(0, 11, 624, 32);
		pnlSaleDetails.add(lblSalesDetails);
		
		JScrollPane scrollPaneItems = new JScrollPane();
		scrollPaneItems.setBounds(15, 77, 168, 159);
		pnlSaleDetails.add(scrollPaneItems);
		
        listDLM = new DefaultListModel<>();	
		listItems = new JList<>(listDLM);
		listItems.setLocation(0, 114);
		listItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPaneItems.setViewportView(listItems);
		loadSalePricesSQL();
		
		listItems.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				int i = listItems.getSelectedIndex();
				
				if(i > -1) {
					if(i == 0) {
						TFITPrice.setEditable(true);
					}
					else {
						TFITPrice.setEditable(false);
					}
					TFITPrice.setText(dc.format(listPrices.get(i+1).getItemPrice())); //Skip court fee
					itemPrice = listPrices.get(i+1).getItemPrice(); //Skip court fee
					itemQty = Integer.parseInt(CBQty.getSelectedItem().toString());
					itemAmount = itemPrice * itemQty;
					TFAmount.setText(dc.format(itemAmount));			
				}
			}
		});
				
		
		JLabel lblPrice = new JLabel("Item Price :");
		lblPrice.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblPrice.setBounds(207, 116, 102, 16);
		pnlSaleDetails.add(lblPrice);
		
		TFITPrice = new JTextField();
		TFITPrice.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					TFITPrice.setText(dc.format(itemPrice));
				}
			}
		});
		TFITPrice.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				try {
					if(TFITPrice.getText().length() > 0) {
						itemPrice = Double.parseDouble(TFITPrice.getText());
						itemAmount = itemPrice * itemQty;
						TFAmount.setText(dc.format(itemAmount));	
					}
				} catch (NumberFormatException e1) {
					lblCheckInputSale.setText("Invalid number");
				}
			}
		});
		TFITPrice.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				TFITPrice.setText("");								
			}
			@Override
			public void focusLost(FocusEvent e) {
				TFITPrice.setText(dc.format(itemPrice));
			}
		});
		TFITPrice.setEditable(false);
		TFITPrice.setColumns(10);
		TFITPrice.setBounds(207, 139, 149, 28);
		pnlSaleDetails.add(TFITPrice);
		
		JLabel lblQuantity = new JLabel("Quantity :");
		lblQuantity.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblQuantity.setBounds(207, 54, 96, 16);
		pnlSaleDetails.add(lblQuantity);
		
		CBQty = new JComboBox();
		CBQty.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(TFITPrice.getText().isEmpty()) {
					JOptionPane.showMessageDialog(SalesPanel.this, "No item selected. Please select an item first.");
				}
				else {
					if(CBQty.getSelectedItem() == "99") {
						try {
							CBQty.setEditable(true);
							itemQty = Integer.parseInt(CBQty.getSelectedItem().toString());
							itemAmount = itemPrice * itemQty;
							TFAmount.setText(dc.format(itemAmount));	
							
						} catch (NumberFormatException e1) {
							lblCheckInputSale.setText("Invalid number");
						}
					}
					else {
						CBQty.setEditable(false);
						itemQty = Integer.parseInt(CBQty.getSelectedItem().toString());
						itemAmount = itemPrice * itemQty;
						TFAmount.setText(dc.format(itemAmount));	
					}
				}		
			}
		});
		CBQty.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "99"}));
		CBQty.setBounds(207, 77, 149, 28);
		pnlSaleDetails.add(CBQty);
		
		JLabel lblSaleTotal = new JLabel("Customer Cart :");
		lblSaleTotal.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblSaleTotal.setBounds(396, 54, 175, 16);
		pnlSaleDetails.add(lblSaleTotal);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(396, 75, 207, 92);
		pnlSaleDetails.add(scrollPane);
		
		TPPurchase = new JTextPane();
		TPPurchase.setLocation(396, 0);
		scrollPane.setViewportView(TPPurchase);
		TPPurchase.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		TPPurchase.setEditable(false);
		
		JLabel label = new JLabel("Amount :");
		label.setFont(new Font("Segoe UI", Font.BOLD, 12));
		label.setBounds(207, 185, 96, 16);
		pnlSaleDetails.add(label);
		
		JLabel lblTotalSales = new JLabel("Total Sales : RM");
		lblTotalSales.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblTotalSales.setBounds(396, 178, 129, 16);
		pnlSaleDetails.add(lblTotalSales);
		
		btnSubmit = new JButton("<html><center>Generate<br>Receipt</center><html>");
		btnSubmit.addActionListener(this);
		btnSubmit.setForeground(Color.WHITE);
		btnSubmit.setFocusPainted(false);
		btnSubmit.setBackground(new Color(17, 29, 127));
		btnSubmit.setBounds(514, 259, 89, 33);
		pnlSaleDetails.add(btnSubmit);
		
		JButton btnResetSP = new JButton("Reset");
		btnResetSP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetAll();
				
			}
		});
		btnResetSP.setForeground(new Color(17, 29, 127));
		btnResetSP.setFocusPainted(false);
		btnResetSP.setBounds(396, 259, 89, 33);
		pnlSaleDetails.add(btnResetSP);
		
		TFAmount = new JTextField();
		TFAmount.setEditable(false);
		TFAmount.setColumns(10);
		TFAmount.setBounds(207, 208, 149, 28);
		pnlSaleDetails.add(TFAmount);
		
		TFTSales = new JTextField();
		TFTSales.setEditable(false);
		TFTSales.setColumns(10);
		TFTSales.setBounds(396, 201, 207, 28);
		pnlSaleDetails.add(TFTSales);
				
		JButton btnPutToCart = new JButton("Put to cart");
		btnPutToCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(TFITPrice.getText().isEmpty()) {
					JOptionPane.showMessageDialog(SalesPanel.this, "No item selected. Please select an item first.");
				}
				else {
					selectedItem += listItems.getSelectedValue() + " x " + itemQty + " = RM" + dc.format(itemAmount) + "\n";
					itemSalesTotal += itemAmount;
					TPPurchase.setText(selectedItem);
					TFTSales.setText(dc.format(itemSalesTotal));
					}
				}
		});
		btnPutToCart.setForeground(Color.WHITE);
		btnPutToCart.setFocusPainted(false);
		btnPutToCart.setBackground(new Color(17, 29, 127));
		btnPutToCart.setBounds(15, 259, 341, 33);
		pnlSaleDetails.add(btnPutToCart);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(new Color(104, 134, 160));
		separator.setBounds(378, 58, 14, 234);
		pnlSaleDetails.add(separator);
		
		lblCheckInputSale = new JLabel("");
		lblCheckInputSale.setForeground(Color.RED);
		lblCheckInputSale.setBounds(207, 245, 139, 14);
		pnlSaleDetails.add(lblCheckInputSale);
		
		JSeparator separatorSales = new JSeparator();
		separatorSales.setForeground(new Color(236, 12, 67));
		separatorSales.setBounds(20, 469, 602, 12);
		add(separatorSales);
		

	}
		
	private void resetAll() {
		super.resetAll1();
		courtFeeTotal = 0;
		TFCFee.setText("");

		itemPrice = 0;
		itemQty = 0;
		itemAmount = 0;
		selectedItem = "";
		itemSalesTotal = 0;

		listItems.clearSelection();
		CBQty.setSelectedIndex(0);
		TFITPrice.setText("");
		TFAmount.setText("");
		TPPurchase.setText("");
		TFTSales.setText("");			
	}
	
	private void loadSalePricesSQL() {
		
		listPrices = new ArrayList<Price>();
		listDLM.clear();
			
		con = SQLConn.getConSetup();
		
		try {			
			query = "SELECT ITEM_NAME, PRICE, IT_ID FROM PRICES ";			
			preparedStmt = con.prepareStatement(query);  
			ResultSet rs = preparedStmt.executeQuery();
			
			while(rs.next()) {
				listPrices.add(new Price(rs.getString(1), rs.getDouble(2), rs.getInt(3)));		
			}		
			rs.close(); 
			preparedStmt.close();
			con.close();
			
			//SKIP COURT FEE (i=0)
			for(int i=1; i<listPrices.size(); i++) {
				listDLM.addElement(listPrices.get(i).getItemName());
			}
			TFCPrice.setText(dc.format(listPrices.get(0).getItemPrice()));
			
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(SalesPanel.this, "Items couldn't be loaded. Please try again.");
		}  		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		frame.setState(1);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		//For SearchPanel()
		if(e.getSource() == btnSearch) {
			super.actionPerformed(e);
			searchMethod();
		}
		
		else if(e.getSource() == btnSubmit) {
			if(TFCFee.getText().isEmpty() && TFTSales.getText().isEmpty()) {
				JOptionPane.showMessageDialog(SalesPanel.this, "There is no booking or sales input.");
			}
			else {
				Receipt receipt = new Receipt(BK_ID, courtFeeTotal, selectedItem, itemSalesTotal, userID);
				receipt.setUndecorated(true);
				receipt.setVisible(true);
				receipt.setLocationRelativeTo(null);
				resetAll();
			}

		}
	}
	
	
	//For SearchPanel()
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_ENTER && e.getSource() == TFSearch){	
				super.keyPressed(e);
				searchMethod();
		}
	}
	
	private void searchMethod() {
		courtFeeTotal = BKPeriod * listPrices.get(0).getItemPrice();
		TFCFee.setText(dc.format(courtFeeTotal));
	}
}
