package com.dhyan.employee;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class RetriveEmployeePhoneDetails {

	public static void main(String[] args) {
		RetriveEmployeePhoneDetails retrieve = new RetriveEmployeePhoneDetails();
		retrieve.retrieveDetails();
	}

	public void retrieveDetails() {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("Entity_Locking");
		EntityManager entityManager = factory.createEntityManager();
		entityManager.getTransaction().begin();

		System.out.println("-----------");

		Query query = entityManager.createNamedQuery("employee");

		@SuppressWarnings("unchecked")
		List<Employee> employeeList = query.getResultList();

		System.out.println("+++++ " + Thread.currentThread().getName() + " +++++");
		System.out.println("########### List of Employees before Changing #########");
		employeeList.stream().forEach(x -> System.out.println("-" + x));
		System.out.println("*************************");

		System.out.println("--Id 2 is updateing by " + Thread.currentThread().getName() + "--");
		Employee employee = entityManager.find(Employee.class, 2, LockModeType.OPTIMISTIC_FORCE_INCREMENT);

		System.out.println(Thread.currentThread().getName() + " Before updating =" + employee);
		employee.setName(Thread.currentThread().getName());

		System.out.println(Thread.currentThread().getName() + " After updating =" + employee);

		entityManager.merge(employee);
		entityManager.getTransaction().commit();

		@SuppressWarnings("unchecked")
		List<Employee> empUpdatedList = query.getResultList();
		empUpdatedList.stream().forEach(x -> System.out.println(x));

		entityManager.close();
		factory.close();
	}

}
