package com.example.entities;

public class Item {
	int ID;
	int productID;
	int currentPrice;

	public Item() {}
	
	public Item(int ID, int productID) {
		this.ID = ID;
		this.productID = productID;
	}

	@Override
	public String toString() {
		return String.format("%d -- %d", ID, productID);
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public int getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(int price) {
		this.currentPrice = price;
	}
}
