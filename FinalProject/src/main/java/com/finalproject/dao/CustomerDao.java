package com.finalproject.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import com.finalproject.model.Customer;


public class CustomerDao {
	private Connection con;
	private String query;
	private PreparedStatement pst;
	private ResultSet res;
	
	public CustomerDao(Connection con) {
		this.con = con;
	}
	
	public List<Customer> getAllCustomers(){
		List<Customer> customers = new ArrayList<Customer>();
		
		try {
			query ="select * from customer order by customer_name";
			pst = this.con.prepareStatement(query);
			res = pst.executeQuery();
			
			while(res.next()) {
				Customer row = new Customer();
				row.setId(res.getInt("customer_id"));
				row.setName(res.getString("customer_name"));
				row.setLocation(res.getString("customer_location"));
				row.setNumber(res.getString("customer_number"));
				row.setImage(res.getString("customer_image"));
				
				customers.add(row);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return customers;
	}

	public Customer getSingleCustomer(int id) {
		Customer row = null;
		try {
			query = "select * from customer where customer_id=?";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, id);
			res = pst.executeQuery();
			while(res.next()) {
				row = new Customer();
				row.setId(res.getInt("customer_id"));
				row.setName(res.getString("customer_name"));
				row.setLocation(res.getString("customer_location"));
				row.setNumber(res.getString("customer_number"));
				row.setImage(res.getString("customer_image"));
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}
	
}
