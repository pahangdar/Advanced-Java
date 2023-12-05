package com.finalproject.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/FetchInvoiceDetailsServlet")
public class FetchInvoiceDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int invoiceId = Integer.parseInt(request.getParameter("invoiceId"));

        // Fetch details based on invoiceId and create HTML content

        String detailsHtml = "<p>Details for Invoice ID " + invoiceId + "</p>";

        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(detailsHtml);
    }
}
