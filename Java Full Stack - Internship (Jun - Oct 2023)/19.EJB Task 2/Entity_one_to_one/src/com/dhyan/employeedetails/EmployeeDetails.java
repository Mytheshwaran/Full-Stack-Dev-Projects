package com.dhyan.employeedetails;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EmployeeDetails {

	public static void main(String[] args) {
		EntityManagerFactory factory=Persistence.createEntityManagerFactory("Entity_one_to_one");
		EntityManager entityManager=factory.createEntityManager();
		
		entityManager.getTransaction().begin();
		
		Address address1=new Address();
		address1.setStreet("Street_1");
		address1.setDistrict("District_1");
		address1.setState("State_1");
		
		Address address2=new Address();
		address2.setStreet("Street_2");
		address2.setDistrict("District_2");
		address2.setState("State_2");
		
		Employee employee1=new Employee();
		employee1.setName("Employee_1");
		employee1.setAddress(address2);
		
		Employee employee2=new Employee();
		employee2.setName("Employee_2");
		employee2.setAddress(address1);
		
		address1.setEmployee(employee2);
		address2.setEmployee(employee1);
		
		entityManager.persist(address1);
		entityManager.persist(address2);
		entityManager.persist(employee1);
		entityManager.persist(employee2);
		
		entityManager.getTransaction().commit();
		
		entityManager.close();
		factory.close();
	}

}
