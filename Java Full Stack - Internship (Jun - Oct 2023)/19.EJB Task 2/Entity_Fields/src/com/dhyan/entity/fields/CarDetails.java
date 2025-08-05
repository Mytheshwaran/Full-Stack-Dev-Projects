package com.dhyan.entity.fields;


import java.sql.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CarDetails {

	public static void main(String[] args) {
		EntityManagerFactory factory= Persistence.createEntityManagerFactory("Entity_Fields");
		EntityManager entityManager=factory.createEntityManager();
		
		entityManager.getTransaction().begin();
		
		Car car1=new Car();
		car1.setCompany("Company1");
		car1.setColor(Color.BLUE);
		car1.setDate(Date.valueOf("2003-01-26"));
		car1.setCarOwner("Owner1");
		
		Car car2=new Car();
		car2.setCompany("Company2");
		car2.setColor(Color.RED);
		car2.setDate(Date.valueOf("2001-08-15"));
		car2.setCarOwner("Owner2");
		
		entityManager.persist(car2);
		entityManager.persist(car1);
		
		entityManager.getTransaction().commit();
		
		//Retrieve
		Car car=entityManager.find(Car.class,2);
		System.out.println("________"+car+"___________");
		
		entityManager.close();
		factory.close();
	}

}
