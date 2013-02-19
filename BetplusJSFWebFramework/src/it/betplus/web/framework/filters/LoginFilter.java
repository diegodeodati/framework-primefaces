package it.betplus.web.framework.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter{
	
	FilterConfig fc;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		fc = filterConfig;
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession(true);

		// check logged user
		if(session.getAttribute("currentUser") != null) {
		
			chain.doFilter(request, response); 	
		
		} else {
			
			resp.sendRedirect("../index.xhtml"); 
			
		}
		
	}

	public void destroy() {
		
	}
	
	public boolean checkFunctionalityInRequestUrl(HttpServletRequest req, String currentFunctionality) {
		
		String fullURL = req.getRequestURL().append("?").append(req.getQueryString()).toString();

		if(fullURL.contains("/" + currentFunctionality + "/"))
			return true;
		else 
			return false;
		
	}

}
