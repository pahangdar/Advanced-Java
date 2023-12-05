<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
    
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.*" %>
<%@page import= "java.text.DecimalFormat" %>


<%@ page import="com.finalproject.connection.DbCon" %>
<%@ page import="com.finalproject.model.User" %>
<%@ page import="com.finalproject.model.Expense" %>
<%@ page import="com.finalproject.dao.ExpenseDao" %>

<%
	User auth = (User)request.getSession().getAttribute("auth");
	if(auth == null)  {
		response.sendRedirect("login.jsp");
	}
	
	ExpenseDao ed = new ExpenseDao(DbCon.getConnection());
	List<Expense> expenses = ed.getAllExpenses();
	
	double total = ed.getTotalExpenses();
	int recCount = (expenses != null) ? expenses.size() : 0;
	
    DecimalFormat dcf = new DecimalFormat("#.##");
    request.setAttribute("dcf", dcf);
%>

 
<!DOCTYPE html>
<html>
	<head>
		<%@include file="include/header.jsp" %>
		<title>Pastry Shop Internal Application - Expenses</title>
	</head>
	
	<body>
		<div class="container">
		<%@include file = "include/navbar.jsp"%>
		<div class ="container my-3">
			<div class="d-flex py-3">
				<a class="mx-3 btn btn-success btn-lg" href="expense_new.jsp">New Expense</a>
			</div>
			<div class="card-header my-3">All Expenses (<%=recCount %>)</div>
			<table class = "table table-light">
				<thead>
					<tr>
						<th scope="col">Reference</th>
						<th scope="col">Date</th>
						<th scope="col">Description</th>
						<th scope="col">Amount</th>
						<th scope="col">TransRef</th>
					</tr>
				</thead>	
				<tbody>
					<tr>
						<th scope="col"></th>
						<th scope="col"></th>
						<th scope="col">Total Expenses</th>
						<th scope="col">$<%=(total>0)?dcf.format(total):0 %></th>
						<th scope="col"></th>
					</tr>
					<%
					if(expenses != null){
						for(Expense e:expenses){%>
							<tr>
								<td><%=e.getId() %></td>
								<td><%=e.getDate() %></td>
								<td><%=e.getDesc() %></td>
								<td>$<%=dcf.format(e.getAmount()) %> </td>
								<td><%=e.getTransaction() %></td>
								<td><a class="btn btn-sm btn-danger" href="expense-delete?eid=<%=e.getId()%>&tid=<%=e.getTransaction() %>">Delete</a></td>
							</tr>
					<%
						}
					}
					%>				
				</tbody>
				</table>
			</div>		
		</div>
		<%@include file = "include/footer.jsp"%>
	</body>
</html>