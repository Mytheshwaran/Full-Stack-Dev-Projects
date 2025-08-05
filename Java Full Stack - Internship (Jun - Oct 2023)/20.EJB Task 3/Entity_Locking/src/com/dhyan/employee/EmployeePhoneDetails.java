package com.dhyan.employee;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EmployeePhoneDetails {

	public static void main(String[] args) {
		EntityManagerFactory factory=Persistence.createEntityManagerFactory("Entity_Locking");
		EntityManager entityManager=factory.createEntityManager();
		
		entityManager.getTransaction().begin();
		
		PhoneNumber number1=new PhoneNumber();
		number1.setPhoneNumber(9124256973L);
		
		PhoneNumber number2=new PhoneNumber();
		number2.setPhoneNumber(9537286534L);
		
		PhoneNumber number3=new PhoneNumber();
		number3.setPhoneNumber(9573652734L);
		
		PhoneNumber number4=new PhoneNumber();
		number4.setPhoneNumber(9755348634L);
		
		Employee employee1=new Employee();
		employee1.setName("name1");
		employee1.setPhoneNumber(Arrays.asList(number2,number4));
		
		Employee employee2=new Employee();
		employee2.setName("name2");
		employee2.setPhoneNumber(Arrays.asList(number1,number3));
		
		
		entityManager.persist(employee2);
		entityManager.persist(employee1);
		
		entityManager.getTransaction().commit();
		
		entityManager.close();
		factory.close();
		
	}

}
