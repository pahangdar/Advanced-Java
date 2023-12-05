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
import com.finalproject.dao.PaymentDao;
import com.finalproject.model.Payment;

@WebServlet("/payment-submit")
public class PaymentSubmit extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.sendRedirect("login.jsp");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		try (PrintWriter out = response.getWriter()){
			String date = request.getParameter("payment-date");
			String desc = request.getParameter("payment-desc");
			double amount = Double.parseDouble(request.getParameter("payment-amount"));
			int customer = Integer.parseInt(request.getParameter("payment-customer"));
			int user = Integer.parseInt(request.getParameter("payment-user"));

			try {
				PaymentDao edao = new PaymentDao(DbCon.getConnection());
				Payment py = new Payment();
				py.setDate(date);
				py.setDesc(desc);
				py.setAmount(amount);
				py.setCustomer(customer);
				py.setUser(user);
				int result = edao.newPayment(py);
				if(result > 0) {
					//request.getSession().setAttribute("auth", user);
					response.sendRedirect("payments.jsp");
					//out.print("user login");
				}else {
					out.print("submit failed");
				}
				
			}catch(ClassNotFoundException | SQLException | NumberFormatException  e) {
				e.printStackTrace();
				System.out.print(e.getMessage());
		    }
		}
			
	}

}
