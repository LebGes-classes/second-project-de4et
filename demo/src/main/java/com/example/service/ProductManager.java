package com.example.service;

import com.example.entities.Product;
import com.example.storage.product.ProductStorage;;

public class ProductManager {
	ProductStorage ps;

	public ProductManager(ProductStorage ps) {
		this.ps = ps;
	}

	public Product get(int ID) throws Exception {
		return this.ps.get(ID);
	}

	public Product[] getAll() throws Exception {
		return this.ps.getAll();
	}

	public int getPriceOfProduct(int ID) throws Exception {
		return get(ID).getPrice();
	}

	public Product newProduct(String name, int price) throws Exception {
		return this.ps.insert(name, price);
	}
}

