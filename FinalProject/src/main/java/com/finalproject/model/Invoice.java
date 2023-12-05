package com.finalproject.model;

public class Invoice {
	private int id;
	private String date;
	private String desc;
	private double amount;
	private int transaction;
	private int customer;
	private int user;
	private String customerName;

	public Invoice() {
		
	}

	public Invoice(int id, String date, String desc, double amount, int transaction, int customer, int user,
			String customerName) {
		super();
		this.id = id;
		this.date = date;
		this.desc = desc;
		this.amount = amount;
		this.transaction = transaction;
		this.customer = customer;
		this.user = user;
		this.customerName = customerName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getTransaction() {
		return transaction;
	}

	public void setTransaction(int transaction) {
		this.transaction = transaction;
	}

	public int getCustomer() {
		return customer;
	}

	public void setCustomer(int customer) {
		this.customer = customer;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	
}
