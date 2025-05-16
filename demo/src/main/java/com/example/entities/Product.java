package com.example.entities;

public class Product {
	int ID;
	String name;
	float price;

	public Product() {
	}

	public Product(int ID, String name, float price) {
		this.ID = ID;
		this.name = name;
		this.price = price;
	}

	public int getID() {
		return ID;
	}

	@Override
	public String toString() {
		return String.format("[%d] %s - %f", ID, name, price);
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

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
}
