package com.example.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import com.example.exceptions.NoEnoughSpaceException;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Warehouse {
	int responsibleID;
	int ID;
	int maxAmountOfCells;
	Cell[] cells;

	public Warehouse() {}
	public Warehouse(int ID, int responsibleID, int maxAmountOfCells) {
		this.ID = ID;
		cells = new Cell[maxAmountOfCells];
		for (int i = 0; i < maxAmountOfCells; i++) {
			cells[i] = new Cell(i);
		}
		this.maxAmountOfCells = maxAmountOfCells;
	}	
	
	public int countFreeCells() {
		int count = 0;
		for (int i = 0; i < maxAmountOfCells; i++) {
			if (!cells[i].isFull()) {
				count++;
			}
		}
		return count;
	}

	public int addItemToFreeCell(Item item) throws NoEnoughSpaceException {
		for (int i = 0; i < maxAmountOfCells; i++) {
			if (!cells[i].isFull()) {
				cells[i].setItem(item);
				return i;
			}
		}
		throw new NoEnoughSpaceException("no space " + maxAmountOfCells);
	}

	public void replaceResponsible(int employeeID) {
		responsibleID = employeeID;
	}

	public void freeCell(int ind) throws IndexOutOfBoundsException {
		if (ind > maxAmountOfCells && ind < 0) {
			throw new IndexOutOfBoundsException(ind);
		}

		cells[ind].free();
	}

	public int countProduct(int productID) {
		int count = 0;
		for (Cell cell: cells) {
			if (cell.isFull() && cell.getItem().getProductID() == productID) {
				count++;
			}
		}
		return count;
	}


	public int retrieveFirstItemID() throws NoSuchElementException {
		for (Cell cell: cells) {
			if (cell.isFull()) {
				return cell.getItem().getID();
			}
		}
		throw new NoSuchElementException();
	}

	@JsonIgnore
	public int getFirstItemIDByProduct(int productID) throws NoSuchElementException {
		for (Cell cell: cells) {
			if (cell.isFull() && cell.getItem().getProductID() == productID) {
				return cell.getItem().getID();
			}
		}
		throw new NoSuchElementException();
	}

	public HashMap<Integer, Integer> countAvailableProducts() {
		HashMap<Integer, Integer> productsTaken = new HashMap<>();
		for (Cell cell: cells) {
			if (cell.isFull()) {
				int productID = cell.getItem().getProductID();
				Integer count = productsTaken.get(productID);
				if (count == null) {
					count = 0;
				}
				productsTaken.put(productID, count+1);
			}
		}
		return productsTaken;
	}

	@JsonIgnore
	public int getCellIdByItem(Item item) throws NoSuchElementException {
		for (Cell cell: cells) {
			if (cell.isFull() && cell.getItem().getID() == item.getID()) {
				cell.setItem(item);
				return cell.getID();
			}
		}
		throw new NoSuchElementException();
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getMaxAmountOfCells() {
		return maxAmountOfCells;
	}

	public void setMaxAmountOfCells(int maxAmountOfCells) {
		this.maxAmountOfCells = maxAmountOfCells;
	}

	public Cell[] getCells() {
		return cells;
	}

	public static class Cell {
		int ID;
		Item item;
		boolean isFull = false;

		public Cell() {}
		public Cell(int ID) {
			this.ID = ID;
		}

		public Cell(int ID, Item item) {
			this.ID = ID;
			this.item = item;
			this.isFull = true;
		}

		public void free() {
			this.item = null;
			this.isFull = false;
		}

		public void setItem(Item item) {
			this.item = item;
			this.isFull = true;
		}

		public Item getItem() {
			return item;
		}

		public boolean isFull() {
			return isFull;
		}

		public int getID() {
			return ID;
		}

		@Override
		public String toString() {
			if (item == null) {
				return "null";
			}
			return item.toString();
		}
		public void setID(int iD) {
			ID = iD;
		}
		public void setFull(boolean isFull) {
			this.isFull = isFull;
		}
	}

	public void setCells(Cell[] cells) {
		this.cells = cells;
	}

	@Override
	public String toString() {
		return "Warehouse [ID=" + ID + ", maxAmountOfCells=" + maxAmountOfCells + "]";
	}

	public String toAdvancedString() {
		return "Warehouse [ID=" + ID + ", maxAmountOfCells=" + ", responsible=" + responsibleID + maxAmountOfCells + ", cells=" + Arrays.toString(cells)
				+ "]";
	}

	@JsonIgnore
	public List<Item> getAllItems() {
		List<Item> l = new ArrayList<>();

		for (Cell cell: cells) {
			if (cell.isFull()) {
				l.add(cell.getItem());
			}
		}
		return l;
	}

	public int getResponsibleID() {
		return responsibleID;
	}
	public void setResponsibleID(int responsibleID) {
		this.responsibleID = responsibleID;
	}
}
