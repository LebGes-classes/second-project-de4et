package com.example.storage.item;

import com.example.entities.Item;

public interface ItemStorage {
	Item get(int ID) throws Exception;
	Item[] getAll() throws Exception;
	Item insert(int productID, int initialPrice) throws Exception;
	Item update(Item item) throws Exception;
}
