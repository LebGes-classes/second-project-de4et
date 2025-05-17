package com.example.storage.product;

import com.example.entities.Product;

public interface ProductStorage {
	Product get(int ID) throws Exception;
	Product[] getAll() throws Exception;
	Product insert(String name, int price) throws Exception;
}
