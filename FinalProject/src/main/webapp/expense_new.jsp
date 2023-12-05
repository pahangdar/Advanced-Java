<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
    
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.*" %>
<%@page import= "java.text.DecimalFormat" %>


<%@ page import="com.finalproject.connection.DbCon" %>
<%@ page import="com.finalproject.model.User" %>

<%
	User auth = (User)request.getSession().getAttribute("auth");
	int userId = 0;
	if(auth == null)  {
		response.sendRedirect("login.jsp");
	} else{
		userId = auth.getId();
	}
%>

 
<!DOCTYPE html>
<html>
	<head>
		<%@include file="include/header.jsp" %>
		<title>Pastry Shop Internal Application - New Expense</title>
	</head>
	
	<body>
		<div class="container">
			<%@include file = "include/navbar.jsp"%>
			<div class="container">
				<div class ="card w-50 mx-auto my-5">
				
					<div class = "card-header text-center">New Expense
					<%-- <button type="submit" class="btn btn-primary rounded-circle">Back</button> --%>
					</div>
					
					<div class ="card-body">
						<form action="expense-submit" method="post">
							<div class = "form-group">
								<label>Date</label>
								<input type="text" class="form-control" name="expense-date" placeholder="yyyy/mm/dd" required>
							</div>					
							<div class = "form-group">
								<label>Description</label>
								<input type="text" class="form-control" name="expense-desc" placeholder="" required>
							</div>
							<div class = "form-group">
								<label>Amount</label>
								<input type="text" class="form-control" name="expense-amount" placeholder="0.0" required>
							</div>
							<input type="hidden" name="expense-user" value="<%= userId %>">
							<div class ="text-center">							
								<button type="submit" class="btn btn-primary">Submit</button>
							</div>
						</form>
					
					</div>
				
				</div>			
			</div>
		</div>
		<%@include file = "include/footer.jsp"%>
	</body>
</html>