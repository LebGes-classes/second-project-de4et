package com.example.ui;

import java.util.Scanner;

import com.example.entities.PointOfSale;
import com.example.entities.Warehouse;
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

	Scanner sc; 

	public ConsoleUI(ProductManager pm, EmployeeManager em, ItemManager im, WarehouseManager wm,
			PointOfSaleManager posm) {
		this.pm = pm;
		this.em = em;
		this.im = im;
		this.wm = wm;
		this.posm = posm;

		this.sc = new Scanner(System.in);
	}

	public void run() throws Exception {
		mainMenu();
	}

	void mainMenu() throws Exception {
		System.out.println("Выберите действие:");
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
		System.out.println("12. Информация о складе/пункте продаж");
		System.out.println("13. Информация о товарах на складе/пункте");
		System.out.println("14. Информация о товарах доступных к закупке");
		System.out.println("15. Информация о доходности предприятия");
		System.out.println("0. Выход из программы");

		int choice = getIntegerInput(0, 15);

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
			case 12 -> showLocationInfoMenu();
			case 13 -> showProductsInfoMenu();
			case 14 -> showAvailableProductsMenu();
			case 15 -> showProfitabilityMenu();
			case 0 -> {
				System.out.println("Выход");
			}
		}
	}

	void moveItemMenu() throws Exception {
		System.out.println("\nПеремещение товаров:");

		System.out.println("\nВыберите склад");
		int i = 1;
		for (Warehouse wh: wm.getAll()) {
			System.out.println(String.format("%d. %s", i, wh.toString()));
			i++;
		}
		System.out.println("0. Вернуться в главное меню");
		
		int choice = getIntegerInput(0, wm.getAll().length);
		if (choice == 0) {
			mainMenu();
			return;
		}
		// int warehouseID = choice-1;
		
		System.out.println("\nВыберите склад-назначение:");
		System.out.println("1. Склад В");
		System.out.println("2. Склад Г");
		System.out.println("0. Вернуться в главное меню");

		choice = getIntegerInput(0, 2);
		if (choice == 0) {
			mainMenu();
			return;
		}
		
		System.out.println("\nПодтвердите перемещение:");
		System.out.println("1. Да");
		System.out.println("0. Нет (вернуться в главное меню)");

		choice = getIntegerInput(0, 1);
		if (choice == 1) {
			System.out.println("Товар успешно перемещен!");
		}
		mainMenu();
	}

	void changeResponsiblePersonMenu() throws Exception {
		System.out.println("\nВыберите склад:");
		int i = 1;
		for (Warehouse wh: wm.getAll()) {
			System.out.println(String.format("%d. %s", i, wh.toString()));
			i++;
		}
		System.out.println("0. Вернуться в главное меню");

		int choice = getIntegerInput(0, wm.getAll().length);
		if (choice == 0) {
			mainMenu();
			return;
		}
		int warehouseID = choice-1;
		
		System.out.println("\nВведите нового ответственного:");
		System.out.println("0. Вернуться в главное меню");

		String responsible = this.sc.nextLine();
		if (responsible.equals("0")) {
			mainMenu();
			return;
		}
		
		System.out.println("\nПодтвердите изменение:");
		System.out.println("1. Да");
		System.out.println("0. Нет (вернуться в главное меню)");

		choice = getIntegerInput(0, 1);
		if (choice == 1) {
			wm.replaceResponsible(warehouseID, em.add(responsible, "boss", 50000).getID());
			System.out.println("Ответственный успешно изменен!");
		}
		mainMenu();
	}

	void sellProductMenu() throws Exception {
		System.out.println("\nВыберите пункт продаж:");
		System.out.println("1. Пункт А");
		System.out.println("2. Пункт Б");
		System.out.println("0. Вернуться в главное меню");

		int choice = getIntegerInput(0, 2);
		if (choice == 0) {
			mainMenu();
			return;
		}
		
		System.out.println("\nВыберите товар:");
		System.out.println("1. Товар 1");
		System.out.println("2. Товар 2");
		System.out.println("0. Вернуться в главное меню");

		choice = getIntegerInput(0, 2);
		if (choice == 0) {
			mainMenu();
			return;
		}
		
		System.out.println("\nВведите количество:");
		System.out.println("0. Вернуться в главное меню");
		
		int quantity = getIntegerInput(0, Integer.MAX_VALUE);
		if (quantity == 0) {
			mainMenu();
			return;
		}
		
		System.out.println("\nПодтвердите продажу:");
		System.out.println("1. Да");
		System.out.println("0. Нет (вернуться в главное меню)");

		choice = getIntegerInput(0, 1);
		if (choice == 1) {
			System.out.println("Продажа успешно оформлена!");
		}
		mainMenu();
	}

	void returnProductMenu() throws Exception {
		System.out.println("\nВведите номер чека:");
		System.out.println("0. Вернуться в главное меню");
		
		int receiptNumber = getIntegerInput(0, Integer.MAX_VALUE);
		if (receiptNumber == 0) {
			mainMenu();
			return;
		}
		
		System.out.println("\nВыберите товар для возврата:");
		System.out.println("1. Товар 1");
		System.out.println("2. Товар 2");
		System.out.println("0. Вернуться в главное меню");

		int choice = getIntegerInput(0, 2);
		if (choice == 0) {
			mainMenu();
			return;
		}
		
		System.out.println("\nУкажите причину возврата:");
		System.out.println("1. Брак");
		System.out.println("2. Не подошел");
		System.out.println("0. Вернуться в главное меню");

		choice = getIntegerInput(0, 2);
		if (choice == 0) {
			mainMenu();
			return;
		}
		
		System.out.println("\nПодтвердите возврат:");
		System.out.println("1. Да");
		System.out.println("0. Нет (вернуться в главное меню)");

		choice = getIntegerInput(0, 1);
		if (choice == 1) {
			System.out.println("Возврат успешно оформлен!");
		}
		mainMenu();
	}

	void purchaseProductMenu() throws Exception {
		System.out.println("\nВыберите поставщика:");
		System.out.println("1. Поставщик 1");
		System.out.println("2. Поставщик 2");
		System.out.println("0. Вернуться в главное меню");

		int choice = getIntegerInput(0, 2);
		if (choice == 0) {
			mainMenu();
			return;
		}
		
		System.out.println("\nВыберите товары:");
		System.out.println("1. Товар А");
		System.out.println("2. Товар Б");
		System.out.println("0. Вернуться в главное меню");

		choice = getIntegerInput(0, 2);
		if (choice == 0) {
			mainMenu();
			return;
		}
		
		System.out.println("\nВведите количество:");
		System.out.println("0. Вернуться в главное меню");
		
		int quantity = getIntegerInput(0, Integer.MAX_VALUE);
		if (quantity == 0) {
			mainMenu();
			return;
		}
		
		System.out.println("\nПодтвердите закупку:");
		System.out.println("1. Да");
		System.out.println("0. Нет (вернуться в главное меню)");

		choice = getIntegerInput(0, 1);
		if (choice == 1) {
			System.out.println("Закупка успешно оформлена!");
		}
		mainMenu();
	}


	void hireEmployeeMenu() throws Exception {
		System.out.println("\nВыберите пункт продаж:");
		int i = 1;
		for (PointOfSale pos: posm.getAll()) {
			System.out.println(String.format("%d. %s", i, pos.toString()));
			i++;
		}
		System.out.println("0. Вернуться в главное меню");
		
		int choice = getIntegerInput(0, posm.getAll().length);
		if (choice == 0) {
			mainMenu();
			return;
		}
		int posID = choice-1;

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
		System.out.println("\nВыберите пункт продаж для закрытия:");
		int i = 1;
		for (PointOfSale pos: posm.getAll()) {
			System.out.println(String.format("%d. %s", i, pos.toString()));
			i++;
		}
		System.out.println("0. Вернуться в главное меню");
		
		int choice = getIntegerInput(0, posm.getAll().length);
		if (choice == 0) {
			mainMenu();
			return;
		}
		int posID = choice-1;

		System.out.println("\nВыберите сотрудника:");
		System.out.println("0. Вернуться в главное меню");
		
		choice = this.sc.nextInt();
		if (choice == ) {
			mainMenu();
			return;
		}
		
		System.out.println("\nПодтвердите увольнение:");
		System.out.println("1. Да");
		System.out.println("0. Нет (вернуться в главное меню)");

		choice = getIntegerInput(0, 1);
		if (choice == 1) {
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
			wm.newWarehouse(amount, em.add("Ivan", "boss", 50000).getID());
			System.out.println("Склад успешно открыт!");
		}
		
		mainMenu();
	}

	void closeWarehouseMenu() throws Exception {
		System.out.println("\nВыберите склад для закрытия:");
		int i = 1;
		for (Warehouse wh: wm.getAll()) {
			System.out.println(String.format("%d. %s", i, wh.toString()));
			i++;
		}
		System.out.println("0. Вернуться в главное меню");

		int choice = getIntegerInput(0, wm.getAll().length);
		if (choice == 0) {
			mainMenu();
			return;
		}
		int warehouseID = choice-1;
		
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
		System.out.println("\nВведите адрес пункта продаж:");
		System.out.println("0. Вернуться в главное меню");
		
		String address = scanner.nextLine();
		if (address.equals("0")) {
			mainMenu();
			return;
		}
		
		System.out.println("\nВыберите тип пункта:");
		System.out.println("1. Розничный");
		System.out.println("2. Оптовый");
		System.out.println("0. Вернуться в главное меню");

		int choice = getIntegerInput(0, 2);
		if (choice == 0) {
			mainMenu();
			return;
		}
		
		System.out.println("\nПодтвердите открытие:");
		System.out.println("1. Да");
		System.out.println("0. Нет (вернуться в главное меню)");

		choice = getIntegerInput(0, 1);
		if (choice == 1) {
			System.out.println("Пункт продаж успешно открыт!");
		}
		mainMenu();
	}

	void closeSalesPointMenu() throws Exception {
		System.out.println("\nВыберите пункт продаж для закрытия:");
		System.out.println("1. Пункт А");
		System.out.println("2. Пункт Б");
		System.out.println("0. Вернуться в главное меню");

		int choice = getIntegerInput(0, 2);
		if (choice == 0) {
			mainMenu();
			return;
		}
		
		System.out.println("\nУкажите причину закрытия:");
		System.out.println("1. Нерентабельность");
		System.out.println("2. Окончание аренды");
		System.out.println("0. Вернуться в главное меню");

		choice = getIntegerInput(0, 2);
		if (choice == 0) {
			mainMenu();
			return;
		}
		
		System.out.println("\nПодтвердите закрытие:");
		System.out.println("1. Да");
		System.out.println("0. Нет (вернуться в главное меню)");

		choice = getIntegerInput(0, 1);
		if (choice == 1) {
			System.out.println("Пункт продаж успешно закрыт!");
		}
		mainMenu();
	}

	void showLocationInfoMenu() throws Exception {
		System.out.println("\nВыберите объект для просмотра:");
		System.out.println("1. Склад А");
		System.out.println("2. Пункт продаж Б");
		System.out.println("0. Вернуться в главное меню");

		int choice = getIntegerInput(0, 2);
		if (choice == 0) {
			mainMenu();
			return;
		}
		
		System.out.println("\nИнформация об объекте:");
		System.out.println("Адрес: ул. Примерная, 123");
		System.out.println("Ответственный: Иванов И.И.");
		System.out.println("Статус: Активен");
		
		System.out.println("\n1. Обновить информацию");
		System.out.println("0. Вернуться в главное меню");

		choice = getIntegerInput(0, 1);
		if (choice == 1) {
			showLocationInfoMenu(); 
		} else {
			mainMenu();
		}
	}

	void showProductsInfoMenu() throws Exception {
		System.out.println("\nВыберите объект для просмотра товаров:");
		System.out.println("1. Склад А");
		System.out.println("2. Пункт продаж Б");
		System.out.println("0. Вернуться в главное меню");

		int choice = getIntegerInput(0, 2);
		if (choice == 0) {
			mainMenu();
			return;
		}
		
		System.out.println("\nСписок товаров:");
		System.out.println("1. Товар 1 - 50 шт.");
		System.out.println("2. Товар 2 - 30 шт.");
		
		System.out.println("\n1. Обновить список");
		System.out.println("0. Вернуться в главное меню");

		choice = getIntegerInput(0, 1);
		if (choice == 1) {
			showProductsInfoMenu(); 
		} else {
			mainMenu();
		}
	}

	void showAvailableProductsMenu() throws Exception {
		System.out.println("\nДоступные для закупки товары:");
		System.out.println("1. Товар А - от 100 руб./шт.");
		System.out.println("2. Товар Б - от 150 руб./шт.");
		System.out.println("3. Товар В - от 200 руб./шт.");
		System.out.println("0. Вернуться в главное меню");

		int choice = getIntegerInput(0, 3);
		if (choice == 0) {
			mainMenu();
			return;
		}
		
		System.out.println("\nИнформация о товаре:");
		System.out.println("Минимальная партия: 10 шт.");
		System.out.println("Срок поставки: 3-5 дней");
		
		System.out.println("\n1. Вернуться к списку");
		System.out.println("0. Вернуться в главное меню");

		choice = getIntegerInput(0, 1);
		if (choice == 1) {
			showAvailableProductsMenu();
		} else {
			mainMenu();
		}
	}

	void showProfitabilityMenu() throws Exception {
		System.out.println("\nВыберите период для отчета:");
		System.out.println("1. За день");
		System.out.println("2. За неделю");
		System.out.println("3. За месяц");
		System.out.println("0. Вернуться в главное меню");

		int choice = getIntegerInput(0, 3);
		if (choice == 0) {
			mainMenu();
			return;
		}
		
		System.out.println("\nОтчет о доходности:");
		System.out.println("Доход: 50 000 руб.");
		System.out.println("Расходы: 30 000 руб.");
		System.out.println("Прибыль: 20 000 руб.");
		
		System.out.println("\n1. Сформировать другой отчет");
		System.out.println("2. Экспортировать в файл");
		System.out.println("0. Вернуться в главное меню");

		choice = getIntegerInput(0, 2);
		switch (choice) {
			case 1 -> showProfitabilityMenu();
			case 2 -> {
				System.out.println("Отчет успешно экспортирован!");
				mainMenu();
			}
			case 0 -> mainMenu();
		}
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
