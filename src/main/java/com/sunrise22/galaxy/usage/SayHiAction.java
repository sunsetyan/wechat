package com.sunrise22.galaxy.usage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SayHiAction extends HttpServlet {

	/**
	 * UUID
	 */
	private static final long serialVersionUID = 6481870169959712739L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("thename", "小敏");
		request.getRequestDispatcher("/WEB-INF/html/hi.html").forward(request,
				response);
	}

}
