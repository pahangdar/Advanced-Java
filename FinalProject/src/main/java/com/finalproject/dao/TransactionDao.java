package com.finalproject.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.*;

import com.finalproject.model.Transaction;

public class TransactionDao {
	private Connection con;
	private String query;
	private PreparedStatement pst;
	private ResultSet res;
	
	public TransactionDao(Connection con) {
		this.con = con;
	}
	
	public List<Transaction> getAllTransactions(){
		List<Transaction> transactions = new ArrayList<Transaction>();
		
		try {
			query ="select * from transaction left join customer on transaction_customer = customer_id join transactiontype on transaction_type=type_id  order by transaction_date, transaction_id";
			pst = this.con.prepareStatement(query);
			res = pst.executeQuery();
			
			while(res.next()) {
				Transaction row = new Transaction();
				row.setId(res.getInt("transaction_id"));
				row.setDate(res.getString("transaction_date"));
				row.setDesc(res.getString("transaction_desc"));
				row.setAmount(res.getDouble("transaction_amount"));
				row.setCustomer(res.getInt("transaction_customer"));
				row.setMethod(res.getInt("transaction_method"));
				row.setType(res.getInt("transaction_type"));
				row.setUser(res.getInt("transaction_user"));
				if(res.getString("customer_name") == null) {
					row.setCustomerName("");
				} else {
					row.setCustomerName(res.getString("customer_name"));					
				}
				row.setTypeName(res.getString("type_name"));
				
				transactions.add(row);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return transactions;
	}

	public List<Transaction> getCustomerTransactions(int cid){
		List<Transaction> transactions = new ArrayList<Transaction>();
		
		try {
			query ="select * from transaction left join customer on transaction_customer = customer_id join transactiontype on transaction_type=type_id where transaction_customer = ? order by transaction_date, transaction_id";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, cid);
			res = pst.executeQuery();
			
			while(res.next()) {
				Transaction row = new Transaction();
				row.setId(res.getInt("transaction_id"));
				row.setDate(res.getString("transaction_date"));
				row.setDesc(res.getString("transaction_desc"));
				row.setAmount(res.getDouble("transaction_amount"));
				row.setCustomer(res.getInt("transaction_customer"));
				row.setMethod(res.getInt("transaction_method"));
				row.setType(res.getInt("transaction_type"));
				row.setUser(res.getInt("transaction_user"));
				if(res.getString("customer_name") == null) {
					row.setCustomerName("");
				} else {
					row.setCustomerName(res.getString("customer_name"));					
				}
				row.setTypeName(res.getString("type_name"));
				
				transactions.add(row);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return transactions;
	}

	public Transaction getSingleTransaction(int id) {
		Transaction row = null;
		try {
			query = "select * from transaction left join customer on transaction_customer = customer_id join transactiontype on transaction_type=type_id where transaction_id=?";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, id);
			res = pst.executeQuery();
			while(res.next()) {
				row.setId(res.getInt("transaction_id"));
				row.setDate(res.getString("transaction_date"));
				row.setDesc(res.getString("transaction_desc"));
				row.setAmount(res.getDouble("transaction_amount"));
				row.setCustomer(res.getInt("transaction_customer"));
				row.setMethod(res.getInt("transaction_method"));
				row.setType(res.getInt("transaction_type"));
				row.setUser(res.getInt("transaction_user"));
				if(res.getString("customer_name") == null) {
					row.setCustomerName("");
				} else {
					row.setCustomerName(res.getString("customer_name"));					
				}
				row.setTypeName(res.getString("type_name"));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return row;
	}
	
	public double getTotalTransactions() {
		double total = 0;
		try {
			query = "select sum(transaction_amount*transaction_method) as transaction_total from transaction";
			pst = this.con.prepareStatement(query);
			res = pst.executeQuery();
			while(res.next()) {
				total += res.getDouble("transaction_total");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return total;
	}
	
	public double getTotalCustomer(int cid) {
		double total = 0;
		try {
			query = "select sum(transaction_amount*transaction_method) as transaction_total from transaction where transaction_customer = ?";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, cid);
			res = pst.executeQuery();
			while(res.next()) {
				total += res.getDouble("transaction_total");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return total;
	}
	
	
	public boolean deleteTransaction(int tid) {
	    boolean result = false;
	    try {
	        query = "delete from transaction where transaction_id = ?";
	        pst = this.con.prepareStatement(query);
	        pst.setInt(1, tid);
	         pst.executeUpdate();  // Use executeUpdate instead of executeQuery
	        result = true;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return result;
	}
	
	public int newTransaction(Transaction t) {
	    int generatedId = -1;

	    try {
			query = "insert into transaction(transaction_date, transaction_desc, transaction_amount, transaction_customer, transaction_method, transaction_type, transaction_user) values(? ,?, ?, ?, ?, ?, ?)";
	        pst = this.con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, t.getDate());
			pst.setString(2, t.getDesc());
			pst.setDouble(3, t.getAmount());
			//pst.setInt(4, t.getCustomer());
			if (t.getCustomer() != -1) {
	            pst.setInt(4, t.getCustomer());
	        } else {
	            pst.setNull(4, Types.INTEGER);
	        }
			pst.setInt(5, t.getMethod());
			pst.setInt(6, t.getType());
			pst.setInt(7, t.getUser());
			pst.executeUpdate();

	        ResultSet generatedKeys = pst.getGeneratedKeys();
	        
	        if (generatedKeys.next()) {
	        	generatedId = generatedKeys.getInt(1);
	        }
	        
	        generatedKeys.close();

		}catch (Exception e) {
			e.printStackTrace();
			System.out.print(e.getMessage());
		}
		return generatedId;		
	}

}
