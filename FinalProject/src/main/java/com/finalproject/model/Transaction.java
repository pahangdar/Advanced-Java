package com.finalproject.model;

public class Transaction {
	private int id;
	private String date;
	private String desc;
	private double amount;
	private int customer;
	private int method;
	private int type;
	private int user;
	private String customerName;
	private String typeName;
	
	public Transaction() {
		
	}

	public Transaction(int id, String date, String desc, double amount, int customer, int method, int type, int user) {
		super();
		this.id = id;
		this.date = date;
		this.desc = desc;
		this.amount = amount;
		this.customer = customer;
		this.method = method;
		this.type = type;
		this.user = user;
	}

	public double getDebitAmount() {
		return (this.method == -1) ? amount : 0;
	}

	public double getCreditAmount() {
		return (this.method == 1) ? amount : 0;
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

	public int getCustomer() {
		return customer;
	}

	public void setCustomer(int customer) {
		this.customer = customer;
	}

	public int getMethod() {
		return method;
	}

	public void setMethod(int method) {
		this.method = method;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}
