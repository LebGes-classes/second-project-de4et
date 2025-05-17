package com.example.storage.employee;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import com.example.entities.Employee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;


public class JSONEmployeeStorage implements EmployeeStorage {
	String path;
	List<Employee> arr;

	public JSONEmployeeStorage(String path) {
		this.path = path;
		this.arr = new ArrayList<>();
	}

	@Override
	public Employee insert(String name, String position, int salary) throws Exception {
		updateArr();
		Employee employee = new Employee(arr.size(), name, position, salary);
		this.arr.add(employee);
		writeArr();
		return employee;
	}

	@Override
	public Employee update(Employee employee) throws Exception {
		updateArr();
		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i).getID() == employee.getID()) {
				arr.set(i, employee);
			}
		}
		writeArr();
		return employee;
	}

	@Override
	public Employee get(int ID) throws Exception  {
		updateArr();
		for (Employee employee: arr) {
			if (employee.getID() == ID) {
				return employee;
			}
		}
		return null;
	}

	@Override
	public Employee[] getAll() throws Exception  {
		updateArr();
		return (Employee[]) this.arr.toArray(Employee[]::new);
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
			this.arr = mapper.readValue(new File(this.path), new TypeReference<ArrayList<Employee>>() {});
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

