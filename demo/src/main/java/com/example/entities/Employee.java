package com.example.entities;

public class Employee {
	int ID;
	String name;
	String position;
	int salary;

	public Employee() {}
	public Employee(int iD, String name, String position, int salary) {
		ID = iD;
		this.name = name;
		this.position = position;
		this.salary = salary;
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	@Override
	public String toString() {
		return "Employee [ID=" + ID + ", name=" + name + ", position=" + position + ", salary=" + salary + "]";
	} 
}
