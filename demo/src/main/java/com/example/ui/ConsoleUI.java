package com.example.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.example.entities.Consumer;
import com.example.entities.Employee;
import com.example.entities.Item;
import com.example.entities.PointOfSale;
import com.example.entities.Product;
import com.example.entities.Warehouse;
import com.example.exceptions.NoEnoughMoneyException;
import com.example.service.ConsumerManager;
import com.example.service.EmployeeManager;
import com.example.service.ItemManager;
import com.example.service.PointOfSaleManager;
import com.example.service.ProductManager;
import com.example.service.WarehouseManager;

public class ConsoleUI {
	ProductManager pm;
	EmployeeManager em;
	ItemManager im;
	WarehouseManager wm;
	PointOfSaleManager posm;
	ConsumerManager cm;

	Scanner sc; 

	public ConsoleUI(ProductManager pm, EmployeeManager em, ItemManager im, WarehouseManager wm,
			PointOfSaleManager posm, ConsumerManager cm) {
		this.pm = pm;
		this.em = em;
		this.im = im;
		this.wm = wm;
		this.posm = posm;
		this.cm = cm;

		this.sc = new Scanner(System.in);
	}

	public void run() throws Exception {
		mainMenu();
	}

	void mainMenu() throws Exception {
		System.out.println("\nВыберите действие:");
		System.out.println("1. Перемещение товаров");
		System.out.println("2. Смена ответственного лица");
		System.out.println("3. Продажа товара");
		System.out.println("4. Возврат товара");
		System.out.println("5. Закупка товара");
		System.out.println("6. Найм сотрудника");
		System.out.println("7. Увольнение сотрудника");
		System.out.println("8. Открытие нового склада");
		System.out.println("9. Закрытие склада");
		System.out.println("10. Открытие пункта продаж");
		System.out.println("11. Закрытие пункта продаж");
		System.out.println("12. Информация о складе");
		System.out.println("13. Информация о пункте");
		System.out.println("14. Информация о товарах доступных к закупке");
		System.out.println("15. Информация о доходности пункте");
		System.out.println("---------------------------------");
		System.out.println("16. Зарегистрировать нового покупателя");
		System.out.println("17. Создать продукт");
		System.out.println("0. Выход из программы");

		int choice = getIntegerInput(0, 17);

		switch (choice) {
			case 1 -> moveItemMenu();
			case 2 -> changeResponsiblePersonMenu();
			case 3 -> sellProductMenu();
			case 4 -> returnProductMenu();
			case 5 -> purchaseProductMenu();
			case 6 -> hireEmployeeMenu();
			case 7 -> fireEmployeeMenu();
			case 8 -> openWarehouseMenu();
			case 9 -> closeWarehouseMenu();
			case 10 -> openSalesPointMenu();
			case 11 -> closeSalesPointMenu();
			case 12 -> showWarehouseInfoMenu();
			case 13 -> showPointOfSaleInfoMenu();
			case 14 -> showAvailableProductsMenu();
			case 15 -> showProfitabilityMenu();
			case 16 -> regNewConsumerMenu();
			case 17 -> addNewProductMenu();
			case 0 -> {
				System.out.println("Выход");
			}
		}
	}

	void moveItemMenu() throws Exception {
		System.out.println("\nПеремещение товаров:");

		System.out.println("\nВыберите склад 'c'");
		int i = 1;
		Warehouse[] all = wm.getAll();
		for (Warehouse pos: all) {
			System.out.println(String.format("%d. %s", i, pos.toString()));
			i++;
		}
		System.out.println("0. Вернуться в главное меню");
		
		int choice = getIntegerInput(0, posm.getAll().length);
		if (choice == 0) {
			mainMenu();
			return;
		}
		int warehouse1ID = all[(choice-1)].getID();
		
		System.out.println("\nВыберите предмет:");
		i = 1;
		List<Item> allItems = wm.getAllItems(warehouse1ID);
		for (Item item: allItems) {
			System.out.println(String.format("%d. %s", i, item.toString()));
			i++;
		}
		System.out.println("0. Вернуться в главное меню");
		
		choice = getIntegerInput(0, allItems.size());
		if (choice == 0) {
			mainMenu();
			return;
		}
		int itemID = allItems.get((choice-1)).getID();

		System.out.println("\nВыберите склад 'в'");
		i = 1;
		for (Warehouse pos: all) {
			System.out.println(String.format("%d. %s", i, pos.toString()));
			i++;
		}
		System.out.println("0. Вернуться в главное меню");
		
		choice = getIntegerInput(0, posm.getAll().length);
		if (choice == 0) {
			mainMenu();
			return;
		}
		int warehouse2ID = all[(choice-1)].getID();
		
		System.out.println("\nПодтвердите перемещение:");
		System.out.println("1. Да");
		System.out.println("0. Вернуться в главное меню");

		choice = getIntegerInput(0, 1);
		if (choice == 1) {
			wm.moveItem(itemID, warehouse2ID, warehouse2ID);
			System.out.println("Товар успешно перемещен!");
		}
		mainMenu();
	}

	void changeResponsiblePersonMenu() throws Exception {
		System.out.println("\nВыберите склад:");
		int i = 1;
		Warehouse[] all = wm.getAll();
		for (Warehouse pos: all) {
			System.out.println(String.format("%d. %s", i, pos.toString()));
			i++;
		}
		System.out.println("0. Вернуться в главное меню");
		
		int choice = getIntegerInput(0, posm.getAll().length);
		if (choice == 0) {
			mainMenu();
			return;
		}
		int warehouseID = all[(choice-1)].getID();
		
		System.out.println("\nВведите нового ответственного:");
		System.out.println("0. Вернуться в главное меню");

		String responsible = this.sc.nextLine();
		if (responsible.equals("0")) {
			mainMenu();
			return;
		}
		
		System.out.println("\nПодтвердите изменение:");
		System.out.println("1. Да");
		System.out.println("0. Вернуться в главное меню");

		choice = getIntegerInput(0, 1);
		if (choice == 1) {
			wm.replaceResponsible(warehouseID, em.add(responsible, "boss", 50000).getID());
			System.out.println("Ответственный успешно изменен!");
		}
		mainMenu();
	}

	void sellProductMenu() throws Exception {
		System.out.println("\nВыберите покупателя:");
		int i = 1;
		Consumer[] allCustomers = cm.getAll();
		for (Consumer cons: allCustomers) {
			System.out.println(String.format("%d. %s", i, cons.toString()));
			i++;
		}
		System.out.println("0. Вернуться в главное меню");
		
		int choice = getIntegerInput(0, cm.getAll().length);
		if (choice == 0) {
			mainMenu();
			return;
		}
		int customerID = allCustomers[(choice-1)].getID();

		System.out.println("\nВыберите пункт продаж:");
		i = 1;
		PointOfSale[] allPOS = posm.getAll();
		for (PointOfSale pos: allPOS) {
			System.out.println(String.format("%d. %s", i, pos.toString()));
			i++;
		}
		System.out.println("0. Вернуться в главное меню");
		
		choice = getIntegerInput(0, posm.getAll().length);
		if (choice == 0) {
			mainMenu();
			return;
		}
		int posID = allPOS[(choice-1)].getID();
		
		System.out.println("\nВыберите товар:");
		i = 1;
		List<Item> allItems = posm.getItems(posID);
		for (Item item: allItems) {
			String s = String.format("%s", pm.get(item.getProductID()).toString());
			System.out.println(String.format("%d. %s", i, s));
			i++;
		}
		System.out.println("0. Вернуться в главное меню");
		
		choice = getIntegerInput(0, allItems.size());
		if (choice == 0) {
			mainMenu();
			return;
		}
		int itemID = allItems.get((choice-1)).getID();
		
		System.out.println("\nПодтвердите продажу:");
		System.out.println("1. Да");
		System.out.println("0. Вернуться в главное меню");

		choice = getIntegerInput(0, 1);
		if (choice == 1) {
			cm.buyItem(customerID, itemID, posID);
			System.out.println("Продажа успешно оформлена!");
		}
		mainMenu();
	}

	void returnProductMenu() throws Exception {
		System.out.println("\nВыберите покупателя:");
		int i = 1;
		Consumer[] allConsumers = cm.getAll();
		for (Consumer cons: allConsumers) {
			System.out.println(String.format("%d. %s", i, cons.toString()));
			i++;
		}
		System.out.println("0. Вернуться в главное меню");
		
		int choice = getIntegerInput(0, cm.getAll().length);
		if (choice == 0) {
			mainMenu();
			return;
		}
		int consumerID = allConsumers[(choice-1)].getID();

		System.out.println("\nВыберите товар для возврата:");
		i = 1;
		HashMap<Item, Integer> boughtItems = cm.getBoughtItems(consumerID);
		List<Item> allItems = new ArrayList<>();
		allItems.addAll(boughtItems.keySet());
		for (Item item: allItems) {
			System.out.println(String.format("%d. %s", i, item.toString()));
			i++;
		}
		System.out.println("0. Вернуться в главное меню");
		
		choice = getIntegerInput(0, allItems.size());
		if (choice == 0) {
			mainMenu();
			return;
		}
		int itemID = allItems.get((choice-1)).getID();
		
		System.out.println("\nПодтвердите возврат:");
		System.out.println("1. Да");
		System.out.println("0. Вернуться в главное меню");

		choice = getIntegerInput(0, 1);
		if (choice == 1) {
			cm.returnItem(consumerID, itemID);
			System.out.println("Возврат успешно оформлен!");
		}
		mainMenu();
	}

	void purchaseProductMenu() throws Exception {
		System.out.println("\nВыберите склад для закупки:");
		int i = 1;
		Warehouse[] all = wm.getAll();
		for (Warehouse pos: all) {
			System.out.println(String.format("%d. %s", i, pos.toString()));
			i++;
		}
		System.out.println("0. Вернуться в главное меню");
		
		int choice = getIntegerInput(0, wm.getAll().length);
		if (choice == 0) {
			mainMenu();
			return;
		}
		int warehouseID = all[(choice-1)].getID();

		System.out.println("\nВыберите пункт продаж:");
		i = 1;
		PointOfSale[] allPOS = posm.getAll();
		for (PointOfSale pos: allPOS) {
			System.out.println(String.format("%d. %s", i, pos.toString()));
			i++;
		}
		System.out.println("0. Вернуться в главное меню");
		
		choice = getIntegerInput(0, posm.getAll().length);
		if (choice == 0) {
			mainMenu();
			return;
		}
		int posID = allPOS[(choice-1)].getID();
		
		System.out.println("\nВыберите продукт:");
		i = 1;
		Product[] products = pm.getAll();
		for (Product prod: products) {
			System.out.println(String.format("%d. %s", i, prod.toString()));
			i++;
		}
		System.out.println("0. Вернуться в главное меню");
		
		choice = getIntegerInput(0, products.length);
		if (choice == 0) {
			mainMenu();
			return;
		}
		int productID = products[choice-1].getID();

		System.out.println("\nУкажите количество [1:" + wm.countProduct(warehouseID, productID) + "]:");
		System.out.println("0. Вернуться в главное меню");
		choice = getIntegerInput(0, wm.countProduct(warehouseID, productID));
		if (choice == 0) {
			mainMenu();
			return;
		}
		int amount = choice;
		
		System.out.println("\nПодтвердите закупку:");
		System.out.println("1. Да");
		System.out.println("0. Вернуться в главное меню");

		choice = getIntegerInput(0, 1);
		if (choice == 1) {
			try {
				posm.buyProduct(posID, productID, warehouseID, amount);
			} catch (NoEnoughMoneyException e) {
				System.out.println("Не хватает денег!");
				mainMenu();
				return;
			}
			System.out.println("Закупка успешно оформлена!");
		}
		mainMenu();
	}


	void hireEmployeeMenu() throws Exception {
		System.out.println("\nВыберите пункт продаж:");
		int i = 1;
		PointOfSale[] all = posm.getAll();
		for (PointOfSale pos: all) {
			System.out.println(String.format("%d. %s", i, pos.toString()));
			i++;
		}
		System.out.println("0. Вернуться в главное меню");
		
		int choice = getIntegerInput(0, posm.getAll().length);
		if (choice == 0) {
			mainMenu();
			return;
		}
		int posID = all[(choice-1)].getID();

		System.out.println("\nВведите имя нового сотрудника:");
		System.out.println("0. Вернуться в главное меню");
		
		String name = this.sc.nextLine();
		if (name.equals("0")) {
			mainMenu();
			return;
		}
		
		System.out.println("\nВведите должность:");
		System.out.println("0. Вернуться в главное меню");

		String position = this.sc.nextLine();
		if (name.equals("0")) {
			mainMenu();
			return;
		}
		
		System.out.println("\nВведите зарплату:");
		System.out.println("0. Вернуться в главное меню");
		
		int salary = sc.nextInt();
		if (salary == 0) {
			mainMenu();
			return;
		}
		
		System.out.println("\nПодтвердите найм:");
		System.out.println("1. Да");
		System.out.println("0. Вернуться в главное меню");

		choice = getIntegerInput(0, 1);
		if (choice == 1) {
			posm.hire(posID, em.add(name, position, salary).getID());
			System.out.println("Сотрудник успешно нанят!");
		}
		mainMenu();
	}

	void fireEmployeeMenu() throws Exception {
		System.out.println("\nВыберите пункт продаж:");
		int i = 1;
		PointOfSale[] all = posm.getAll();
		for (PointOfSale pos: all) {
			System.out.println(String.format("%d. %s", i, pos.toString()));
			i++;
		}
		System.out.println("0. Вернуться в главное меню");
		
		int choice = getIntegerInput(0, posm.getAll().length);
		if (choice == 0) {
			mainMenu();
			return;
		}
		int posID = all[(choice-1)].getID();

		System.out.println("\nВыберите сотрудника:");
		List<Employee> employees = posm.getEmployees(posID);
		i = 1;
		for (Employee e: employees) {
			System.out.println(String.format("%d. %s", i++, e.toString()));
		}
		System.out.println("0. Вернуться в главное меню");
		
		choice = this.sc.nextInt();
		if (choice == 0) {
			mainMenu();
			return;
		}
		int employeeID = employees.get(choice-1).getID();
		
		System.out.println("\nПодтвердите увольнение:");
		System.out.println("1. Да");
		System.out.println("0. Вернуться в главное меню");

		choice = getIntegerInput(0, 1);
		if (choice == 1) {
			posm.fire(posID, employeeID);
			System.out.println("Сотрудник успешно уволен!");
		}
		mainMenu();
	}

	void openWarehouseMenu() throws Exception {
		System.out.println("\nВведите количество ячеек");
		System.out.println("0. Вернуться в главное меню");
		
		int amount = this.sc.nextInt();
		if (amount == 0) {
			mainMenu();
			return;
		}
		
		System.out.println("\nПодтвердите открытие:");
		System.out.println("1. Да");
		System.out.println("0. Вернуться в главное меню");

		int choice = getIntegerInput(0, 1);
		if (choice == 1) {
			Warehouse wh = wm.newWarehouse(amount, em.add("Ivan", "boss", 50000).getID());

			for (Product prod: pm.getAll()) {
				for (int i = 0; i < 10 && wh.countFreeCells() > 0; i++) {
					wm.addItem(wh.getID(), im.add(prod.getID()).getID());
				}
			}
			System.out.println("Склад успешно открыт!");
		}
		
		mainMenu();
	}

	void closeWarehouseMenu() throws Exception {
		System.out.println("\nВыберите склад для закрытия:");
		int i = 1;
		Warehouse[] all = wm.getAll();
		for (Warehouse pos: all) {
			System.out.println(String.format("%d. %s", i, pos.toString()));
			i++;
		}
		System.out.println("0. Вернуться в главное меню");
		
		int choice = getIntegerInput(0, wm.getAll().length);
		if (choice == 0) {
			mainMenu();
			return;
		}
		int warehouseID = all[(choice-1)].getID();
		
		System.out.println("\nПодтвердите закрытие:");
		System.out.println("1. Да");
		System.out.println("0. Вернуться в главное меню");

		choice = getIntegerInput(0, 1);
		if (choice == 1) {
			this.wm.close(warehouseID);
			System.out.println("Склад успешно закрыт!");
		}
		mainMenu();
	}

	void openSalesPointMenu() throws Exception {
		System.out.println("\nВведите начальный бюджет:");
		System.out.println("0. Вернуться в главное меню");
		
		int initialBudget = sc.nextInt();
		if (initialBudget == 0) {
			mainMenu();
			return;
		}
		
		System.out.println("\nПодтвердите открытие:");
		System.out.println("1. Да");
		System.out.println("0. Вернуться в главное меню");

		int choice = getIntegerInput(0, 1);
		if (choice == 1) {
			posm.newPointOfSale(initialBudget);
			System.out.println("Пункт продаж успешно открыт!");
		}
		mainMenu();
	}

	void closeSalesPointMenu() throws Exception {
		System.out.println("\nВыберите пункт продаж:");
		int i = 1;
		PointOfSale[] all = posm.getAll();
		for (PointOfSale pos: all) {
			System.out.println(String.format("%d. %s", i, pos.toString()));
			i++;
		}
		System.out.println("0. Вернуться в главное меню");
		
		int choice = getIntegerInput(0, posm.getAll().length);
		if (choice == 0) {
			mainMenu();
			return;
		}
		int posID = all[(choice-1)].getID();
		
		System.out.println("\nПодтвердите закрытие:");
		System.out.println("1. Да");
		System.out.println("0. Вернуться в главное меню");

		choice = getIntegerInput(0, 1);
		if (choice == 1) {
			posm.close(posID);
			System.out.println("Пункт продаж успешно закрыт!");
		}
		mainMenu();
	}

	void showWarehouseInfoMenu() throws Exception {
		System.out.println("\nВыберите склад");
		int i = 1;
		Warehouse[] all = wm.getAll();
		for (Warehouse pos: all) {
			System.out.println(String.format("%d. %s", i, pos.toString()));
			i++;
		}
		System.out.println("0. Вернуться в главное меню");
		
		int choice = getIntegerInput(0, wm.getAll().length);
		if (choice == 0) {
			mainMenu();
			return;
		}
		int warehouseID = all[(choice-1)].getID();
		
		System.out.println("\nИнформация об пункте:");
		System.out.println(wm.get(warehouseID).toAdvancedString());
		mainMenu();
	}

	void showPointOfSaleInfoMenu() throws Exception {
		System.out.println("\nВыберите пункт продаж:");
		int i = 1;
		PointOfSale[] all = posm.getAll();
		for (PointOfSale pos: all) {
			System.out.println(String.format("%d. %s", i, pos.toString()));
			i++;
		}
		System.out.println("0. Вернуться в главное меню");
		
		int choice = getIntegerInput(0, posm.getAll().length);
		if (choice == 0) {
			mainMenu();
			return;
		}
		int posID = all[(choice-1)].getID();
		
		System.out.println(posm.get(posID).toAdvancedString());
		mainMenu();
	}

	void showAvailableProductsMenu() throws Exception {
		System.out.println("\nВыберите склад");
		int i = 1;
		Warehouse[] all = wm.getAll();
		for (Warehouse pos: all) {
			System.out.println(String.format("%d. %s", i, pos.toString()));
			i++;
		}
		System.out.println("0. Вернуться в главное меню");
		
		int choice = getIntegerInput(0, wm.getAll().length);
		if (choice == 0) {
			mainMenu();
			return;
		}
		int warehouseID = all[(choice-1)].getID();

		System.out.println("\nДоступные для закупки товары:");
		for (Map.Entry<Product, Integer> pair : wm.countAvailableProducts(warehouseID).entrySet()) {
			System.out.println(pair.getKey() + ": " + pair.getValue());
		}

		mainMenu();
	}

	void showProfitabilityMenu() throws Exception {
		System.out.println("\nВыберите пункт продаж:");
		int i = 1;
		PointOfSale[] all = posm.getAll();
		for (PointOfSale pos: all) {
			System.out.println(String.format("%d. %s", i, pos.toString()));
			i++;
		}
		System.out.println("0. Вернуться в главное меню");
		
		int choice = getIntegerInput(0, posm.getAll().length);
		if (choice == 0) {
			mainMenu();
			return;
		}
		int posID = all[(choice-1)].getID();

		PointOfSale pos = posm.get(posID);
		System.out.println("\nОтчет о доходности:");
		System.out.println("Изначальный бюджет: " + pos.getInitialBudget());
		System.out.println("Текущий бюджет: " + pos.getBudget());
		System.out.println("Доход: " + posm.getIncome(posID));
		
		mainMenu();
	}

	void regNewConsumerMenu() throws Exception {
		System.out.println("\nВведите имя нового покупателя:");
		System.out.println("0. Вернуться в главное меню");
		
		String name = this.sc.nextLine();
		if (name.equals("0")) {
			mainMenu();
			return;
		}

		System.out.println("\nПодтвердите регистрацию:");
		System.out.println("1. Да");
		System.out.println("0. Вернуться в главное меню");

		int choice = getIntegerInput(0, 1);
		if (choice == 1) {
			cm.add(name);
			System.out.println("Пользователь успешно зарегистрирован!");
		}
		mainMenu();
	}

	void addNewProductMenu() throws Exception {
		System.out.println("\nВведите название продукта:");
		System.out.println("0. Вернуться в главное меню");
		
		String name = this.sc.nextLine();
		if (name.equals("0")) {
			mainMenu();
			return;
		}

		System.out.println("Введите цену продукта:");
		System.out.println("0. Вернуться в главное меню");
		int price = this.sc.nextInt();
		if (price == 0) {
			mainMenu();
			return;
		}
		
		System.out.println("\nПодтвердите создание:");
		System.out.println("1. Да");
		System.out.println("0. Вернуться в главное меню");

		int choice = getIntegerInput(0, 1);
		if (choice == 1) {
			pm.newProduct(name, price);
			System.out.println("Продукт успешно создан!");
		}
		mainMenu();
	}

	private int getIntegerInput(int min, int max) {
        while (true) {
            try {
                int input = Integer.parseInt(this.sc.nextLine());
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.printf("Выберите действие из диапазона. [%d:%d]\n", min, max);
                }
            } catch (NumberFormatException e) {}
        }
    }
}
