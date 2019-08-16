package com.tlys.comm.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * Servlet Filter implementation class UserinfoSessFilter
 */
public class UserSessFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public UserSessFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hreq = (HttpServletRequest) request;
		HttpSession sess = hreq.getSession();
		String addr = hreq.getRequestURI();
		
		/*
		if (addr.indexOf("login") < 0 && addr.indexOf("rsmsg")<0) {
			UserinfoSess us = (UserinfoSess) sess.getAttribute("userinfoSess");
			if (us == null) {
				hreq.getRequestDispatcher("/login.jsp").forward(
							request, response);
			} 
		}
		*/

		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
