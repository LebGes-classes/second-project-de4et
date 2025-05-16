package com.example;

import java.util.Random;

import com.example.entities.Item;
import com.example.entities.Product;
import com.example.service.ItemManager;
import com.example.service.ProductManager;
import com.example.storage.item.ItemStorage;
import com.example.storage.item.JSONItemStorage;
import com.example.storage.product.JSONProductStorage;
import com.example.storage.product.ProductStorage;

public class App {
    public static Random rd = new Random();
    @SuppressWarnings("CallToPrintStackTrace")
    public static void main( String[] args )
    {
        try {
            ProductStorage productStorage = new JSONProductStorage("products.json");
            ProductManager pm = new ProductManager(productStorage);
            pm.newProduct("amd rx 6600", 26000);
            pm.newProduct("amd ryzen 5600", 14000);
            pm.newProduct("LG UltraGear", 16000);

            
            ItemStorage itemStorage = new JSONItemStorage("items.json");
            ItemManager im = new ItemManager(itemStorage, pm);
            
            Product[] products = pm.getAll();
            for (Product product: products) {
                int r = rd.nextInt() % 10 + 2;
                for (int i = 0; i < r; i++) {
                    im.add(product.getID());
                }
            }

            System.out.println("Products");
            System.out.println(productStorage);

            System.out.println("Item");
            for (Item item: im.getAll()) {
                System.out.println(item + " == " + im.getProductOfItem(item.getID()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
