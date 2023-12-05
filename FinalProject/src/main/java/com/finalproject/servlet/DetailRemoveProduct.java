package com.finalproject.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;

import com.finalproject.connection.DbCon;
import com.finalproject.dao.ProductDao;
import com.finalproject.model.Product;
import com.finalproject.model.InvoiceDetail;

@WebServlet("/detail-remove_product")
public class DetailRemoveProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("login.jsp");
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    response.setContentType("text/html; charset=UTF-8");
	    try (PrintWriter out = response.getWriter()) {
	        int product_id = Integer.parseInt(request.getParameter("product_id"));

	        try {
	            ProductDao pdao = new ProductDao(DbCon.getConnection());
	            Product product = pdao.getSingleProduct(product_id);

	            if (product != null) {
	                List<Product> products = (List<Product>)request.getSession().getAttribute("products");
	                if (products == null) {
	                	products = new ArrayList<Product>();
	                }
	                products.add(product);
	                request.getSession().setAttribute("products", products);
	            	
	            	List<InvoiceDetail> details = (List<InvoiceDetail>) request.getSession().getAttribute("details");

	                if (details == null) {
	                    details = new ArrayList<InvoiceDetail>();
	                }
	                Iterator<InvoiceDetail> iterator = details.iterator();
	                while (iterator.hasNext()) {
	                	InvoiceDetail d = iterator.next();
	                    if (d.getProduct() == product.getId()) {
	                        iterator.remove();
	                    }
	                }
	                request.getSession().setAttribute("details", details);
	                
	                
	                response.sendRedirect("invoice_product.jsp");
	            } else {
	                out.print("Adding product failed");
	            }

	        } catch (ClassNotFoundException | SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
}


