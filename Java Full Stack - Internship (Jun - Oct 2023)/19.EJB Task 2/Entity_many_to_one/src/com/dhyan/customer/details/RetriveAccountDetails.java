package com.dhyan.customer.details;

import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RetriveAccountDetails {

	public static void main(String[] args) {
		EntityManagerFactory factory=Persistence.createEntityManagerFactory("Entity_many_to_one");
		EntityManager entityManager=factory.createEntityManager();
		
		Scanner scan=new Scanner(System.in);
		System.out.println("Enter Account no: ");
		int accNo=scan.nextInt();
		Account account=entityManager.find(Account.class,accNo);
		
		System.out.println(account);
		System.out.println(account.getCustomer());
		
		scan.close();
		entityManager.close();
		factory.close();
	}

}
