package com.example.service;

import java.util.List;

import com.example.entities.Employee;
import com.example.entities.Item;
import com.example.entities.PointOfSale;
import com.example.entities.Product;
import com.example.exceptions.NoEnoughItemsException;
import com.example.exceptions.NoEnoughMoneyException;
import com.example.storage.pos.PointOfSaleStorage;

public class PointOfSaleManager {
	ProductManager pm;
	EmployeeManager em;
	ItemManager im;
	PointOfSaleStorage ps;
	WarehouseManager wm;

	public PointOfSaleManager(PointOfSaleStorage ps, EmployeeManager em, WarehouseManager wm, ProductManager pm, ItemManager im) {
		this.ps = ps;
		this.em = em;
		this.wm = wm;
		this.pm = pm;
		this.im = im;
	}

	public PointOfSale newPointOfSale(int initialBudget) throws Exception {
		return this.ps.insert(initialBudget);
	}

	public List<Employee> getEmployees(int posID) throws Exception  {
		return get(posID).getEmployees();
	}

	public void sellItem(int posID, int itemID) throws Exception {
		PointOfSale pos = get(posID);
		pos.sellItem(itemID);
		ps.update(pos);
	}

	public void addItem(int posID, int itemID) throws Exception {
		PointOfSale pos = get(posID);
		Item item = im.get(itemID);
		pos.setBudget(pos.getBudget() - item.getCurrentPrice());
		ps.update(pos);
	}

	public PointOfSale get(int ID) throws Exception {
		return ps.get(ID);
	}

	public PointOfSale[] getAll() throws Exception {
		return ps.getAll();
	}

	public int getIncome(int posID) throws Exception {
		PointOfSale pos = ps.get(posID);
		return pos.getIncome();
	}

	// posID - PointOfSaleID
	public void buyProduct(int posID, int productID, int warehouseID, int amount) throws Exception {
		int freeAmount = this.wm.countProduct(warehouseID, productID);
		if (amount > freeAmount) {
			throw new NoEnoughItemsException("");
		}

		PointOfSale pos = ps.get(posID);
		Product product = this.pm.get(productID);
		if (product.getPrice() * amount > pos.getBudget()) {
			throw new NoEnoughMoneyException("");
		}

		for (int i = 0; i < amount; i++) {
			Item item = this.im.get(this.wm.getItemByProduct(warehouseID, productID));
			pos.setBudget(pos.getBudget() - item.getCurrentPrice());
			pos.addItem(item);
			this.wm.removeItem(warehouseID, item.getID()); 
		}
		ps.update(pos);
	}

	public void hire(int posID, int employeeID) throws Exception {
		PointOfSale pos = get(posID);
		Employee employee = em.get(employeeID);

		pos.addEmployee(employee);
		ps.update(pos);
	}

	public void fire(int posID, int employeeID) throws Exception {
		PointOfSale pos = get(posID);
		pos.removeEmployee(employeeID);
		ps.update(pos);
	}

	public List<Item> getItems(int posID) throws Exception {
		PointOfSale pos = get(posID);
		return pos.getItems();
	}

	public void close(int ID) throws Exception {
		ps.delete(ID);
	}
}
