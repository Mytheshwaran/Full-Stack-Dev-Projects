package com.dhyan.entity;

import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityStore {

	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("Entity_Store_Retrive");
		EntityManager entityManager = factory.createEntityManager();

		entityManager.getTransaction().begin();

		

		Scanner scan = new Scanner(System.in);
		System.out.print("Enter name: ");
		String name = scan.nextLine();
		System.out.print("Enter address: ");
		String address = scan.nextLine();

		StudentDetails beanObj = new StudentDetails();
		beanObj.setName(name);
		beanObj.setAddress(address);

		entityManager.persist(beanObj);
		

		entityManager.getTransaction().commit();

		scan.close();
		entityManager.close();
		factory.close();
	}

}
