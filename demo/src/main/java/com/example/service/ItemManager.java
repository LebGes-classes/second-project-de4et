package com.example.service;

import com.example.entities.Item;
import com.example.entities.Product;
import com.example.storage.item.ItemStorage;

public class ItemManager {
	ItemStorage is;
	ProductManager pm;

	public ItemManager(ItemStorage is, ProductManager pm) {
		this.is = is;
		this.pm = pm;
	}

	public Item add(int productID) throws Exception {
		Item item = this.is.insert(productID, pm.getPriceOfProduct(productID));
		return item;
	}

	public Item get(int ID) throws Exception {
		return this.is.get(ID);
	}

	public Item[] getAll() throws Exception {
		return this.is.getAll();
	}

	public Product getProductOfItem(int ID) throws Exception {
		Item item = this.is.get(ID);
		return getProductOfItem(item);
	}

	public Product getProductOfItem(Item item) throws Exception {
		return pm.get(item.getProductID());
	}
	
}
