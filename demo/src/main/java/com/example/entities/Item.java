package com.example.entities;

public class Item {
	int ID;
	int productID;
	// boolean sold;

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
}
