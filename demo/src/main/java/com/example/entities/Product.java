package com.example.entities;

public class Product {
	int ID;
	String name;
	int price;

	public Product() {
	}

	public Product(int ID, String name, int price) {
		this.ID = ID;
		this.name = name;
		this.price = price;
	}

	public int getID() {
		return ID;
	}

	@Override
	public String toString() {
		return String.format("[%d] %s - %d", ID, name, price);
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
