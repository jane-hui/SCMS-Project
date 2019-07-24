package Home;

/*
 * A price class consists of item name, price, and ID from database
 */
public class Price {
	
	private String itemName;
	private double itemPrice;
	private int itemID;
	
	public Price(String itemName, double itemPrice, int itemID) {
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.itemID = itemID;
	}
	
	public String getItemName() {
		return this.itemName;
	}
	
	public double getItemPrice() {
		return this.itemPrice;
	}
	
	public int getItemID() {
		return this.itemID;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
}
