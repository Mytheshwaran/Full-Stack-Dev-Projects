package com.dhyan.entity.collection;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class StudentDetails {

	public static void main(String[] args) {
		EntityManagerFactory factory=Persistence.createEntityManagerFactory("Entity_Collection");
		EntityManager entityManager=factory.createEntityManager();
		
		entityManager.getTransaction().begin();
		
		Subject subject1=new Subject();
		subject1.setSubject_code(1);
		subject1.setSubject_name("English");
		
		Subject subject2=new Subject();
		subject2.setSubject_code(2);
		subject2.setSubject_name("Maths");
		
		Subject subject3=new Subject();
		subject3.setSubject_code(3);
		subject3.setSubject_name("Chemistry");
		
		Subject subject4=new Subject();
		subject4.setSubject_code(4);
		subject4.setSubject_name("Physics");
		
		Subject subject5=new Subject();
		subject5.setSubject_code(5);
		subject5.setSubject_name("Biology");
		
		Subject subject6=new Subject();
		subject6.setSubject_code(6);
		subject6.setSubject_name("Computer Science");
		
		Student student1=new Student();
		student1.setName("Name1");
		student1.setStandard("XII");
		student1.setSection("B");
		student1.setSubjects(Arrays.asList(subject1,subject2,subject3));
		
		Student student2=new Student();
		student2.setName("Name2");
		student2.setStandard("XII");
		student2.setSection("A");
		student2.setSubjects(Arrays.asList(subject4,subject5,subject6));
		
		
		entityManager.persist(student2);
		entityManager.persist(student1);
		
		entityManager.getTransaction().commit();
		
		entityManager.close();
		factory.close();
		
	}

}
