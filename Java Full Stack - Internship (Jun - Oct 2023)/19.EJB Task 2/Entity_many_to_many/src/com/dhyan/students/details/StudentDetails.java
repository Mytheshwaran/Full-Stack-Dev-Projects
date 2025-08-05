package com.dhyan.students.details;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class StudentDetails {

	public static void main(String[] args) {
		EntityManagerFactory factory=Persistence.createEntityManagerFactory("Entity_many_to_many");
		EntityManager entityManager=factory.createEntityManager();
		
		entityManager.getTransaction().begin();
		
		Student stu1=new Student();
		stu1.setName("stu1");
		Student stu2=new Student();
		stu2.setName("stu2");
		Student stu3=new Student();
		stu3.setName("stu3");
		
		Course course1=new Course();
		course1.setName("course1");
		Course course2=new Course();
		course2.setName("course2");
		Course course3=new Course();
		course3.setName("course3");
	
		stu1.setCourse(Arrays.asList(course1,course3));
		stu2.setCourse(Arrays.asList(course2,course3));
		stu3.setCourse(Arrays.asList(course3,course1));
		
		course1.setStudent(Arrays.asList(stu1,stu2));
		course2.setStudent(Arrays.asList(stu2,stu3));
		course3.setStudent(Arrays.asList(stu3,stu1));
		
		entityManager.persist(stu3);
		entityManager.persist(stu2);
		entityManager.persist(stu1);
		
		entityManager.persist(course3);
		entityManager.persist(course2);
		entityManager.persist(course1);
		
		
		
		entityManager.getTransaction().commit();
		
		entityManager.close();
		factory.close();		
	}

}
