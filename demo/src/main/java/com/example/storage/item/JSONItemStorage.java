package com.example.storage.item;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import com.example.entities.Item;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

public class JSONItemStorage implements ItemStorage {
	String path;
	List<Item> arr;

	public JSONItemStorage(String path) {
		this.path = path;
		this.arr = new ArrayList<>();
	}

	@Override
	public Item insert(int productID, int price) throws Exception {
		updateArr();
		Item item = new Item(arr.size(), productID);
		item.setCurrentPrice(price);
		this.arr.add(item);
		writeArr();
		return item;
	}

	@Override
	public Item update(Item item) throws Exception {
		updateArr();
		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i).getID() == item.getID()) {
				arr.set(i, item);
			}
		}
		writeArr();
		return item;
	}

	@Override
	public Item get(int ID) throws Exception  {
		updateArr();
		for (Item item: arr) {
			if (item.getID() == ID) {
				return item;
			}
		}
		return null;
	}

	@Override
	public Item[] getAll() throws Exception  {
		updateArr();
		return (Item[]) this.arr.toArray(Item[]::new);
	}

	private void writeArr() throws IOException {
		try (FileWriter writer = new FileWriter(this.path)) {
			ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
			mapper.writerWithDefaultPrettyPrinter().writeValue(writer, arr);
		}
	}

	private void updateArr() throws IOException {
		ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
		try {
			this.arr = mapper.readValue(new File(this.path), new TypeReference<ArrayList<Item>>() {});
		} catch (MismatchedInputException | FileNotFoundException e) {}
	}

	@Override
	public String toString() {
		StringJoiner sb = new StringJoiner("\n");
		this.arr.forEach(el -> {
			sb.add(el.toString());
		});
		return sb.toString();
	}
}

