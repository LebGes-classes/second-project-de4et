package com.example.storage.pos;

import com.example.entities.PointOfSale;

public interface  PointOfSaleStorage {
	PointOfSale get(int ID) throws Exception;
	PointOfSale[] getAll() throws Exception;
	PointOfSale insert(int initialBudget) throws Exception;
	PointOfSale update(PointOfSale pos) throws Exception;
	void delete(int ID) throws Exception;
}
