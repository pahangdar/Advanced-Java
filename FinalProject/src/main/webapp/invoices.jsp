<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
    
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.*" %>
<%@page import= "java.text.DecimalFormat" %>


<%@ page import="com.finalproject.connection.DbCon" %>
<%@ page import="com.finalproject.model.User" %>
<%@ page import="com.finalproject.model.Invoice" %>
<%@ page import="com.finalproject.dao.InvoiceDao" %>
<%@ page import="com.finalproject.model.Customer" %>
<%@ page import="com.finalproject.dao.CustomerDao" %>

<%
	User auth = (User)request.getSession().getAttribute("auth");
	if(auth == null)  {
		response.sendRedirect("login.jsp");
	}
	
	InvoiceDao idao = new InvoiceDao(DbCon.getConnection());
	List<Invoice> invoices = null;
	Customer selectedCustomer = (Customer)request.getSession().getAttribute("selected-customer");
	double total = 0;
	int cid = 0;
	
	if(selectedCustomer == null){
		invoices = idao.getAllInvoices();
		total = idao.getTotalInvoices();
	} else {
		cid = selectedCustomer.getId();
		invoices = idao.getCustomerInvoices(cid);
		total = idao.getTotalCustomer(cid);
	}
	
	
	
	int recCount = (invoices != null) ? invoices.size() : 0;
	
	CustomerDao cd = new CustomerDao(DbCon.getConnection());
	List<Customer> customers = cd.getAllCustomers();	

    DecimalFormat dcf = new DecimalFormat("#.##");
    request.setAttribute("dcf", dcf);
%>

 
<!DOCTYPE html>
<html>
	<head>
	
	<!-- Add this link before the Bootstrap script tags -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!-- Bootstrap script tags go here -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
	
	
		<%@include file="include/header.jsp" %>
		<title>Pastry Shop Internal Application - Invoices</title>
	</head>
	
	<body>
		<div class="container">
		<%@include file = "include/navbar.jsp"%>
		<div class ="container my-3">
			<div class="d-flex py-3">
				<a class="mx-3 btn btn-success btn-lg" href="invoice_new.jsp">New Invoice</a>
			</div>
			<div class="card-header my-3">All Invoices (<%=recCount %>)
			
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
				            <input type="hidden" name="page-redirect" value="invoices.jsp">
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
						<th scope="col">Total Invoices</th>
						<th scope="col">$<%=(total>0)?dcf.format(total):0 %></th>
						<th scope="col"></th>
					</tr>
					<%
					if(invoices != null){
						for(Invoice inv:invoices){%>
							<tr>
								<td><%=inv.getId() %></td>
								<td><%=inv.getDate() %></td>
								<td><%=inv.getCustomerName() %></td>
								<td>$<%=dcf.format(inv.getAmount()) %> </td>
								<td><%=inv.getTransaction() %></td>
								<td>
									<button type="button" class="btn btn-sm btn-primary" data-toggle="modal" data-target="#exampleModal">
									  Details
									</button>								
								</td>
								<td><a class="btn btn-sm btn-danger" href="invoice-delete?pid=<%=inv.getId()%>">Delete</a></td>
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
		
		<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Invoice Details</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        ...
      </div>
      <div class="modal-footer">
      </div>
    </div>
  </div>
</div>


	</body>
</html>