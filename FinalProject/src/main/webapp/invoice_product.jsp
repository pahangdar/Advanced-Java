<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
    
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.*" %>
<%@page import= "java.text.DecimalFormat" %>


<%@ page import="com.finalproject.connection.DbCon" %>
<%@ page import="com.finalproject.model.Product" %>
<%@ page import="com.finalproject.model.Invoice" %>
<%@ page import="com.finalproject.model.InvoiceDetail" %>
<%@ page import="com.finalproject.model.Customer" %>

<%
	User auth = (User)request.getSession().getAttribute("auth");
	if(auth == null)  {
		response.sendRedirect("login.jsp");
	}
	
	Invoice invoice = (Invoice)request.getSession().getAttribute("invoice");
	if(invoice == null)  {
		response.sendRedirect("invoice_new.jsp");
	}

	Customer customer = (Customer)request.getSession().getAttribute("customer");
	if(customer == null)  {
		response.sendRedirect("invoice_new.jsp");
	}

	List<Product> products = (List<Product>)request.getSession().getAttribute("products");
	if(products == null)  {
		response.sendRedirect("invoice_new.jsp");
	}

	List<InvoiceDetail> details = (List<InvoiceDetail>)request.getSession().getAttribute("details");
	if(details == null){
		details = new ArrayList<InvoiceDetail>();
	}
	double total = 0.0;

	int recCount = (products != null) ? products.size() : 0;
	
    DecimalFormat dcf = new DecimalFormat("#.##");
    request.setAttribute("dcf", dcf);
%>

 
<!DOCTYPE html>
<html>
	<head>
		<%@include file="include/header.jsp" %>
		<title>Pastry Shop Internal Application - Adding Products to Invoice</title>
	</head>
	
	<body>
		<div class="container">
			<%@include file = "include/navbar.jsp"%>
			<div class= "container">
				<div class="card-header my-3">All Products (<%=recCount %>)</div>
				<div class ="row">
				<% 
				if(!products.isEmpty()){
					for(Product p:products){%>
						<div class="col-md-3 my-3">
						<div class="card w-100" style="width: 18rem;">
							  <img src="product-images/<%= p.getImage() %>" class="card-img-top" alt="...">
							  <div class="card-body">
							    <h5 class="card-title"><%= p.getName() %></h5>
							    <h6 class="price">Price: $<%= p.getPrice() %></h6>
							    <h6 class="price">Level: <%= p.getLevel() %> <%= p.getScaleName() %></h6>
							    <div class ="mt-3 d-flex justify-content-between" >
								    <a href="detail-add_product?product_id=<%=p.getId() %>" class="btn btn-dark">Add to Invoice</a>
							    </div>    
			  				</div>
						</div>
					</div>
					
					<% }
				}
				%>
				</div>
				<div class="card-header my-3">
					<h5 class="card-title">Invoice for:</h5>
					<h6 class="price"><%=customer.getName() %></h6>
				</div>
				
				<table class = "table table-light">
					<thead>
						<tr>
							<th scope="col">Product</th>
							<th scope="col">Quantity</th>
							<th scope="col">Unit Price</th>
							<th scope="col">Total</th>
						</tr>
					</thead>	
					<tbody>
						<%
						if(details != null){
							total = 0.0;
							for(InvoiceDetail d:details){
								total += d.getPrice()*d.getQuantity();%>
								
								<form action="detail-quantity-change" method="post">
					                <input type="hidden" name="product_id" value="<%=d.getProduct() %>">
					                <tr>
					                    <td><%=d.getProductName() %></td>
					                    <td>
					                        <input type="text" name="quantity" class="form-control w-50" value="<%=d.getQuantity()%>" onchange="this.form.submit()">
					                    </td>
					                    <td>$<%=dcf.format(d.getPrice()) %></td>
					                    <td>$<%=dcf.format(d.getPrice() * d.getQuantity()) %></td>
					                    <td><a class="btn btn-sm btn-danger" href="detail-remove_product?product_id=<%=d.getProduct()%>">Remove</a></td>
					                </tr>
					            </form>
						<%
							}
						}
						%>				
						<tr>
							<th scope="col"></th>
							<th scope="col"></th>
							<th scope="col">Invoice Total</th>
							<th scope="col" id="total-value">$<%=dcf.format(total) %></th>
						</tr>
					</tbody>
				</table>
				<%
				if(total>0){%>
				<div class ="text-center">
					<a class="btn btn-sm btn-primary btn-lg" href="invoice-submit">Submit</a>
				</div>
				<%} %>
			</div>
		</div>		
		<%@include file = "include/footer.jsp"%>
	</body>
</html>