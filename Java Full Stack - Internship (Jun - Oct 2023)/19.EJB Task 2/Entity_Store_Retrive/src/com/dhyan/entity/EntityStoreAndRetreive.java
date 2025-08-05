package com.dhyan.entity;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class EntityStoreAndRetreive {

	public static void main(String[] args) {
		Scanner scan = null;
		EntityManager entityManager = null;
		EntityManagerFactory factory = null;
		try {
			factory = Persistence.createEntityManagerFactory("Entity_Store_Retrive");
			entityManager = factory.createEntityManager();

			entityManager.getTransaction().begin();

			scan = new Scanner(System.in);
			System.out.print("Enter name: ");
			String name = scan.nextLine();
			System.out.print("Enter address: ");
			String address = scan.nextLine();

			StudentDetails beanObj = new StudentDetails();
			beanObj.setName(name);
			beanObj.setAddress(address);

			entityManager.persist(beanObj);
			entityManager.getTransaction().commit();
			
			StudentDetails student=entityManager.find(StudentDetails.class, beanObj.getId());
			System.out.println(student);

			Query query = entityManager.createQuery("Select o from StudentDetails o");
			@SuppressWarnings("unchecked")
			List<StudentDetails> entity = query.getResultList();
			entity.stream().forEach(x -> System.out.println(x));
			System.out.println("---------------------");

			Query query1 = entityManager.createNamedQuery("retrive1");
			@SuppressWarnings("unchecked")
			List<StudentDetails> entity1 = query1.getResultList();
			entity1.stream().forEach(x -> System.out.println(x));
			System.out.println("---------------------");

			Query nativeQuery = entityManager.createNativeQuery("Select * from studentdetails", StudentDetails.class);
			@SuppressWarnings("unchecked")
			List<StudentDetails> entity2 = nativeQuery.getResultList();
			entity2.stream().forEach(x -> System.out.println(x));
			System.out.println("---------------------");

			System.out.println("Enter Id: ");
			int id = scan.nextInt();
			StudentDetails obj = entityManager.find(StudentDetails.class, id);
			// Retrieve
			System.out.println(obj);
			// Remove
//			entityManager.remove(obj);


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (scan != null) {
				scan.close();
			}
			if (entityManager != null) {
				entityManager.close();
			}
			if (factory != null) {
				factory.close();
			}
		}

	}

}
