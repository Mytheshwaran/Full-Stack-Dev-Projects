package com.dhyan.employee.details;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class StoreEmployee {

	public static void main(String[] args) {
		EntityManagerFactory factory=Persistence.createEntityManagerFactory("Entity_Inheritance");
		EntityManager entityManager=factory.createEntityManager();
		
		entityManager.getTransaction().begin();
		
		ActiveEmployee activeEmp1=new ActiveEmployee();
		activeEmp1.setName("activeEmp_1");
		activeEmp1.setSalary(30000);
		activeEmp1.setExperience(3);
		
		ActiveEmployee activeEmp2=new ActiveEmployee();
		activeEmp2.setName("activeEmp_2");
		activeEmp2.setSalary(40000);
		activeEmp2.setExperience(6);
		
		RetiredEmployee retiredEmp1=new RetiredEmployee();
		retiredEmp1.setName("retiredEmp_1");
		retiredEmp1.setPenson(3000);
		
		RetiredEmployee retiredEmp2=new RetiredEmployee();
		retiredEmp2.setName("retiredEmp_2");
		retiredEmp2.setPenson(4000);
		
		entityManager.persist(retiredEmp2);
		entityManager.persist(retiredEmp1);
		entityManager.persist(activeEmp2);
		entityManager.persist(activeEmp1);
		
		entityManager.getTransaction().commit();
		
		entityManager.close();
		factory.close();
	}

}
