package com.dhyan.embedded.id.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EmployeeDetails {

	public static void main(String[] args) {
		EntityManagerFactory factory=Persistence.createEntityManagerFactory("Entity_Embeddable");
		EntityManager entityManager=factory.createEntityManager();
		
		entityManager.getTransaction().begin();
		
		Department dept1=new Department();
		dept1.setName("Developer");
		
		Department dept2=new Department();
		dept2.setName("Tester");
		
		Employee emp1=new Employee();
		emp1.setName("employee1");
		emp1.setDept(dept2);
		
		Employee emp2=new Employee();
		emp2.setName("employee2");
		emp2.setDept(dept1);
		
		entityManager.persist(emp2);
		entityManager.persist(emp1);
		
		entityManager.getTransaction().commit();
		
		entityManager.close();
		factory.close();
	}

}
