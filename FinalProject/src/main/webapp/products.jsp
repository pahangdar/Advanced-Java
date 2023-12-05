<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
    
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.*" %>


<%@ page import="com.finalproject.connection.DbCon" %>
<%@ page import="com.finalproject.model.Product" %>
<%@ page import="com.finalproject.dao.ProductDao" %>

<%
	User auth = (User)request.getSession().getAttribute("auth");
	if(auth == null)  {
		response.sendRedirect("login.jsp");
	}
	ProductDao pd = new ProductDao(DbCon.getConnection());
	List<Product> products = pd.getAllProducts();	
	int recCount = (products != null) ? products.size() : 0;
%>

 
<!DOCTYPE html>
<html>
	<head>
		<%@include file="include/header.jsp" %>
		<title>Pastry Shop Internal Application - Products</title>
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
							    <%--
							    <div class ="mt-3 d-flex justify-content-between" >
							    <a href="add-to-cart?id=<%=p.getId() %>" class="btn btn-dark">Add to Cart</a>
							    <a href="order-now?quantity=1&id=<%=p.getId() %>" class="btn btn-primary">Buy Now</a>							    
							    </div>    
							     --%>
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