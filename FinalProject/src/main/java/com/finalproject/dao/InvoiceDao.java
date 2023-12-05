package com.finalproject.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.finalproject.connection.DbCon;
import com.finalproject.model.Invoice;
import com.finalproject.model.Transaction;
import com.finalproject.dao.InvoiceDetailDao;

public class InvoiceDao {
	private Connection con;
	private String query;
	private PreparedStatement pst;
	private ResultSet res;
	
	public InvoiceDao(Connection con) {
		this.con = con;
	}
	
	public List<Invoice> getAllInvoices(){
		List<Invoice> invoices = new ArrayList<Invoice>();
		
		try {
			query ="select * from invoice join customer on invoice_customer = customer_id order by invoice_date desc, invoice_id desc";
			pst = this.con.prepareStatement(query);
			res = pst.executeQuery();
			
			while(res.next()) {
				Invoice row = new Invoice();
				row.setId(res.getInt("invoice_id"));
				row.setDate(res.getString("invoice_date"));
				row.setDesc(res.getString("invoice_desc"));
				row.setAmount(res.getDouble("invoice_amount"));
				row.setTransaction(res.getInt("invoice_transaction"));
				row.setCustomer(res.getInt("invoice_customer"));
				row.setUser(res.getInt("invoice_user"));
				row.setCustomerName(res.getString("customer_name"));
				
				invoices.add(row);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return invoices;
	}

	public List<Invoice> getCustomerInvoices(int cid){
		List<Invoice> invoices = new ArrayList<Invoice>();
		
		try {
			query ="select * from invoice join customer on invoice_customer = customer_id where invoice_customer = ? order by invoice_date desc, invoice_id desc";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, cid);
			res = pst.executeQuery();
			
			while(res.next()) {
				Invoice row = new Invoice();
				row.setId(res.getInt("invoice_id"));
				row.setDate(res.getString("invoice_date"));
				row.setDesc(res.getString("invoice_desc"));
				row.setAmount(res.getDouble("invoice_amount"));
				row.setTransaction(res.getInt("invoice_transaction"));
				row.setCustomer(res.getInt("invoice_customer"));
				row.setUser(res.getInt("invoice_user"));
				row.setCustomerName(res.getString("customer_name"));
				
				invoices.add(row);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return invoices;
	}

	public Invoice getSingleInvoice(int id) {
		Invoice row = null;
		try {
			query = "select * from invoice join customer on invoice_customer = customer_id where invoice_id=?";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, id);
			res = pst.executeQuery();
			while(res.next()) {
				row = new Invoice();
				row.setId(res.getInt("invoice_id"));
				row.setDate(res.getString("invoice_date"));
				row.setDesc(res.getString("invoice_desc"));
				row.setAmount(res.getDouble("invoice_amount"));
				row.setTransaction(res.getInt("invoice_transaction"));
				row.setCustomer(res.getInt("invoice_customer"));
				row.setUser(res.getInt("invoice_user"));
				row.setCustomerName(res.getString("customer_name"));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return row;
	}
	
	public double getTotalInvoices() {
		double total = 0;
		try {
			query = "select sum(invoice_amount) as invoice_total from invoice";
			pst = this.con.prepareStatement(query);
			res = pst.executeQuery();
			while(res.next()) {
				total += res.getDouble("invoice_total");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return total;
	}
	
	public double getTotalCustomer(int cid) {
		double total = 0;
		try {
			query = "select sum(invoice_amount) as invoice_total from invoice where invoice_customer = ?";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, cid);
			res = pst.executeQuery();
			while(res.next()) {
				total += res.getDouble("invoice_total");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return total;
	}
	
	public int newInvoice(Invoice inv) {
	    
		int generatedId = -1; // Initialize with a default value indicating failure

		try {
			TransactionDao tdao = new TransactionDao(DbCon.getConnection());
			Transaction t = new Transaction();
			t.setDate(inv.getDate());
			t.setDesc(inv.getDesc());
			t.setAmount(inv.getAmount());
			t.setCustomer(inv.getCustomer());
			t.setMethod(-1);
			t.setType(1);
			t.setUser(inv.getUser());
			int newTransactionID = tdao.newTransaction(t);
			if(newTransactionID > 0) {
				inv.setTransaction(newTransactionID);
			    try {
			        query = "insert into invoice(invoice_date, invoice_desc, invoice_amount, invoice_transaction, invoice_customer, invoice_user) values(?, ?, ?, ?, ?, ?)";
			        pst = this.con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			        pst.setString(1, inv.getDate());
			        pst.setString(2, inv.getDesc());
			        pst.setDouble(3, inv.getAmount());
			        pst.setInt(4, inv.getTransaction());
			        pst.setInt(5, inv.getCustomer());
			        pst.setInt(6, inv.getUser());
			        pst.executeUpdate();
			        
			        // Retrieve the generated keys
			        ResultSet generatedKeys = pst.getGeneratedKeys();
			        
			        if (generatedKeys.next()) {
			            // Retrieve the auto-generated key (invoice_id)
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

	public boolean deleteInvoice(Invoice inv) {
		boolean resultInv = false;
		boolean result = false;

		try {
			InvoiceDetailDao ddao = new InvoiceDetailDao(DbCon.getConnection());
			resultInv =ddao.deleteInvoiceDetail(inv.getId());
			if(resultInv) {
				resultInv = false;
				query = "delete from invoice where invoice_id = ?";
				pst = this.con.prepareStatement(query);
				pst.setInt(1, inv.getId());
				pst.executeUpdate();
				resultInv = true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		if(resultInv) {
			try {
				TransactionDao tdao = new TransactionDao(DbCon.getConnection());
				result = tdao.deleteTransaction(inv.getTransaction());
			}catch(Exception  e) {
				e.printStackTrace();
		    }
			
		}
	    return result;
	}

}
