package com.finalproject.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.finalproject.connection.DbCon;
import com.finalproject.model.Expense;
import com.finalproject.model.Transaction;
import com.finalproject.dao.TransactionDao;

public class ExpenseDao {
	private Connection con;
	private String query;
	private PreparedStatement pst;
	private ResultSet res;
	
	public ExpenseDao(Connection con) {
		this.con = con;
	}
	
	public List<Expense> getAllExpenses(){
		List<Expense> expenses = new ArrayList<Expense>();
		
		try {
			query ="select * from expense order by expense_date desc, expense_id desc";
			pst = this.con.prepareStatement(query);
			res = pst.executeQuery();
			
			while(res.next()) {
				Expense row = new Expense();
				row.setId(res.getInt("expense_id"));
				row.setDate(res.getString("expense_date"));
				row.setDesc(res.getString("expense_desc"));
				row.setAmount(res.getDouble("expense_amount"));
				row.setTransaction(res.getInt("expense_transaction"));
				row.setUser(res.getInt("expense_user"));
				
				expenses.add(row);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return expenses;
	}

	public Expense getSingleExpense(int id) {
		Expense row = null;
		try {
			query = "select * from expense where expense_id=?";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, id);
			res = pst.executeQuery();
			while(res.next()) {
				row = new Expense();
				row.setId(res.getInt("expense_id"));
				row.setDate(res.getString("expense_date"));
				row.setDesc(res.getString("expense_desc"));
				row.setAmount(res.getDouble("expense_amount"));
				row.setTransaction(res.getInt("expense_transaction"));
				row.setUser(res.getInt("expense_user"));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return row;
	}
	
	public double getTotalExpenses() {
		double total = 0;
		try {
			query = "select sum(expense_amount) as expense_total from expense";
			pst = this.con.prepareStatement(query);
			res = pst.executeQuery();
			while(res.next()) {
				total += res.getDouble("expense_total");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return total;
	}
	
	public int newExpense(Expense ex) {
	    
		int generatedId = -1; // Initialize with a default value indicating failure

		try {
			TransactionDao tdao = new TransactionDao(DbCon.getConnection());
			Transaction t = new Transaction();
			t.setDate(ex.getDate());
			t.setDesc(ex.getDesc());
			t.setAmount(ex.getAmount());
			t.setCustomer(-1);
			t.setMethod(-1);
			t.setType(3);
			t.setUser(ex.getUser());
			int newTransactionID = tdao.newTransaction(t);
			if(newTransactionID > 0) {
				ex.setTransaction(newTransactionID);
			    try {
			        query = "insert into expense(expense_date, expense_desc, expense_amount, expense_transaction, expense_user) values(?, ?, ?, ?, ?)";
			        pst = this.con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			        pst.setString(1, ex.getDate());
			        pst.setString(2, ex.getDesc());
			        pst.setDouble(3, ex.getAmount());
			        pst.setInt(4, ex.getTransaction());
			        pst.setInt(5, ex.getUser());
			        pst.executeUpdate();
			        
			        // Retrieve the generated keys
			        ResultSet generatedKeys = pst.getGeneratedKeys();
			        
			        if (generatedKeys.next()) {
			            // Retrieve the auto-generated key (expense_id)
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

	public boolean deleteExpense(int eid, int tid) {
		boolean resultExp = false;
		boolean result = false;

		try {
			query = "delete from expense where expense_id = ?";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, eid);
			pst.executeUpdate();
			resultExp = true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		if(resultExp) {
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
