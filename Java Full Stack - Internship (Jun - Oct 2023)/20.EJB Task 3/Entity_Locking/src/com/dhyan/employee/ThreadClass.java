package com.dhyan.employee;

public class ThreadClass {

	public static void main(String[] args) {

		Thread thread1 = new Thread(new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getState());
				RetriveEmployeePhoneDetails.main(args);
			}
		},"thread1");

		Thread thread2 = new Thread(new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getState());
				RetriveEmployeePhoneDetails.main(args);
			}
		},"thread2");

		thread1.start();
		thread2.start();
	}

}
