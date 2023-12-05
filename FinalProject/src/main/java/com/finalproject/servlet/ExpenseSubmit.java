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
import com.finalproject.model.Expense;

@WebServlet("/expense-submit")
public class ExpenseSubmit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.sendRedirect("login.jsp");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		try (PrintWriter out = response.getWriter()){
			String date = request.getParameter("expense-date");
			String desc = request.getParameter("expense-desc");
			double amount = Double.parseDouble(request.getParameter("expense-amount"));
			int user = Integer.parseInt(request.getParameter("expense-user"));

			try {
				ExpenseDao edao = new ExpenseDao(DbCon.getConnection());
				Expense ex = new Expense();
				ex.setDate(date);
				ex.setDesc(desc);
				ex.setAmount(amount);
				ex.setUser(user);
				int result = edao.newExpense(ex);
				if(result > 0) {
					//request.getSession().setAttribute("auth", user);
					response.sendRedirect("expenses.jsp");
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
