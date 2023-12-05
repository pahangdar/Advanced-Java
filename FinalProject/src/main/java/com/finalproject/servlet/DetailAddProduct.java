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


@WebServlet("/detail-add_product")
public class DetailAddProduct extends HttpServlet {
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
	                List<InvoiceDetail> details = (List<InvoiceDetail>) request.getSession().getAttribute("details");

	                if (details == null) {
	                    details = new ArrayList<InvoiceDetail>();
	                }

	                InvoiceDetail detail = new InvoiceDetail();
	                detail.setProduct(product.getId());
	                detail.setQuantity(1);
	                detail.setPrice(product.getPrice());
	                detail.setProductName(product.getName());

	                details.add(detail);
	                request.getSession().setAttribute("details", details);
	                
	                List<Product> products = (List<Product>)request.getSession().getAttribute("products");
	                if (products == null) {
	                	products = new ArrayList<Product>();
	                }
	                //products.remove(product);
	                Iterator<Product> iterator = products.iterator();
	                while (iterator.hasNext()) {
	                    Product p = iterator.next();
	                    if (p.getId() == product.getId()) {
	                        iterator.remove();
	                    }
	                }
	                request.getSession().setAttribute("products", products);
	                
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


