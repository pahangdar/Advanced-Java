package com.finalproject.model;

public class Product {
	private int id;
	private String name;
	private double price;
	private String image;
	private double level;
	private int user;
	private String scaleName;
	
	public Product() {
		super();
	}
	
	public Product(int id, String name, double price, String image, double level,int user, String scaleName) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.image = image;
		this.level = level;
		this.user = user;
		this.scaleName = scaleName;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public double getLevel() {
		return level;
	}

	public void setLevel(double level) {
		this.level = level;
	}

	public String getScaleName() {
		return scaleName;
	}

	public void setScaleName(String scaleName) {
		this.scaleName = scaleName;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}
	
}
