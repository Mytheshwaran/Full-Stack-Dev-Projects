package com.dhyan.entity;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class EntityRetrive {

	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("Entity_Store_Retrive");
		EntityManager entityManager = factory.createEntityManager();
		
		Query query=entityManager.createQuery("select data from StudentDetails data");
		@SuppressWarnings("unchecked")
		List<StudentDetails> dataList=query.getResultList();
		dataList.stream().forEach(x->System.out.println(x));
		
		Scanner scan=new Scanner(System.in);
		System.out.print("Enter id: ");
		int id=scan.nextInt();
		
		StudentDetails beanData=entityManager.find(StudentDetails.class,id);
		
		System.out.println(beanData.getId()+" "+beanData.getName()+" "+beanData.getAddress());
		
		scan.close();
		entityManager.close();
		factory.close();
	}

}
