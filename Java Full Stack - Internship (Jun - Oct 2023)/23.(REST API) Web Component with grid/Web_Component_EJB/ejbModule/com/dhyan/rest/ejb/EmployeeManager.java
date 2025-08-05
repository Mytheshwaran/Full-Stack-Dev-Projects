package com.dhyan.rest.ejb;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.dhyan.rest.common.EmployeeManagerLocal;
import com.dhyan.rest.common.JsonEmployee;
import com.dhyan.web.jpa.Address;
import com.dhyan.web.jpa.Employee;
import com.dhyan.web.jpa.PhoneNumber;

/**
 * Session Bean implementation class EmployeeManager
 */
@Stateless
public class EmployeeManager implements EmployeeManagerLocal, Serializable {

	@PersistenceContext(unitName = "Web_Component_JPA")
	EntityManager entityManager;
	private static final long serialVersionUID = 1L;

	/**
	 * Store the employee data in the database using JPA
	 * 
	 * @param jsonEmployee
	 */
	@Override
	public String addEmployee(JsonEmployee jsonEmployee) {
		try {

			List<PhoneNumber> phoneNoList = new ArrayList<>();
			for (String phoneNo : jsonEmployee.getPhoneNumbers()) {
				if (!"0".equals(phoneNo)) {
					PhoneNumber obj = new PhoneNumber();
					obj.setPhoneNo(Long.valueOf(phoneNo));
					phoneNoList.add(obj);
				}
			}

			Address address = new Address();
			address.setDoorNo(jsonEmployee.getDoorNo());
			address.setStreet(jsonEmployee.getStreet());
			address.setDistrict(jsonEmployee.getDistrict());
			address.setState(jsonEmployee.getState());

			Employee employee = new Employee();
			employee.setName(jsonEmployee.getName());
			employee.setAge(jsonEmployee.getAge());
			employee.setBloodGroup(jsonEmployee.getBlood());
			employee.setDate(Date.valueOf(jsonEmployee.getBirth()));
			employee.setDesignation(jsonEmployee.getDesignation());
			employee.setAddress(address);
			employee.setPhoneNumber(phoneNoList);
			
			for (PhoneNumber phoneNo : phoneNoList) {
				entityManager.persist(phoneNo);
			}
			entityManager.persist(employee);

			return "Successfully Data Stored";
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error " + e.getMessage());
			return "Error";
		}
	}

	/**
	 * Delete Employee in the database
	 * 
	 * @param jsonData
	 */
	@Override
	public String deleteEmployee(String[] jsonData) {
		try {

			List<String> idList = Arrays.asList(jsonData);

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

			return "Data Removed Successfully";
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error " + e.getMessage());
			return "Error";
		}
	}

	/**
	 * Update database
	 * 
	 * @param jsonEmployee
	 */
	@Override
	public String updateEmployee(JsonEmployee jsonEmployee) {

		try {
			List<PhoneNumber> newPhoneNumberList = new ArrayList<>();
			Query query = entityManager.createQuery("Select obj from PhoneNumber obj");
			@SuppressWarnings("unchecked")
			List<PhoneNumber> oldPhoneNumberList = query.getResultList();

			for (String newPhoneNumber : jsonEmployee.getPhoneNumbers()) {

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

			Employee employee = entityManager.find(Employee.class, jsonEmployee.getId());
			Address address = new Address();
			address.setDoorNo(jsonEmployee.getDoorNo());
			address.setStreet(jsonEmployee.getState());
			address.setDistrict(jsonEmployee.getDistrict());
			address.setState(jsonEmployee.getState());

			employee.setName(jsonEmployee.getName());
			employee.setAge(jsonEmployee.getAge());
			employee.setBloodGroup(jsonEmployee.getBlood());
			employee.setDate(Date.valueOf(jsonEmployee.getBirth()));
			employee.setDesignation(jsonEmployee.getDesignation());
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

			return "Successfully Data updated";
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error " + e.getMessage());
			return "Error";
		}
	}

	/**
	 * Retrieve all the employee's from the database
	 * 
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public String retrieveEmployee() {
		JSONObject arrayObj = new JSONObject();
		try {
			Query query = entityManager.createQuery("Select obj from Employee obj", Employee.class);
			List<Employee> employeeList = query.getResultList();

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			JSONArray array = new JSONArray();
			for (Employee employee : employeeList) {
				String date = formatter.format(employee.getDate());

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", employee.getId());
				jsonObject.put("name", employee.getName());
				jsonObject.put("age", employee.getAge());
				jsonObject.put("birth", date);
				jsonObject.put("blood", employee.getBloodGroup());
				jsonObject.put("designation", employee.getDesignation());
				jsonObject.put("doorNo", employee.getAddress().getDoorNo());
				jsonObject.put("street", employee.getAddress().getStreet());
				jsonObject.put("district", employee.getAddress().getDistrict());
				jsonObject.put("state", employee.getAddress().getState());
				jsonObject.put("phoneNumbers", employee.getPhoneNumber());
				array.add(jsonObject);
			}

			arrayObj.put("data", array);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error " + e.getMessage());
		}
		return arrayObj.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String checkDetails(String jsonData) {

		try {
			JSONParser parser = new JSONParser();
			JSONObject jsonObj = (JSONObject) parser.parse(jsonData);
			String name=jsonObj.get("name").toString();
			long password=Long.parseLong(jsonObj.get("password").toString());

			Query query = entityManager.createNativeQuery(
					"select emp.emp_id,emp.emp_name,ph.phone_no from employee as emp left join emp_phone_number as ph on emp.emp_id=ph.emp_id where emp.emp_name=:name and ph.phone_no=:password");
			query.setParameter("name",name);
			query.setParameter("password",password);
			
			List<Object[]> result = query.getResultList();
			
			if (result.isEmpty()) {
				return "Fail";
			} else {
				return "Success";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		}
	}
}
