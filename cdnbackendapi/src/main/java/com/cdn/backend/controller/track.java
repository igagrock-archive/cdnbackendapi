package com.cdn.backend.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class track
 */
public class track extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out= response.getWriter();
		out.println(request.getServletPath());
		out.println("servlet context name = "+this.getServletContext().getServletContextName());   
		out.println("resorce path= "+this.getServletContext().getResourcePaths("/images"));
		out.println("context path= "+this.getServletContext().getContextPath());
        out.println(System.getenv("OPENSHIFT_DATA_DIR")+"images");
        System.out.println(System.getenv("PATH_TO_IMAGES"));
    

		
	
		
	}


}
   