package com.example.entities;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class Consumer {
	int ID;
	String name;
	HashMap<Integer, Integer> boughtItems;
	
	public Consumer() {
		boughtItems = new HashMap<>();
	}
	
	public Consumer(int ID, String name) {
		this.ID = ID;
		this.name = name;
	}

	public void addBoughItem(int boughtItemID, int posID) {
		boughtItems.put(boughtItemID, posID);
	}

	@JsonIgnore
	public int getPOSIDOfBoughtItem(int boughtItemID) {
		return boughtItems.get(boughtItemID);
	}

	public void removeBoughtItem(int boughtItemID) {
		boughtItems.remove(boughtItemID);
	 }

	public int getID() {
		return ID;
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

	public HashMap<Integer, Integer> getBoughtItems() {
		return boughtItems;
	}

	public void setBoughtItems(HashMap<Integer, Integer> boughtItems) {
		this.boughtItems = boughtItems;
	}
}
