<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
    
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.*" %>


<%@ page import="com.finalproject.connection.DbCon" %>
<%@ page import="com.finalproject.model.Customer" %>
<%@ page import="com.finalproject.dao.CustomerDao" %>

<%
	User auth = (User)request.getSession().getAttribute("auth");
	if(auth == null)  {
		response.sendRedirect("login.jsp");
	}
	
	CustomerDao cd = new CustomerDao(DbCon.getConnection());
	List<Customer> customers = cd.getAllCustomers();	
	int recCount = (customers != null) ? customers.size() : 0;
%>

 
<!DOCTYPE html>
<html>
	<head>
		<%@include file="include/header.jsp" %>
		<title>Pastry Shop Internal Application - Customers</title>
	</head>
	
	<body>
		<div class="container">
			<%@include file = "include/navbar.jsp"%>
			<div class= "container">
				<div class="card-header my-3">All Customers (<%=recCount %>)</div>
				<div class ="row">
				<% 
				if(!customers.isEmpty()){
					for(Customer c:customers){%>
						<div class="col-md-3 my-3">
						<div class="card w-100" style="width: 18rem;">
							  <img src="customer-images/<%= c.getImage() %>" class="card-img-top" alt="...">
							  <div class="card-body">
							    <h5 class="card-title"><%= c.getName() %></h5>
							    <h6 class="price">Number: <%= c.getNumber() %></h6>
			  				</div>
						</div>
					</div>
					
					<% }
				}
				%>
				</div>
			</div>
		</div>		
		<%@include file = "include/footer.jsp"%>
	</body>
</html>