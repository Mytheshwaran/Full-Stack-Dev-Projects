package com.dhyan.employee.details;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class RetriveEmployee {

	public static void main(String[] args) {
		EntityManagerFactory factory=Persistence.createEntityManagerFactory("Entity_Inheritance");
		EntityManager entityManager=factory.createEntityManager();
		
		entityManager.getTransaction().begin();
		
		Query employeeQuery=entityManager.createNamedQuery("employee");
		Query activeEmplyeeQuery=entityManager.createNamedQuery("activeEmployee");
		Query retiredEmplyeeQuery=entityManager.createNamedQuery("retiredEmployee");
		
		@SuppressWarnings("unchecked")
		List<Employee> empList=employeeQuery.getResultList();
		@SuppressWarnings("unchecked")
		List<ActiveEmployee> activeEmpList=activeEmplyeeQuery.getResultList();
		@SuppressWarnings("unchecked")
		List<RetriveEmployee> retiredEmpList=retiredEmplyeeQuery.getResultList();
		
		System.out.println("--Employee List--");
		empList.stream().forEach(x->System.out.println(x));
		System.out.println("--Active Employee List--");
		activeEmpList.stream().forEach(x->System.out.println(x));
		System.out.println("--Retired Employee List--");
		retiredEmpList.stream().forEach(x->System.out.println(x));
		
		Scanner scan=new Scanner(System.in);
		System.out.println("Enter Id: ");
		int id=scan.nextInt();
		Employee employee=entityManager.find(Employee.class,id);
		entityManager.remove(employee);
		
		entityManager.getTransaction().commit();
		
		scan.close();
		entityManager.close();
		factory.close();
	}

}
