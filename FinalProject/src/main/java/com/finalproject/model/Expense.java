package com.finalproject.model;

public class Expense {
	private int id;
	private String date;
	private String desc;
	private double amount;
	private int transaction;
	private int user;
	
	public Expense() {
		
	}
	
	public Expense(int id, String date, String desc, double amount, int transaction, int user) {
		super();
		this.id = id;
		this.date = date;
		this.desc = desc;
		this.amount = amount;
		this.transaction = transaction;
		this.user = user;
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

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

}
