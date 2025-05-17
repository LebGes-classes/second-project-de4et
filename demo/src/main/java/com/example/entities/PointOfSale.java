package com.example.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PointOfSale {
	int ID;
	List<Item> items;
	List<Employee> employees;
	int budget;
	int initialBudget;

	public PointOfSale() {
		items = new ArrayList<>();
		employees = new ArrayList<>();
	}

	public PointOfSale(int ID, int initialBudget) {
		this();
		budget = initialBudget;
		this.initialBudget = initialBudget;
	}

	public void addItem(Item item) throws Exception {
		budget -= item.currentPrice;
		item.setCurrentPrice((int)((float)(item.getCurrentPrice()) * 1.2));
		items.add(item);
	}

	public void addEmployee(Employee employee) throws Exception {
		employees.add(employee);
	}

	public void removeEmployee(int employeeID) throws Exception {
		for (Iterator<Employee> it = employees.iterator(); it.hasNext();) {
			Employee employee = it.next();
			if (employee.getID() == employeeID) {
				it.remove();
			}
		}
	}

	@Override
	public String toString() {
		return "PointOfSale [items=" + items + ", budget=" + budget + "]";
	}

	public void sellItem(int itemID) {
		for (Iterator<Item> it = items.iterator(); it.hasNext();) {
			Item item = it.next();
			if (item.getID() == itemID) {
				budget += item.getCurrentPrice();
				it.remove();
			}
		}
	}

	public int getIncome() {
		return budget - initialBudget;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}
	public int getBudget() {
		return budget;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	public List<Item> getItems() {
		return items;
	}

	public int getInitialBudget() {
		return initialBudget;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public void setInitialBudget(int initialBudget) {
		this.initialBudget = initialBudget;
	}
}
