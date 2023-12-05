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

@WebServlet("/payment-delete")
public class PaymentDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		
		try (PrintWriter out = response.getWriter()){
			
			int pid = Integer.parseInt(request.getParameter("pid"));
			int tid = Integer.parseInt(request.getParameter("tid"));
			try {
				PaymentDao pdao = new PaymentDao(DbCon.getConnection());
				boolean result = pdao.deletePayment(pid, tid);
				if(result) {
					response.sendRedirect("payments.jsp");
				}else {
					out.println("payment delete failed");
				}
				
			}catch(ClassNotFoundException | SQLException | NumberFormatException  e) {
				e.printStackTrace();
				System.out.print(e.getMessage());
		    }
		}
			
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
