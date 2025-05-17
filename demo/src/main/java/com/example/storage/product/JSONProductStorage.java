package com.example.storage.product;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import com.example.entities.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

public class JSONProductStorage implements ProductStorage {
	String path;
	List<Product> arr;

	public JSONProductStorage(String path) throws Exception {
		this.path = path;
		this.arr = new ArrayList<>();
		updateArr();
	}

	@Override
	public Product insert(String name, int price) throws Exception {
		updateArr();
		Product prod = new Product(arr.size(), name, price);
		this.arr.add(prod);
		writeArr();
		return prod;
	}

	@Override
	public Product get(int ID) throws Exception  {
		updateArr();
		for (Product prod: arr) {
			if (prod.getID() == ID) {
				return prod;
			}
		}
		return null;
	}

	@Override
	public Product[] getAll() throws Exception  {
		updateArr();
		return (Product[]) this.arr.toArray(Product[]::new);
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
			this.arr = mapper.readValue(new File(this.path), new TypeReference<ArrayList<Product>>() {});
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
