package com.finalproject.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.finalproject.model.User;



public class UserDao {
	
	private Connection con;
	private String query;
	private PreparedStatement pst;
	private ResultSet res;
	
	public UserDao(Connection con) {
		this.con = con;
	}
	
	public User userLogin(String name, String password) {
		User user = null;
		try {
			query = "select * from user join shop on user_shop = shop_id where user_name=? and user_password=?";
			pst = this.con.prepareStatement(query);
			pst.setString(1, name);
			pst.setString(2, password);
			res = pst.executeQuery();
			
			if(res.next()) {
				user = new User();
				user.setId(res.getInt("user_id"));
				user.setName(res.getString("user_name"));
				user.setPassword(res.getString("user_password"));
				user.setDesc(res.getString("user_desc"));
				user.setRole(res.getInt("user_role"));
				user.setShop(res.getInt("user_shop"));
				user.setShopName(res.getString("shop_name"));
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			System.out.print(e.getMessage());
		}
		return user;
	}
	

}


