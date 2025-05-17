package com.example.storage.warehouse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;

import com.example.entities.Warehouse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

public class JSONWarehouseStorage implements WarehouseStorage {
	String path;
	List<Warehouse> arr;

	public JSONWarehouseStorage(String path) {
		this.path = path;
		this.arr = new ArrayList<>();
	}

	@Override
	public Warehouse insert(int maxAmountOfCells, int responsibleID) throws Exception {
		updateArr();
		Warehouse warehouse = new Warehouse(arr.size(), responsibleID, maxAmountOfCells);
		this.arr.add(warehouse);
		writeArr();
		return warehouse;
	}

	@Override
	public Warehouse update(Warehouse warehouse) throws Exception {
		updateArr();
		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i).getID() == warehouse.getID()) {
				arr.set(i, warehouse);
			}
		}
		writeArr();
		return warehouse;
	}

	@Override
	public void delete(int ID) throws Exception {
		updateArr();
		for (Iterator<Warehouse> it = arr.iterator(); it.hasNext();) {
			Warehouse elem = it.next();
			if (elem.getID() == ID) {
				it.remove();
				writeArr();
				return;
			}
		}
	}

	@Override
	public Warehouse get(int ID) throws Exception  {
		updateArr();
		for (Warehouse warehouse: arr) {
			if (warehouse.getID() == ID) {
				return warehouse;
			}
		}
		return null;
	}

	@Override
	public Warehouse[] getAll() throws Exception  {
		updateArr();
		return (Warehouse[]) this.arr.toArray(Warehouse[]::new);
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
			this.arr = mapper.readValue(new File(this.path), new TypeReference<ArrayList<Warehouse>>() {});
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