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
import com.finalproject.dao.UserDao;
import com.finalproject.model.User;

@WebServlet("/user-login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("login.jsp");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		try (PrintWriter out = response.getWriter()){
			String name = request.getParameter("login-name");
			String password =request.getParameter("login-password");
			try {
				UserDao udao = new UserDao(DbCon.getConnection());
				User user = udao.userLogin(name, password);
				if(user != null) {
					request.getSession().setAttribute("auth", user);
					response.sendRedirect("index.jsp");
					//out.print("user login");
				}else {
					out.print("user login failed");
				}
				
			}catch(ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}	
	}
	
}


