package com.finalproject.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.finalproject.connection.DbCon;
import com.finalproject.model.Payment;
import com.finalproject.model.Transaction;
import com.finalproject.dao.TransactionDao;

public class PaymentDao {
	private Connection con;
	private String query;
	private PreparedStatement pst;
	private ResultSet res;
	
	public PaymentDao(Connection con) {
		this.con = con;
	}
	
	public List<Payment> getAllPayments(){
		List<Payment> payments = new ArrayList<Payment>();
		
		try {
			query ="select * from payment join customer on payment_customer = customer_id order by payment_date desc, payment_id desc";
			pst = this.con.prepareStatement(query);
			res = pst.executeQuery();
			
			while(res.next()) {
				Payment row = new Payment();
				row.setId(res.getInt("payment_id"));
				row.setDate(res.getString("payment_date"));
				row.setDesc(res.getString("payment_desc"));
				row.setAmount(res.getDouble("payment_amount"));
				row.setTransaction(res.getInt("payment_transaction"));
				row.setCustomer(res.getInt("payment_customer"));
				row.setUser(res.getInt("payment_user"));
				row.setCustomerName(res.getString("customer_name"));
				
				payments.add(row);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return payments;
	}

	public List<Payment> getCustomerPayments(int cid){
		List<Payment> payments = new ArrayList<Payment>();
		
		try {
			query ="select * from payment join customer on payment_customer = customer_id where payment_customer = ? order by payment_date desc, payment_id desc";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, cid);
			res = pst.executeQuery();
			
			while(res.next()) {
				Payment row = new Payment();
				row.setId(res.getInt("payment_id"));
				row.setDate(res.getString("payment_date"));
				row.setDesc(res.getString("payment_desc"));
				row.setAmount(res.getDouble("payment_amount"));
				row.setTransaction(res.getInt("payment_transaction"));
				row.setCustomer(res.getInt("payment_customer"));
				row.setUser(res.getInt("payment_user"));
				row.setCustomerName(res.getString("customer_name"));
				
				payments.add(row);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return payments;
	}

	public Payment getSinglePayment(int id) {
		Payment row = null;
		try {
			query = "select * from payment join customer on payment_customer = customer_id where payment_id=?";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, id);
			res = pst.executeQuery();
			while(res.next()) {
				row = new Payment();
				row.setId(res.getInt("payment_id"));
				row.setDate(res.getString("payment_date"));
				row.setDesc(res.getString("payment_desc"));
				row.setAmount(res.getDouble("payment_amount"));
				row.setTransaction(res.getInt("payment_transaction"));
				row.setCustomer(res.getInt("payment_customer"));
				row.setUser(res.getInt("payment_user"));
				row.setCustomerName(res.getString("customer_name"));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return row;
	}
	
	public double getTotalPayments() {
		double total = 0;
		try {
			query = "select sum(payment_amount) as payment_total from payment";
			pst = this.con.prepareStatement(query);
			res = pst.executeQuery();
			while(res.next()) {
				total += res.getDouble("payment_total");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return total;
	}
	
	public double getTotalCustomer(int cid) {
		double total = 0;
		try {
			query = "select sum(payment_amount) as payment_total from payment where payment_customer = ?";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, cid);
			res = pst.executeQuery();
			while(res.next()) {
				total += res.getDouble("payment_total");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return total;
	}
	
	public int newPayment(Payment py) {
	    
		int generatedId = -1; // Initialize with a default value indicating failure

		try {
			TransactionDao tdao = new TransactionDao(DbCon.getConnection());
			Transaction t = new Transaction();
			t.setDate(py.getDate());
			t.setDesc(py.getDesc());
			t.setAmount(py.getAmount());
			t.setCustomer(py.getCustomer());
			t.setMethod(1);
			t.setType(2);
			t.setUser(py.getUser());
			int newTransactionID = tdao.newTransaction(t);
			if(newTransactionID > 0) {
				py.setTransaction(newTransactionID);
			    try {
			        query = "insert into payment(payment_date, payment_desc, payment_amount, payment_transaction, payment_customer, payment_user) values(?, ?, ?, ?, ?, ?)";
			        pst = this.con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			        pst.setString(1, py.getDate());
			        pst.setString(2, py.getDesc());
			        pst.setDouble(3, py.getAmount());
			        pst.setInt(4, py.getTransaction());
			        pst.setInt(5, py.getCustomer());
			        pst.setInt(6, py.getUser());
			        pst.executeUpdate();
			        
			        // Retrieve the generated keys
			        ResultSet generatedKeys = pst.getGeneratedKeys();
			        
			        if (generatedKeys.next()) {
			            // Retrieve the auto-generated key (payment_id)
			        	generatedId = generatedKeys.getInt(1);
			        }
			        
			        // Close the ResultSet
			        generatedKeys.close();
			    } catch (Exception e) {
			        tdao.deleteTransaction(newTransactionID);
			    	e.printStackTrace();
			        System.out.print(e.getMessage());
			    }				
			}
			
		}catch(ClassNotFoundException | SQLException | NumberFormatException  e) {
			e.printStackTrace();
	    }
	    return generatedId;
	}

	public boolean deletePayment(int pid, int tid) {
		boolean resultPay = false;
		boolean result = false;

		try {
			query = "delete from payment where payment_id = ?";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, pid);
			pst.executeUpdate();
			resultPay = true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		if(resultPay) {
			try {
				TransactionDao tdao = new TransactionDao(DbCon.getConnection());
				result = tdao.deleteTransaction(tid);
			}catch(Exception  e) {
				e.printStackTrace();
		    }
			
		}
	    return result;
	}

}
