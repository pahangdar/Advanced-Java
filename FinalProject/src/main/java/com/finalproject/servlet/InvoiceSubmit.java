package com.finalproject.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import com.finalproject.connection.DbCon;
import com.finalproject.dao.InvoiceDao;
import com.finalproject.model.Invoice;
import com.finalproject.dao.InvoiceDetailDao;
import com.finalproject.model.InvoiceDetail;




@WebServlet("/invoice-submit")
public class InvoiceSubmit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("login.jsp");
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		try (PrintWriter out = response.getWriter()){

			Invoice invoice = (Invoice)request.getSession().getAttribute("invoice");
			if(invoice == null)  {
				response.sendRedirect("error.jsp");
			}

			List<InvoiceDetail> details = (List<InvoiceDetail>)request.getSession().getAttribute("details");
			if(details == null)  {
				response.sendRedirect("error.jsp");
			}
			if(details.isEmpty())  {
				response.sendRedirect("error.jsp");
			}

			try {
				double total = 0.0;
				for(InvoiceDetail d:details){
					total += d.getPrice()*d.getQuantity();
				}
				invoice.setAmount(total);
				
				InvoiceDao idao = new InvoiceDao(DbCon.getConnection());
				int newInvoiceId = idao.newInvoice(invoice);
				if(newInvoiceId > 0) {
					try {
						for(InvoiceDetail d:details){
							d.setInvoice(newInvoiceId);
						}
						InvoiceDetailDao ddao = new InvoiceDetailDao(DbCon.getConnection());
						boolean result = ddao.newInvoiceDetails(details);
						if(result) {
							response.sendRedirect("invoices.jsp");
						}else {
							idao.deleteInvoice(invoice);
							out.print("invoice detail submit failed");
						}
						
					}catch(ClassNotFoundException | SQLException | NumberFormatException  e) {
						e.printStackTrace();
						System.out.print(e.getMessage());
				    }
				}else {
					out.print("invoice submit failed");
				}
				
			}catch(ClassNotFoundException | SQLException | NumberFormatException  e) {
				e.printStackTrace();
				System.out.print(e.getMessage());
		    }

		}	
	}
	
}
