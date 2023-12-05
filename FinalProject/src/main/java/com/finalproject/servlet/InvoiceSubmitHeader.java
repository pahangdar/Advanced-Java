package com.finalproject.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import com.finalproject.connection.DbCon;
import com.finalproject.model.Invoice;
import com.finalproject.dao.CustomerDao;
import com.finalproject.model.Customer;
import com.finalproject.dao.ProductDao;
import com.finalproject.model.Product;
import com.finalproject.model.InvoiceDetail;

@WebServlet("/invoice-submit-header")
public class InvoiceSubmitHeader extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("login.jsp");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		try (PrintWriter out = response.getWriter()){
			int customer_id = Integer.parseInt(request.getParameter("invoice-customer"));
			String date =request.getParameter("invoice-date");
			String desc =request.getParameter("invoice-desc");
			int user = Integer.parseInt(request.getParameter("invoice-user"));
			
			try {
				Invoice invoice = new Invoice();
				invoice.setDate(date);
				invoice.setDesc(desc);
				invoice.setCustomer(customer_id);
				invoice.setUser(user);
				request.getSession().setAttribute("invoice", invoice);
				
				CustomerDao cdao = new CustomerDao(DbCon.getConnection());
				Customer customer = cdao.getSingleCustomer(customer_id);
				request.getSession().setAttribute("customer", customer);
				
				ProductDao pdao = new ProductDao(DbCon.getConnection());
				List<Product> products = pdao.getAllProducts();
				request.getSession().setAttribute("products", products);
				
				request.getSession().removeAttribute("details");
				//List<InvoiceDetail> details = new ArrayList<InvoiceDetail>();
				//request.getSession().setAttribute("details", details);
				
				response.sendRedirect("invoice_product.jsp");
			} catch(Exception e) {
				out.print("invoice header failed");
			}
		}	
	}
	
}
