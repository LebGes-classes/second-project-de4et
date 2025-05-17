package com.example.storage.employee;

import com.example.entities.Employee;

public interface EmployeeStorage {
	Employee get(int ID) throws Exception;
	Employee[] getAll() throws Exception;
	Employee insert(String name, String position, int salary) throws Exception;
	Employee update(Employee employee) throws Exception;
}
