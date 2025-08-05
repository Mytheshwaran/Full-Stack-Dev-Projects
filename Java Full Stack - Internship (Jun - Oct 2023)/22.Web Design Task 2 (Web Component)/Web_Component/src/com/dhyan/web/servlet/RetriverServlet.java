package com.dhyan.web.servlet;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.dhyan.web.jpa.Employee;
import com.dhyan.web.jpa.PhoneNumber;

public class RetriverServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private PrintWriter responseOfGet = null;
	private PrintWriter responseOfDelete=null;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			responseOfGet = response.getWriter();
			getAllEmployees();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (responseOfGet != null) {
				responseOfGet.close();
			}
		}
	}
	
	@Override
	public void doDelete(HttpServletRequest request, HttpServletResponse response) {
		try {
			responseOfDelete = response.getWriter();
			removeEmployee(request);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (responseOfDelete != null) {
				responseOfDelete.close();
			}
		}
	}
	@SuppressWarnings("unchecked")
	public void getAllEmployees()
	{
		EntityManagerFactory factory = null;
		EntityManager entityManager = null;
		try {
			factory = Persistence.createEntityManagerFactory("Web_Component_JPA");
			entityManager = factory.createEntityManager();
			Query query=entityManager.createQuery("Select obj from Employee obj",Employee.class);
			List<Employee> employeeList=query.getResultList();
			
			SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
			
			JSONArray array = new JSONArray();
		      for(Employee employee:employeeList)
		      {
		    	  String date=formatter.format(employee.getDate());
		    	  
		    	  JSONObject jsonObject = new JSONObject();
			      jsonObject.put("ID", employee.getId());
			      jsonObject.put("NAME", employee.getName());
			      jsonObject.put("AGE", employee.getAge());
			      jsonObject.put("D_O_B", date);
			      jsonObject.put("BLOOD_GROUP", employee.getBloodGroup());
			      jsonObject.put("DESIGNATION", employee.getDesignation());
			      jsonObject.put("DOOR_NO", employee.getAddress().getDoorNo());
			      jsonObject.put("STREET", employee.getAddress().getStreet());
			      jsonObject.put("DISTRICT", employee.getAddress().getDistrict());
			      jsonObject.put("STATE", employee.getAddress().getState());
			      jsonObject.put("PHONE_NUMBER",employee.getPhoneNumber());
			      array.add(jsonObject);
		      }
		      JSONObject arrayObj=new JSONObject();
		      arrayObj.put("data",array);
		      responseOfGet.println(arrayObj);
		} catch (Exception e) {
			responseOfGet.println("Error "+e.getMessage());
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
			if (factory != null) {
				factory.close();
			}
		}
	}
	
	public void removeEmployee(HttpServletRequest request)
	{
		EntityManagerFactory factory = null;
		EntityManager entityManager = null;
		try {
			factory = Persistence.createEntityManagerFactory("Web_Component_JPA");
			entityManager = factory.createEntityManager();
			
			entityManager.getTransaction().begin();
			
			String id=request.getParameter("ID");
			Employee employee=entityManager.find(Employee.class, Integer.parseInt(id));
			Query query = entityManager.createQuery("Select obj from PhoneNumber obj");
			@SuppressWarnings("unchecked")
			List<PhoneNumber> oldPhoneNumberList = query.getResultList();
			
			for(PhoneNumber empNumber:employee.getPhoneNumber())
			{
				for(PhoneNumber number:oldPhoneNumberList)
				{
					if(number==empNumber)
					{
						entityManager.remove(number);
					}
				}
			}
			
			entityManager.remove(employee);
			
			entityManager.getTransaction().commit();
			
			responseOfDelete.println("Data Successfully Removed");
			
		} catch (Exception e) {
			responseOfDelete.println("Error "+e.getMessage());
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
			if (factory != null) {
				factory.close();
			}
		}
	}
}
