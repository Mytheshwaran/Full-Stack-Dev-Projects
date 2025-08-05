package com.dhyan.employee;

import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RetriveEmployeePhoneDetails {

	public static void main(String[] args) {
		EntityManagerFactory factory=Persistence.createEntityManagerFactory("Entity_one_to_many");
		EntityManager entityManager=factory.createEntityManager();
		
		Scanner scan=new Scanner(System.in);
		System.out.println("Enter id: ");
		int id=scan.nextInt();
		Employee employee=entityManager.find(Employee.class, id);
		System.out.println(employee);
		System.out.println(employee.getPhoneNumber());
		
		entityManager.close();
		factory.close();
		scan.close();
	}

}
