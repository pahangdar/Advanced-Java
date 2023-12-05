package com.finalproject.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.finalproject.model.InvoiceDetail;

public class InvoiceDetailDao {
	private Connection con;
	private String query;
	private PreparedStatement pst;
	private ResultSet res;
	
	public InvoiceDetailDao(Connection con) {
		this.con = con;
	}
	
	public List<InvoiceDetail> getAllInvoiceDetails(){
		List<InvoiceDetail> invoices = new ArrayList<InvoiceDetail>();
		
		try {
			query ="select * from invoicedetail join product on detail_product = product_id order by detail_id";
			pst = this.con.prepareStatement(query);
			res = pst.executeQuery();
			
			while(res.next()) {
				InvoiceDetail row = new InvoiceDetail();
				row.setId(res.getInt("detail_id"));
				row.setInvoice(res.getInt("detail_invoice"));
				row.setProduct(res.getInt("detail_product"));
				row.setQuantity(res.getDouble("detail_quantity"));
				row.setPrice(res.getDouble("detail_price"));
				row.setProductName(res.getString("product_name"));
				
				invoices.add(row);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return invoices;
	}

	public List<InvoiceDetail> getInvoiceDetails(int iid){
		List<InvoiceDetail> invoices = new ArrayList<InvoiceDetail>();
		
		try {
			query ="select * from invoicedetail join product on detail_product = product_id where detail_invoice = ? order by detail_id";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, iid);
			res = pst.executeQuery();
			
			while(res.next()) {
				InvoiceDetail row = new InvoiceDetail();
				row.setId(res.getInt("detail_id"));
				row.setInvoice(res.getInt("detail_invoice"));
				row.setProduct(res.getInt("detail_product"));
				row.setQuantity(res.getDouble("detail_quantity"));
				row.setPrice(res.getDouble("detail_price"));
				row.setProductName(res.getString("product_name"));
				
				invoices.add(row);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return invoices;
	}

	public InvoiceDetail getSingleInvoiceDetail(int id) {
		InvoiceDetail row = null;
		try {
			query = "select * from invoicedetail join product on detail_product = product_id where detail_id = ?";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, id);
			res = pst.executeQuery();
			while(res.next()) {
				row = new InvoiceDetail();
				row.setId(res.getInt("detail_id"));
				row.setId(res.getInt("detail_id"));
				row.setInvoice(res.getInt("detail_invoice"));
				row.setProduct(res.getInt("detail_product"));
				row.setQuantity(res.getDouble("detail_quantity"));
				row.setPrice(res.getDouble("detail_price"));
				row.setProductName(res.getString("product_name"));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return row;
	}
	
	public double getTotalInvoiceDetails() {
		double total = 0;
		try {
			query = "select sum(detail_quantity * detail_price) as detail_total from invoicedetail";
			pst = this.con.prepareStatement(query);
			res = pst.executeQuery();
			while(res.next()) {
				total += res.getDouble("detail_total");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return total;
	}
	
	public double getTotalInvoice(int iid) {
		double total = 0;
		try {
			query = "select sum(detail_quantity * detail_price) as detail_total from invoicedetail where detail_invoice = ?";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, iid);
			res = pst.executeQuery();
			while(res.next()) {
				total += res.getDouble("detail_total");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return total;
	}
	
	public boolean newInvoiceDetails(List<InvoiceDetail> details) {
		boolean result = false;
	    try {
	    	for(InvoiceDetail d:details) {
		        query = "insert into invoicedetail(detail_invoice, detail_product, detail_quantity, detail_price) values(?, ?, ?, ?)";
		        pst = this.con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		        pst.setInt(1, d.getInvoice());
		        pst.setInt(2, d.getProduct());
		        pst.setDouble(3, d.getQuantity());
		        pst.setDouble(4, d.getPrice());
		        pst.executeUpdate();
		        result = true;
	    	}	    	
	    } catch (Exception e) {
	        result = false;
	    	e.printStackTrace();
	        System.out.print(e.getMessage());
	    }				
	    return result;
	}

	public boolean deleteInvoiceDetail(int iid) {
		boolean result = false;

		try {
			query = "delete from invoicedetail where detail_invoice = ?";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, iid);
			pst.executeUpdate();
			result = true;
		}catch (Exception e) {
			e.printStackTrace();
		}
	    return result;
	}

}
