package com.dhyan.registration;

import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.dhyan.common.*;

@SuppressWarnings("serial")
public class UserServlet extends HttpServlet {
	@EJB
	RegistrationLocal registerObj;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try (PrintWriter out = response.getWriter()) {
			
			UserDetails userName=new UserDetails();
			userName.setName(request.getParameter("userName"));
			
			
			boolean isUserExist=checkUserList(userName);
			if (!isUserExist) {
				addUser(userName, request, response);
			} else {
				out.println("<div align='center'> <h1>User Already Exist!!!</h1></div>");
				out.println("<a href=index.html><button>Add User</button></a>");
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void addUser(UserDetails userName, HttpServletRequest request, HttpServletResponse response) {
		try {
			registerObj.addUser(userName);
			request.setAttribute("RegistrationLocal", registerObj);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("registeredUsers.jsp");
			requestDispatcher.include(request, response);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public boolean checkUserList(UserDetails userName)
	{
		boolean isUserExist=false;
		for(UserDetails user:registerObj.getUserList())
		{
			if(user.getName().equalsIgnoreCase(userName.getName()))
				isUserExist=true;
		}
		return isUserExist;
	}
}
