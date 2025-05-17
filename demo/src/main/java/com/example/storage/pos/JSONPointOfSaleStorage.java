package com.example.storage.pos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;

import com.example.entities.PointOfSale;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

public class JSONPointOfSaleStorage implements PointOfSaleStorage {
	String path;
	List<PointOfSale> arr;

	public JSONPointOfSaleStorage(String path) {
		this.path = path;
		this.arr = new ArrayList<>();
	}

	@Override
	public PointOfSale insert(int initialBudget) throws Exception {
		updateArr();
		PointOfSale item = new PointOfSale(arr.size(), initialBudget);
		this.arr.add(item);
		writeArr();
		return item;
	}

	@Override
	public PointOfSale update(PointOfSale pos) throws Exception {
		updateArr();
		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i).getID() == pos.getID()) {
				arr.set(i, pos);
			}
		}
		writeArr();
		return pos;
	}

	@Override
	public void delete(int ID) throws Exception {
		updateArr();
		for (Iterator<PointOfSale> it = arr.iterator(); it.hasNext();) {
			PointOfSale elem = it.next();
			if (elem.getID() == ID) {
				it.remove();
				writeArr();
				return;
			}
		}
	}

	@Override
	public PointOfSale get(int ID) throws Exception  {
		updateArr();
		for (PointOfSale item: arr) {
			if (item.getID() == ID) {
				return item;
			}
		}
		return null;
	}

	@Override
	public PointOfSale[] getAll() throws Exception  {
		updateArr();
		return (PointOfSale[]) this.arr.toArray(PointOfSale[]::new);
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
			this.arr = mapper.readValue(new File(this.path), new TypeReference<ArrayList<PointOfSale>>() {});
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

