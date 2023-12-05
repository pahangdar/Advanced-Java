package com.finalproject.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import com.finalproject.model.Product;


public class ProductDao {
	private Connection con;
	private String query;
	private PreparedStatement pst;
	private ResultSet res;
	
	public ProductDao(Connection con) {
		this.con = con;
	}
	
	public List<Product> getAllProducts(){
		List<Product> products = new ArrayList<Product>();
		
		try {
			query ="select * from product join scale on product_scale = scale_id order by product_name";
			pst = this.con.prepareStatement(query);
			res = pst.executeQuery();
			
			while(res.next()) {
				Product row = new Product();
				row.setId(res.getInt("product_id"));
				row.setName(res.getString("product_name"));
				row.setPrice(res.getDouble("product_price"));
				row.setImage(res.getString("product_image"));
				row.setLevel(res.getDouble("product_level"));
				row.setUser(res.getInt("product_user"));
				row.setScaleName(res.getString("scale_name"));
				
				products.add(row);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return products;
	}

	public Product getSingleProduct(int id) {
		Product row = null;
		try {
			query = "select * from product join scale on product_scale = scale_id where product_id=?";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, id);
			res = pst.executeQuery();
			while(res.next()) {
				row = new Product();
				row.setId(res.getInt("product_id"));
				row.setName(res.getString("product_name"));
				row.setPrice(res.getDouble("product_price"));
				row.setImage(res.getString("product_image"));
				row.setLevel(res.getDouble("product_level"));
				row.setUser(res.getInt("product_user"));
				row.setScaleName(res.getString("scale_name"));
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}
	
}
