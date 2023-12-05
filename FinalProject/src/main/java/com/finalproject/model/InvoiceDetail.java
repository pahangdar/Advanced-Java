package com.finalproject.model;

public class InvoiceDetail {
	private int id;
	private int invoice;
	private int product;
	private double quantity;
	private double price;
	private String productName;
	
	public InvoiceDetail() {	
	}

	public InvoiceDetail(int id, int invoice, int product, double quantity, double price, String productName) {
		super();
		this.id = id;
		this.invoice = invoice;
		this.product = product;
		this.quantity = quantity;
		this.price = price;
		this.productName = productName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getInvoice() {
		return invoice;
	}

	public void setInvoice(int invoice) {
		this.invoice = invoice;
	}

	public int getProduct() {
		return product;
	}

	public void setProduct(int product) {
		this.product = product;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
}
