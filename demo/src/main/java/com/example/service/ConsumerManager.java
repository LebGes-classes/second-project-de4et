package com.example.service;

import java.util.HashMap;
import java.util.Map;

import com.example.entities.Consumer;
import com.example.entities.Item;
import com.example.storage.consumer.ConsumerStorage;

public class ConsumerManager {
	ItemManager im;
	PointOfSaleManager pm;
	ConsumerStorage cs;

	public ConsumerManager(ConsumerStorage cs, ItemManager im, PointOfSaleManager pm) {
		this.cs = cs;
		this.im = im;
		this.pm = pm;
	}

	public Consumer get(int ID) throws Exception {
		return this.cs.get(ID);
	}

	public Consumer[] getAll() throws Exception {
		return this.cs.getAll();
	}

	public Consumer add(String name) throws Exception {
		Consumer item = this.cs.insert(name);
		return item;
	}

	public void buyItem(int consumerID, int itemID, int posID) throws Exception {
		Consumer consumer = get(consumerID);
		pm.sellItem(posID, itemID);
		consumer.addBoughItem(itemID, posID);
		this.cs.update(consumer);
	}

	public void returnItem(int consumerID, int itemID) throws Exception {
		Consumer consumer = get(consumerID);
		pm.addItem(consumer.getPOSIDOfBoughtItem(itemID), itemID);
		consumer.removeBoughtItem(itemID);
		this.cs.update(consumer);
	}

	public HashMap<Item, Integer> getBoughtItems(int consumerID) throws Exception {
		Consumer consumer = get(consumerID);
		HashMap<Integer, Integer> res = consumer.getBoughtItems();
		HashMap<Item, Integer> ans = new HashMap<>();
		for (Map.Entry<Integer, Integer> en : res.entrySet()) {
			Integer key = en.getKey();
			Integer val = en.getValue();
			ans.put(im.get(key), val);
		}
		return ans;
	}
}
