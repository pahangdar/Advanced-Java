package com.finalproject.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.finalproject.model.InvoiceDetail;

@WebServlet("/detail-quantity-change")
public class DetailQuantityChange extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("product_id"));
        int newQuantity = Integer.parseInt(request.getParameter("quantity"));

        // Retrieve the details from the session
        List<InvoiceDetail> details = (List<InvoiceDetail>) request.getSession().getAttribute("details");

        // Find the corresponding InvoiceDetail and update the quantity
        for (InvoiceDetail detail : details) {
            if (detail.getProduct() == productId) {
                detail.setQuantity(newQuantity);
                break; // Assuming each product appears only once in the details
            }
        }

        // Update the session attribute with the modified details
        request.getSession().setAttribute("details", details);

        // Redirect or forward to the appropriate page
        response.sendRedirect("invoice_product.jsp");
    }
}
