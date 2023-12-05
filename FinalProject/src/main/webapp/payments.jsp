<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
    
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.*" %>
<%@page import= "java.text.DecimalFormat" %>


<%@ page import="com.finalproject.connection.DbCon" %>
<%@ page import="com.finalproject.model.User" %>
<%@ page import="com.finalproject.model.Payment" %>
<%@ page import="com.finalproject.dao.PaymentDao" %>
<%@ page import="com.finalproject.model.Customer" %>
<%@ page import="com.finalproject.dao.CustomerDao" %>

<%
	User auth = (User)request.getSession().getAttribute("auth");
	if(auth == null)  {
		response.sendRedirect("login.jsp");
	}
	
	PaymentDao pd = new PaymentDao(DbCon.getConnection());
	List<Payment> payments = null;
	Customer selectedCustomer = (Customer)request.getSession().getAttribute("selected-customer");
	double total = 0;
	int cid = 0;
	
	if(selectedCustomer == null){
		payments = pd.getAllPayments();
		total = pd.getTotalPayments();
	} else {
		cid = selectedCustomer.getId();
		payments = pd.getCustomerPayments(cid);
		total = pd.getTotalCustomer(cid);
	}
	
	
	
	int recCount = (payments != null) ? payments.size() : 0;
	
	CustomerDao cd = new CustomerDao(DbCon.getConnection());
	List<Customer> customers = cd.getAllCustomers();	

    DecimalFormat dcf = new DecimalFormat("#.##");
    request.setAttribute("dcf", dcf);
%>

 
<!DOCTYPE html>
<html>
	<head>
		<%@include file="include/header.jsp" %>
		<title>Pastry Shop Internal Application - Payments</title>
	</head>
	
	<body>
		<div class="container">
		<%@include file = "include/navbar.jsp"%>
		<div class ="container my-3">
			<div class="d-flex py-3">
				<a class="mx-3 btn btn-success btn-lg" href="payment_new.jsp">New Payment</a>
			</div>
			<div class="card-header my-3">All Payments (<%=recCount %>)
			
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
				            <input type="hidden" name="page-redirect" value="payments.jsp">
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
						<th scope="col">Customer</th>
						<th scope="col">Amount</th>
						<th scope="col">TransRef</th>
					</tr>
				</thead>	
				<tbody>
					<tr>
						<th scope="col"></th>
						<th scope="col"></th>
						<th scope="col">Total Payments</th>
						<th scope="col">$<%=(total>0)?dcf.format(total):0 %></th>
						<th scope="col"></th>
					</tr>
					<%
					if(payments != null){
						for(Payment p:payments){%>
							<tr>
								<td><%=p.getId() %></td>
								<td><%=p.getDate() %></td>
								<td><%=p.getCustomerName() %></td>
								<td>$<%=dcf.format(p.getAmount()) %> </td>
								<td><%=p.getTransaction() %></td>
								<td><a class="btn btn-sm btn-danger" href="payment-delete?pid=<%=p.getId()%>&tid=<%=p.getTransaction() %>">Delete</a></td>
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