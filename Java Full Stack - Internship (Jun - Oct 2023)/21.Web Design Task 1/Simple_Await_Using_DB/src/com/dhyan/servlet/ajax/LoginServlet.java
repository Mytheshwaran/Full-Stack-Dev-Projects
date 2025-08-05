package com.dhyan.servlet.ajax;

import java.io.PrintWriter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
	private PrintWriter out=null;
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try
		{
			out = response.getWriter();
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			
			userAdder(name,password);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			if(out!=null)
			{
				out.close();
			}
		}
	}
	
	public void userAdder(String name,String password)
	{
		try
		{
		EntityManagerFactory factory=Persistence.createEntityManagerFactory("Simple_Web_User_Adder");
		EntityManager entityManager=factory.createEntityManager();
		
		entityManager.getTransaction().begin();
		
		UserAdder user=new UserAdder();
		user.setName(name);
		user.setPassword(password);
		
		entityManager.persist(user);
		
		entityManager.getTransaction().commit();
		
		UserAdder userData=entityManager.find(UserAdder.class, user.getId());
		out.println("<h1>Successfully Registered</h1>");
		out.println("<p>Name: "+userData.getName()+"  Password: "+userData.getPassword()+"</p>");
		
		entityManager.close();
		factory.close();
		}
		catch(Exception e)
		{
			out.println("Error Occured");
		}
	}
}
