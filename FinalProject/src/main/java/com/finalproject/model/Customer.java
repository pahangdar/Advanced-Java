package com.finalproject.model;

public class Customer {
	private int id;
	private String name;
	private String location;
	private String number;
	private String image;
	private int user;
	
	public Customer() {
		super();
	}

	public Customer(int id, String name, String location, String number, String image, int user) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.number = number;
		this.image = image;
		this.user = user;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}
	
}
