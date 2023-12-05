<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
    
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.*" %>
<%@page import= "java.text.DecimalFormat" %>


<%@ page import="com.finalproject.connection.DbCon" %>
<%@ page import="com.finalproject.model.User" %>
<%@ page import="com.finalproject.model.Customer" %>
<%@ page import="com.finalproject.dao.CustomerDao" %>

<%
	User auth = (User)request.getSession().getAttribute("auth");
	int userId = 0;
	if(auth == null)  {
		response.sendRedirect("login.jsp");
	} else{
		userId = auth.getId();
	}
	
	CustomerDao cd = new CustomerDao(DbCon.getConnection());
	List<Customer> customers = cd.getAllCustomers();	
%>

 
<!DOCTYPE html>
<html>
	<head>
		<%@include file="include/header.jsp" %>
		<title>Pastry Shop Internal Application - New Invoice</title>
	</head>
	
	<body>
		<div class="container">
			<%@include file = "include/navbar.jsp"%>
			<div class="container">
				<div class ="card w-50 mx-auto my-5">
				
					<div class = "card-header text-center">New Invoice
					<%-- <button type="submit" class="btn btn-primary rounded-circle">Back</button> --%>
					</div>
					
					<div class ="card-body">
						<form action="invoice-submit-header" method="post">
							<div class = "form-group">
								<label>Customer</label>
								<div class="input-group mb-3">
								  <select class="custom-select" name="invoice-customer" required>
            						<option value="" selected disabled>Choose...</option>
								    <% 
									if(!customers.isEmpty()){
										for(Customer c:customers){%>
								    <option value="<%= c.getId() %>"><%= c.getName() %></option>
								    <%
										}
									}
								    %>
								  </select>
								</div>							
							</div>					
							
							<div class = "form-group">
								<label>Date</label>
								<input type="text" class="form-control" name="invoice-date" placeholder="yyyy/mm/dd" required>
							</div>					
							<div class = "form-group">
								<label>Description</label>
								<input type="text" class="form-control" name="invoice-desc" placeholder="">
							</div>
							<input type="hidden" name="invoice-user" value="<%= userId %>">
							<div class ="text-center">							
								<button type="submit" class="btn btn-primary">Next</button>
							</div>
						</form>
					
					</div>
				
				</div>			
			</div>
		</div>
		<%@include file = "include/footer.jsp"%>
	</body>
</html>