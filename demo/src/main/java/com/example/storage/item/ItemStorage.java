package com.example.storage.item;

import com.example.entities.Item;

public interface ItemStorage {
	Item get(int ID) throws Exception;
	Item[] getAll() throws Exception;
	Item insert(int productID) throws Exception;
	// void Update(int ID, Item item) throws Exception;
}
