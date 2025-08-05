package com.dhyan.employeedetails;

import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RetriveEmployeeDetails {

	public static void main(String[] args) {
		EntityManagerFactory factory=Persistence.createEntityManagerFactory("Entity_one_to_one");
		EntityManager entityManager=factory.createEntityManager();
		
		Scanner scan=new Scanner(System.in);
		System.out.println("Enter Employee Id: ");
		int id=scan.nextInt();
		
		Employee employee=entityManager.find(Employee.class,id);
		System.out.println(employee);
		System.out.println(employee.getAddress());
		
		System.out.println("Enter Address Id: ");
		int code=scan.nextInt();
		Address address=entityManager.find(Address.class, code);
		System.out.println(address);
		System.out.println(address.getEmployee());
		
		scan.close();
		entityManager.close();
		factory.close();		
	}

}
