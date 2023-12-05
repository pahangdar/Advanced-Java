<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
    
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.*" %>
<%@page import= "java.text.DecimalFormat" %>


<%@ page import="com.finalproject.connection.DbCon" %>
<%@ page import="com.finalproject.model.User" %>
<%@ page import="com.finalproject.model.Transaction" %>
<%@ page import="com.finalproject.dao.TransactionDao" %>
<%@ page import="com.finalproject.model.Customer" %>
<%@ page import="com.finalproject.dao.CustomerDao" %>

<%
	User auth = (User)request.getSession().getAttribute("auth");
	if(auth == null)  {
		response.sendRedirect("login.jsp");
	}
	
	TransactionDao td = new TransactionDao(DbCon.getConnection());
	List<Transaction> transactions = null;
	Customer selectedCustomer = (Customer)request.getSession().getAttribute("selected-customer");
	double total = 0;
	int cid = 0;
	
	if(selectedCustomer == null){
		transactions = td.getAllTransactions();
		total = td.getTotalTransactions();
	} else {
		cid = selectedCustomer.getId();
		transactions = td.getCustomerTransactions(cid);
		total = td.getTotalCustomer(cid);
	}
		
	int recCount = (transactions != null) ? transactions.size() : 0;
	double balance = 0.0;
	double credit = 0.0;
	double debit = 0.0;
	
	CustomerDao cd = new CustomerDao(DbCon.getConnection());
	List<Customer> customers = cd.getAllCustomers();	

    DecimalFormat dcf = new DecimalFormat("#.##");
    request.setAttribute("dcf", dcf);
%>

 
<!DOCTYPE html>
<html>
	<head>
		<%@include file="include/header.jsp" %>
		<title>Pastry Shop Internal Application - Transactions</title>
	</head>
	
	<body>
		<div class="container">
		<%@include file = "include/navbar.jsp"%>
		<div class ="container my-3">
			<div class="card-header my-3">All Transactions (<%=recCount %>)
			
				<form action="customer-select" method="post">
				    <div class="form-group">
				        <div class="input-group mb-3">
				            <div class="input-group-prepend">
				                <label class="input-group-text" for="inputGroupSelect01">Selected Customer</label>
				            </div>
				            <select class="custom-select" name="customer_id" required>
				                <option value="0" <%= (cid == 0) ? "selected" : "" %>>All Customers</option>
				                <% 
				                if (!customers.isEmpty()) {
				                    for (Customer c : customers) { %>
				                        <option value="<%= c.getId() %>" <%= (c.getId() == cid) ? "selected" : "" %>><%= c.getName() %></option>
				                    <% }
				                } %>
				            </select>
				            <input type="hidden" name="page-redirect" value="transactions.jsp">
				            <button type="submit" class="btn btn-primary">Filter</button>
				        </div>
				    </div>
				</form>
			
			
			</div>
			<table class = "table table-light">
				<thead>
					<tr>
						<th scope="col">Reference</th>
						<th scope="col">Date</th>
						<th scope="col">Type</th>
						<th scope="col">Customer-Description</th>
						<th scope="col">Debit</th>
						<th scope="col">Credit</th>
						<th scope="col">Balance</th>
					</tr>
				</thead>	
				<tbody>
					<%
					if(transactions != null){
						for(Transaction t:transactions){
							balance += t.getCreditAmount() - t.getDebitAmount();
							credit += t.getCreditAmount();
							debit += t.getDebitAmount();%>
							<tr>
								<td><%=t.getId() %></td>
								<td><%=t.getDate() %></td>
								<td><%=t.getTypeName() %></td>
								<td><%=(t.getCustomerName()==""?"SHOP":t.getCustomerName())+"-"+t.getDesc() %></td>
								<td><%=(t.getDebitAmount()==0)?"":dcf.format(t.getDebitAmount()) %> </td>
								<td><%=(t.getCreditAmount()==0)?"":dcf.format(t.getCreditAmount()) %> </td>
								<th scope="col">$<%=dcf.format((balance<0)?-balance:balance) %> </th>
								<td><%=(balance<0)?"Deb":"Cre" %></td>
							</tr>
					<%
						}
					}
					%>				
					<tr>
						<th scope="col"></th>
						<th scope="col"></th>
						<th scope="col"></th>
						<th scope="col">Total Transactions</th>
						<th scope="col">$<%=(debit>0)?dcf.format(debit):0 %></th>
						<th scope="col">$<%=(credit>0)?dcf.format(credit):0 %></th>
					</tr>
				</tbody>
				</table>
			</div>		
		</div>
		<%@include file = "include/footer.jsp"%>
	</body>
</html>