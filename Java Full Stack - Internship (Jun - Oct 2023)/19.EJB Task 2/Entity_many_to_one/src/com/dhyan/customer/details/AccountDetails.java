package com.dhyan.customer.details;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AccountDetails {

	public static void main(String[] args) {
		EntityManagerFactory factory=Persistence.createEntityManagerFactory("Entity_many_to_one");
		EntityManager entityManager=factory.createEntityManager();
		
		entityManager.getTransaction().begin();
		
		Customer customer1=new Customer();
		customer1.setName("emp1");
		Customer customer2=new Customer();
		customer2.setName("emp2");
		
		Account accType1=new Account();
		accType1.setType("accType1");
		accType1.setCustomer(customer1);
		
		Account accType2=new Account();
		accType2.setType("accType2");
		accType2.setCustomer(customer2);
		
		Account accType3=new Account();
		accType3.setType("accType3");
		accType3.setCustomer(customer2);
		
		Account accType4=new Account();
		accType4.setType("accType4");
		accType4.setCustomer(customer1);
		
		entityManager.persist(accType4);
		entityManager.persist(accType3);
		entityManager.persist(accType2);
		entityManager.persist(accType1);
		
		entityManager.getTransaction().commit();
		
		entityManager.close();
		factory.close();
		
		
	}

}
