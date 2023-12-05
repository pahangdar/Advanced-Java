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
import com.finalproject.dao.TransactionDao;

@WebServlet("/transaction-delete")
public class TransactionDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try(PrintWriter out = response.getWriter()){
			String tid = request.getParameter("tid");
				if(tid !=null) {
					TransactionDao tdao = new TransactionDao(DbCon.getConnection());
					tdao.deleteTransaction(Integer.parseInt(tid));
				}
				response.sendRedirect("transactions.jsp");
				
			}catch(ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}	
				
	}
}


