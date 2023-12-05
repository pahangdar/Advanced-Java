<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
    
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.*" %>
<%@page import= "java.text.DecimalFormat" %>


<%@ page import="com.finalproject.connection.DbCon" %>
<%@ page import="com.finalproject.model.User" %>
<%@ page import="com.finalproject.dao.InvoiceDao" %>
<%@ page import="com.finalproject.dao.PaymentDao" %>
<%@ page import="com.finalproject.dao.ExpenseDao" %>

<%
	User auth = (User)request.getSession().getAttribute("auth");
	if(auth != null)  {
		request.setAttribute("auth", auth);
	} else {
		response.sendRedirect("login.jsp");
	}	
	try {
		Connection connection = DbCon.getConnection();
		//out.print("Connection successful!");
	} catch (ClassNotFoundException | SQLException e) {
		//out.print("Connection failed! Exception: " + e.getMessage());
		e.printStackTrace();
	}
	
	InvoiceDao idao = new InvoiceDao(DbCon.getConnection());
	PaymentDao pdao = new PaymentDao(DbCon.getConnection());
	ExpenseDao edao = new ExpenseDao(DbCon.getConnection());
	
    DecimalFormat dcf = new DecimalFormat("#.##");
    request.setAttribute("dcf", dcf);

%>
 
<!DOCTYPE html>
<html>
	<head>
		<%@include file="include/header.jsp" %>
		<title>Pastry Shop Internal Application</title>
	</head>
	<body>
		<div class="container">
			<%@include file="include/navbar.jsp" %>
			<div class= "container">
				<div class ="row">
				
					<div class="col-md-3 my-3">
						<div class="card w-100" style="width: 18rem;">
						  <img src="images/invoices.png" class="card-img-top" alt="...">
						  <div class="card-body">
						     <h5 class="card-title">Invoices</h5>
						     <h3 class="price">Total: $<%=dcf.format(idao.getTotalInvoices()) %></h3>
		  				  </div>
						</div>
					</div>	
					
					<div class="col-md-3 my-3">
						<div class="card w-100" style="width: 18rem;">
						  <img src="images/payments.png" class="card-img-top" alt="...">
						  <div class="card-body">
						     <h5 class="card-title">Payments</h5>
						     <h3 class="price">Total: $<%=dcf.format(pdao.getTotalPayments()) %></h3>
		  				  </div>
						</div>
					</div>	
					
					<div class="col-md-3 my-3">	
						<div class="card w-100" style="width: 18rem;">
						  <img src="images/expenses.png" class="card-img-top" alt="...">
						  <div class="card-body">
						     <h5 class="card-title">Expenses</h5>
						     <h3 class="price">Total: $<%=dcf.format(edao.getTotalExpenses()) %></h3>
		  				  </div>
						</div>
						
					</div>
				</div>
			</div>
		</div>
		
		<%@include file="include/footer.jsp" %>
	</body>
</html>