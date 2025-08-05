package com.dhyan.web.servlet;

import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dhyan.web.jpa.Address;
import com.dhyan.web.jpa.Employee;
import com.dhyan.web.jpa.PhoneNumber;

public class AdderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PrintWriter responseOfPost = null;
	private PrintWriter responseOfPut = null;
	private PrintWriter responseOfDelete = null;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			responseOfPost = response.getWriter();
			String name = request.getParameter("name");
			String age = request.getParameter("age");
			String dateOfBirth = request.getParameter("birth");
			String bloodGroup = request.getParameter("blood");
			String designation = request.getParameter("designation");
			String doorNo = request.getParameter("doorNo");
			String street = request.getParameter("street");
			String district = request.getParameter("district");
			String state = request.getParameter("state");
			String[] phoneNo = request.getParameterValues("phoneNumbers[]");

			List<String> phoneNumbers = Arrays.asList(phoneNo);

			addEmployee(name, Integer.parseInt(age), dateOfBirth, bloodGroup, designation, Integer.parseInt(doorNo),
					street, district, state, phoneNumbers);
			responseOfPost.println("Successfully Registered");
		} catch (Exception e) {
			responseOfPost.println("Error " + e.getMessage());
		} finally {
			if (responseOfPost != null) {
				responseOfPost.close();
			}
		}
	}

	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) {
		try {
			responseOfPut = response.getWriter();
			String id = request.getParameter("ID");
			String name = request.getParameter("NAME");
			String age = request.getParameter("AGE");
			String dateOfBirth = request.getParameter("D_O_B");
			String bloodGroup = request.getParameter("BLOOD_GROUP");
			String designation = request.getParameter("DESIGNATION");
			String doorNo = request.getParameter("DOOR_NO");
			String street = request.getParameter("STREET");
			String district = request.getParameter("DISTRICT");
			String state = request.getParameter("STATE");
			String[] phoneNo = request.getParameterValues("PHONE_NUMBER[]");

			//String[] phoneNumberArray = phoneNo.split(",");

			List<String> phoneNumbers = new ArrayList<String>();

			for (String number : phoneNo) {
				phoneNumbers.add(number);
			}

			updateEmployee(id, name, Integer.parseInt(age), dateOfBirth, bloodGroup, designation,
					Integer.parseInt(doorNo), street, district, state, phoneNumbers);
		} catch (Exception e) {
			responseOfPut.println("Error " + e.getMessage());
		} finally {
			if (responseOfPut != null) {
				responseOfPut.close();
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

	public void addEmployee(String name, int age, String birth, String bloodGroup, String designation, int doorNo,
			String street, String district, String state, List<String> phoneNumbers) {
		EntityManagerFactory factory = null;
		EntityManager entityManager = null;
		try {
			factory = Persistence.createEntityManagerFactory("Web_Component_JPA");
			entityManager = factory.createEntityManager();

			entityManager.getTransaction().begin();

			List<PhoneNumber> phoneNoList = new ArrayList<PhoneNumber>();
			for (String phoneNo : phoneNumbers) {
				if (phoneNo != "0") {
					PhoneNumber obj = new PhoneNumber();
					obj.setPhoneNo(Long.valueOf(phoneNo));
					phoneNoList.add(obj);
				}
			}

			Address address = new Address();
			address.setDoorNo(doorNo);
			address.setStreet(street);
			address.setDistrict(district);
			address.setState(state);

			Employee employee = new Employee();
			employee.setName(name);
			employee.setAge(age);
			employee.setBloodGroup(bloodGroup);
			employee.setDate(Date.valueOf(birth));
			employee.setDesignation(designation);
			employee.setAddress(address);
			employee.setPhoneNumber(phoneNoList);

			for (PhoneNumber phoneNo : phoneNoList) {
				entityManager.persist(phoneNo);
			}
			entityManager.persist(employee);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			responseOfPost.println("Error " + e.getMessage());
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
			if (factory != null) {
				factory.close();
			}
		}
	}

	public void updateEmployee(String id, String name, int age, String birth, String bloodGroup, String designation,
			int doorNo, String street, String district, String state, List<String> phoneNumbers) {
		EntityManagerFactory factory = null;
		EntityManager entityManager = null;
		try {
			factory = Persistence.createEntityManagerFactory("Web_Component_JPA");
			entityManager = factory.createEntityManager();

			entityManager.getTransaction().begin();

			List<PhoneNumber> newPhoneNumberList = new ArrayList<PhoneNumber>();
			Query query = entityManager.createQuery("Select obj from PhoneNumber obj");
			@SuppressWarnings("unchecked")
			List<PhoneNumber> oldPhoneNumberList = query.getResultList();

			for (String newPhoneNumber : phoneNumbers) {

				int oldPhoneNumberListSize = oldPhoneNumberList.size();

				for (PhoneNumber oldPhoneNumber : oldPhoneNumberList) {
					oldPhoneNumberListSize--;
					String oldNumber = String.valueOf(oldPhoneNumber.getPhoneNo());
					if (oldNumber.equals(newPhoneNumber)) {
						newPhoneNumberList.add(oldPhoneNumber);
						break;
					}
					if (oldPhoneNumberListSize == 0) {
						PhoneNumber newNumber = new PhoneNumber();
						newNumber.setPhoneNo(Long.valueOf(newPhoneNumber));
						newPhoneNumberList.add(newNumber);
					}
				}
			}

			Employee employee = entityManager.find(Employee.class, Integer.parseInt(id));
			Address address = employee.getAddress();
			address.setDoorNo(doorNo);
			address.setStreet(street);
			address.setDistrict(district);
			address.setState(state);

			employee.setName(name);
			employee.setAge(age);
			employee.setDate(Date.valueOf(birth));
			employee.setBloodGroup(bloodGroup);
			employee.setDesignation(designation);
			employee.setAddress(address);

			for (PhoneNumber existEmployeeNumber : employee.getPhoneNumber()) {
				int newPhoneNumberListSize = newPhoneNumberList.size();
				for (PhoneNumber newEmployeeNumber : newPhoneNumberList) {
					newPhoneNumberListSize--;
					if (newEmployeeNumber == existEmployeeNumber) {
						break;
					}
					if (newPhoneNumberListSize == 0) {
						entityManager.remove(existEmployeeNumber);
					}
				}
			}
			employee.getPhoneNumber().removeAll(employee.getPhoneNumber());
			employee.setPhoneNumber(newPhoneNumberList);

			for (PhoneNumber newPhoneNumber : newPhoneNumberList) {
				int oldPhoneNumberListSize = oldPhoneNumberList.size();
				for (PhoneNumber oldPhoneNumber : oldPhoneNumberList) {
					oldPhoneNumberListSize--;
					if (oldPhoneNumber.equals(newPhoneNumber)) {
						break;
					}
					if (oldPhoneNumberListSize == 0) {
						entityManager.persist(newPhoneNumber);
					}
				}
			}

			entityManager.persist(employee);
			entityManager.getTransaction().commit();
			responseOfPut.println("Data Successfully Updated");

		} catch (Exception e) {
			responseOfPut.println("Error " + e.getMessage());
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
			if (factory != null) {
				factory.close();
			}
		}
	}

	public void removeEmployee(HttpServletRequest request) {
		EntityManagerFactory factory = null;
		EntityManager entityManager = null;
		try {
			factory = Persistence.createEntityManagerFactory("Web_Component_JPA");
			entityManager = factory.createEntityManager();

			entityManager.getTransaction().begin();

			String[] idList = request.getParameterValues("ID[]");

			for (String id : idList) {
				Employee employee = entityManager.find(Employee.class, Integer.parseInt(id));
				Query query = entityManager.createQuery("Select obj from PhoneNumber obj");
				@SuppressWarnings("unchecked")
				List<PhoneNumber> oldPhoneNumberList = query.getResultList();

				for (PhoneNumber empNumber : employee.getPhoneNumber()) {
					for (PhoneNumber number : oldPhoneNumberList) {
						if (number == empNumber) {
							entityManager.remove(number);
						}
					}
				}

				entityManager.remove(employee);
			}

			entityManager.getTransaction().commit();

			responseOfDelete.println("Data Successfully Removed");

		} catch (Exception e) {
			responseOfDelete.println("Error " + e.getMessage());
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
