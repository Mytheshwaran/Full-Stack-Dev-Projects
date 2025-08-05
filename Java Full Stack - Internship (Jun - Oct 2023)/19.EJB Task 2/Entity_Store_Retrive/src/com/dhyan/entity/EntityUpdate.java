package com.dhyan.entity;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class EntityUpdate {

	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("Entity_Store_Retrive");
		EntityManager entityManager = factory.createEntityManager();
		
		entityManager.getTransaction().begin();
		
		Query query=entityManager.createQuery("select data from StudentDetails data");
		@SuppressWarnings("unchecked")
		List<StudentDetails> dataList=query.getResultList();
		dataList.stream().forEach(x->System.out.println(x));
		
		//Update table by changing exist data
		Scanner scan=new Scanner(System.in);
		System.out.print("Enter id: ");
		int id=scan.nextInt();
		
		StudentDetails beanData=entityManager.find(StudentDetails.class,id);
		
		System.out.println(beanData.getId()+" "+beanData.getName()+" "+beanData.getAddress());
		
		beanData.setAddress("newAddress");
		
		System.out.println("After updating");
		System.out.println(beanData.getId()+" "+beanData.getName()+" "+beanData.getAddress());
		
		//Update table by adding data using merge
		System.out.println("----Enter new data-----");
		
		System.out.print("Enter name: ");
		String name = scan.next();
		scan.nextLine();
		System.out.print("Enter address: ");
		String address = scan.nextLine();

		StudentDetails beanObj = new StudentDetails();
		beanObj.setName(name);
		beanObj.setAddress(address);

		entityManager.merge(beanObj);
		
		entityManager.getTransaction().commit();
		
		scan.close();
		entityManager.close();
		factory.close();
	}

}
