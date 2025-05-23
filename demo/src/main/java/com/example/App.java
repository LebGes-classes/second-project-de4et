package com.example;

import java.util.Random;

import com.example.service.ConsumerManager;
import com.example.service.EmployeeManager;
import com.example.service.ItemManager;
import com.example.service.PointOfSaleManager;
import com.example.service.ProductManager;
import com.example.service.WarehouseManager;
import com.example.storage.consumer.ConsumerStorage;
import com.example.storage.consumer.JSONConsumerStorage;
import com.example.storage.employee.EmployeeStorage;
import com.example.storage.employee.JSONEmployeeStorage;
import com.example.storage.item.ItemStorage;
import com.example.storage.item.JSONItemStorage;
import com.example.storage.pos.JSONPointOfSaleStorage;
import com.example.storage.pos.PointOfSaleStorage;
import com.example.storage.product.JSONProductStorage;
import com.example.storage.product.ProductStorage;
import com.example.storage.warehouse.JSONWarehouseStorage;
import com.example.storage.warehouse.WarehouseStorage;
import com.example.ui.ConsoleUI;

public class App {
    public static Random rd = new Random();
    @SuppressWarnings("CallToPrintStackTrace")
    public static void main( String[] args )
    {
        try {
            run();
            // Product[] products = pm.getAll();
            // System.out.println("Filling warehouse " + warehouse);
            // for (Product product: products) {
            //     int r = rd.nextInt(10)+ 5;
            //     System.out.printf("%s %d\n", product, r);
            //     for (int i = 0; i < r; i++) {
            //         Item item = im.add(product.getID());
            //         wm.addItem(warehouse.getID(), item.getID());
            //     }
            // }
            // System.out.println("freeCells - " + wm.countFreeCells(warehouse.getID()));

            // System.out.println("Buying some for POS " + pointOfSale);
            // Map<Integer, Integer> availableProducts = wm.countAvailableProducts(warehouse.getID());
            // System.out.println("Available: " + availableProducts);
            // for (Map.Entry<Integer, Integer> pair : availableProducts.entrySet()) {
            //     int productID = pair.getKey();
            //     posm.buyProduct(pointOfSale.getID(), productID, warehouse.getID(), 3);
            // }
            // System.out.println("PointOfSale: " + pointOfSale);

            // for (PointOfSale pos: posm.getAll()) {
            //     posm.hire(pos.getID(), em.add("Ivan", "stuff", 45000).getID());
            // }
            
            // System.out.println("Items " + im.getAll().length);
            // for (Item item: im.getAll()) {
            //     System.out.println(item + " == " + im.getProductOfItem(item.getID()));
            // }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static void run() throws Exception {
        ProductStorage productStorage = new JSONProductStorage("products.json");
        ProductManager pm = new ProductManager(productStorage);
        // pm.newProduct("amd rx 6600", 26000);
        // pm.newProduct("amd ryzen 5600", 14000);
        // pm.newProduct("LG UltraGear", 16000);

        ItemStorage itemStorage = new JSONItemStorage("items.json");
        ItemManager im = new ItemManager(itemStorage, pm);
        
        EmployeeStorage employeeStorage = new JSONEmployeeStorage("employee.json");
        EmployeeManager em = new EmployeeManager(employeeStorage);
        System.out.println(em);

        WarehouseStorage warehouseStorage = new JSONWarehouseStorage("warehouse.json");
        WarehouseManager wm = new WarehouseManager(pm, warehouseStorage, im);

        PointOfSaleStorage pointOfSaleStorage = new JSONPointOfSaleStorage("pointOfSale.json");
        PointOfSaleManager posm = new PointOfSaleManager(pointOfSaleStorage, em, wm, pm, im);

        ConsumerStorage consumerStorage = new JSONConsumerStorage("consumers.json");
        ConsumerManager cm = new ConsumerManager(consumerStorage, im, posm);

        ConsoleUI ui = new ConsoleUI(pm, em, im, wm, posm, cm);
        ui.run();
    }
}
