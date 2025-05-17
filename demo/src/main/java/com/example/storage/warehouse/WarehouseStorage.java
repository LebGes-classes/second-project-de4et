package com.example.storage.warehouse;

import com.example.entities.Warehouse;

public interface WarehouseStorage {
	Warehouse get(int ID) throws Exception;
	Warehouse[] getAll() throws Exception;
	Warehouse insert(int maxAmountOfCells, int responsibleID) throws Exception;
	Warehouse update(Warehouse warehouse) throws Exception;
	void delete(int ID) throws Exception;
}
