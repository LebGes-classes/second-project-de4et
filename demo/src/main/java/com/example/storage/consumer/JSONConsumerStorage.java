package com.example.storage.consumer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import com.example.entities.Consumer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;


public class JSONConsumerStorage implements ConsumerStorage {
	String path;
	List<Consumer> arr;

	public JSONConsumerStorage(String path) {
		this.path = path;
		this.arr = new ArrayList<>();
	}

	@Override
	public Consumer insert(String name) throws Exception {
		updateArr();
		Consumer consumer = new Consumer(arr.size(), name);
		this.arr.add(consumer);
		writeArr();
		return consumer;
	}

	@Override
	public Consumer update(Consumer consumer) throws Exception {
		updateArr();
		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i).getID() == consumer.getID()) {
				arr.set(i, consumer);
			}
		}
		writeArr();
		return consumer;
	}

	@Override
	public Consumer get(int ID) throws Exception  {
		updateArr();
		for (Consumer consumer: arr) {
			if (consumer.getID() == ID) {
				return consumer;
			}
		}
		return null;
	}

	@Override
	public Consumer[] getAll() throws Exception  {
		updateArr();
		return (Consumer[]) this.arr.toArray(Consumer[]::new);
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
			this.arr = mapper.readValue(new File(this.path), new TypeReference<ArrayList<Consumer>>() {});
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

