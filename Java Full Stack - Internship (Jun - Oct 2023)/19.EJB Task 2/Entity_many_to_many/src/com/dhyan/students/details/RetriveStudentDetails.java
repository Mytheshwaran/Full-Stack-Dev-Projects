package com.dhyan.students.details;

import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RetriveStudentDetails {

	public static void main(String[] args) {
		EntityManagerFactory factory=Persistence.createEntityManagerFactory("Entity_many_to_many");
		EntityManager entityManager=factory.createEntityManager();
		
		Scanner scan=new Scanner(System.in);
		System.out.println("Enter Student id: ");
		int id=scan.nextInt();
		Student student=entityManager.find(Student.class,id);
		System.out.println(student);
		System.out.println(student.getCourse());
		
		System.out.println("Enter course Id: ");
		int courseId=scan.nextInt();
		Course course=entityManager.find(Course.class, courseId);
		System.out.println(course);
		System.out.println(course.getStudent());
		
		scan.close();
		entityManager.close();
		factory.close();
	}

}
