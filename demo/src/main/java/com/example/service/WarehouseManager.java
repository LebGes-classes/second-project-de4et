package com.example.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entities.Item;
import com.example.entities.Product;
import com.example.entities.Warehouse;
import com.example.storage.warehouse.WarehouseStorage;

public class WarehouseManager {
	ProductManager pm;
	WarehouseStorage ws;
	ItemManager im;

    public WarehouseManager(ProductManager pm, WarehouseStorage ws, ItemManager im) {
		this.pm = pm;
		this.ws = ws;
		this.im = im;
    }

	public Warehouse newWarehouse(int maxAmountOfCells, int responsibleID) throws Exception {
		return ws.insert(maxAmountOfCells, responsibleID);
	}

	public Warehouse get(int ID) throws Exception {
		return ws.get(ID);
	}

	public Warehouse[] getAll() throws Exception {
		return ws.getAll();
	}

	public void replaceResponsible(int warehouseID, int employeeID) throws Exception {
		Warehouse warehouse = this.get(warehouseID);
		warehouse.replaceResponsible(employeeID);
		ws.update(warehouse);
	}


	public int countProduct(int warehouseID, int productID) throws Exception {
		Warehouse warehouse = this.get(warehouseID);
		return warehouse.countProduct(productID);
	}

	public int countFreeCells(int warehouseID) throws Exception {
		Warehouse warehouse = get(warehouseID);
		return warehouse.countFreeCells();
	}

	public HashMap<Product, Integer> countAvailableProducts(int warehouseID) throws Exception {
		Warehouse warehouse = get(warehouseID);
		HashMap<Integer, Integer> res = warehouse.countAvailableProducts();
		HashMap<Product, Integer> ans = new HashMap<>();

		for (Map.Entry<Integer, Integer> pair : res.entrySet()) {
			int productID = pair.getKey();
			ans.put(pm.get(productID), pair.getValue());
		}
		
		return ans;
	}

	public int getItemByProduct(int warehouseID, int productID) throws Exception {
		Warehouse warehouse = this.get(warehouseID);
		return warehouse.getFirstItemIDByProduct(productID);
	}

	public void removeItem(int warehouseID, int itemID) throws Exception {
		Warehouse warehouse = this.get(warehouseID);
		Item item = this.im.get(itemID);
		int cellID = warehouse.getCellIdByItem(item);
		warehouse.freeCell(cellID);
		ws.update(warehouse);
	}

	public void moveItem(int itemID, int fromWarehouseID, int toWarehouseID) throws Exception {
		Warehouse from = this.get(fromWarehouseID);
		Warehouse to = this.get(toWarehouseID);
		Item item = im.get(itemID);

		int cellID = from.getCellIdByItem(item);
		from.freeCell(cellID);
		
		to.addItemToFreeCell(item);

		ws.update(from);
		ws.update(to);
	}

	public List<Item> getAllItems(int warehouseID) throws Exception {
		Warehouse wh = get(warehouseID);
		return wh.getAllItems();
	}

	public void close(int warehouseID) throws Exception {
		Warehouse[] whs = getAll();
		Warehouse warehouse = get(warehouseID);
		for (Warehouse wh: whs) {
			int count = wh.countFreeCells();
			for (int i = 0; i < count; i++) {
				if (countFreeCells(warehouseID) != warehouse.getMaxAmountOfCells()){
					moveItem(warehouse.retrieveFirstItemID(), warehouseID, wh.getID());
				} else {
					ws.delete(warehouseID);
					return;
				}
			}
		}
	}

	public void addItem(int warehouseID, int itemID) throws Exception {
		Warehouse warehouse = this.get(warehouseID);
		Item item = im.get(itemID);

		warehouse.addItemToFreeCell(item);
		ws.update(warehouse);
	}
}
