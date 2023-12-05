<%@ page import="com.finalproject.model.User" %>

<%
	String userDesc;
	User navUser = (User)request.getSession().getAttribute("auth");
	if(navUser != null)  {
		userDesc = "  (Welcome " + navUser.getDesc() + ")";
	} else {
		userDesc = "  (not logined)";
	}
%>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="index.jsp">Pastry Shop <%=userDesc %></a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav ml-auto">
      <li class="nav-item active">
        <a class="nav-link" href="index.jsp">Home</a>
      </li>
      <%if(navUser != null) {%>
          <li class="nav-item active">
            <a class="nav-link" href="products.jsp">Products</a>
 	      </li>
          <li class="nav-item active">
            <a class="nav-link" href="customers.jsp">Customers</a>
 	      </li>
          <li class="nav-item active">
            <a class="nav-link" href="invoices.jsp">Invoices</a>
 	      </li>
          <li class="nav-item active">
            <a class="nav-link" href="payments.jsp">Payments</a>
 	      </li>
          <li class="nav-item active">
            <a class="nav-link" href="expenses.jsp">Expenses</a>
 	      </li>
          <li class="nav-item active">
            <a class="nav-link" href="transactions.jsp">Transactions</a>
 	      </li>
          <li class="nav-item active">
            <a class="nav-link" href="log-out">Logout</a>
          </li>
      <%} else {%>
	      <li class="nav-item active">
    	     <a class="nav-link" href="login.jsp">Login</a>
      	  </li>
      <%}%>
    </ul>
  </div>
</nav>	
