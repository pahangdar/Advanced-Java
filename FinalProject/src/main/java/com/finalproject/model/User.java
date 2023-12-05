package com.finalproject.model;

public class User {
	private int id;
	private String name;
	private String password;
	private String desc;
	private int role;
	private int shop;
	private String shopName;
	
	public User() {
		
	}
	
	public User(int id, String name, String password, String desc, int role, int shop, String shopName) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.desc = desc;
		this.role = role;
		this.shop = shop;
		this.shopName = shopName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public int getShop() {
		return shop;
	}

	public void setShop(int shop) {
		this.shop = shop;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	
}
