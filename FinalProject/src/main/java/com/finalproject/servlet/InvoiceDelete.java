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
import com.finalproject.dao.InvoiceDao;
import com.finalproject.model.Invoice;

@WebServlet("/invoice-delete")
public class InvoiceDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		
		try (PrintWriter out = response.getWriter()){
			
			int iid = Integer.parseInt(request.getParameter("pid"));
			try {
				InvoiceDao idao = new InvoiceDao(DbCon.getConnection());
				Invoice inv = idao.getSingleInvoice(iid);
				boolean result = idao.deleteInvoice(inv);
				if(result) {
					response.sendRedirect("invoices.jsp");
				}else {
					out.println("invoice delete failed");
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
