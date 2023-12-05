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
import com.finalproject.dao.ExpenseDao;

@WebServlet("/expense-delete")
public class ExpenseDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		
		try (PrintWriter out = response.getWriter()){
			
			int eid = Integer.parseInt(request.getParameter("eid"));
			int tid = Integer.parseInt(request.getParameter("tid"));
			try {
				ExpenseDao edao = new ExpenseDao(DbCon.getConnection());
				boolean result = edao.deleteExpense(eid, tid);
				if(result) {
					response.sendRedirect("expenses.jsp");
				}else {
					out.println("expense delete failed");
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
