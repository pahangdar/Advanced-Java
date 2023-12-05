package com.finalproject.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import com.finalproject.connection.DbCon;
import com.finalproject.dao.CustomerDao;
import com.finalproject.model.Customer;

@WebServlet("/customer-select")
public class CustomerSelect extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("login.jsp");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		try (PrintWriter out = response.getWriter()){
			int customer_id = Integer.parseInt(request.getParameter("customer_id"));
			String pageRedirect = request.getParameter("page-redirect");
			
			try {
				CustomerDao cdao = new CustomerDao(DbCon.getConnection());
				Customer customer = cdao.getSingleCustomer(customer_id);
				if(customer != null) {
					request.getSession().setAttribute("selected-customer", customer);
					//out.print("user login");
				}else {
					request.getSession().removeAttribute("selected-customer");
					out.print("select customer failed");
				}
				response.sendRedirect(pageRedirect);
				
			}catch(ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}	
	}
	
}


