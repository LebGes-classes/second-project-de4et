package com.example.service;

import com.example.entities.Employee;
import com.example.storage.employee.EmployeeStorage;

public class EmployeeManager {
	EmployeeStorage es;
	public EmployeeManager(EmployeeStorage es) {
		this.es = es;
	}

	public Employee get(int ID) throws Exception {
		return this.es.get(ID);
	}

	public Employee[] getAll() throws Exception {
		return this.es.getAll();
	}

	public Employee add(String name, String position, int salary) throws Exception {
		Employee item = this.es.insert(name, position, salary);
		return item;
	}
}
