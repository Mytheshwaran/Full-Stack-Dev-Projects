package com.dhyan.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class AddRemove implements Runnable {
	private List<Integer> values = new ArrayList<Integer>();
	static Scanner scan = new Scanner(System.in);

	@Override
	public void run() {
		try {
			if (Thread.currentThread().getName().equals("Adding Thread")) {
				System.out.println("Running Add");
				add();
			} else {
				System.out.println("Running Remove");
				remove();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Add Elements to List
	 */
	public void add() {

		try {
			int element = 0;
			while (true) {

				Thread.sleep(500);
				if (values.size() < 10) {
					values.add(++element);
					System.out.println("Added Element: " + element);
					System.out.println("List: " + values);
					synchronized (this) {
						this.notify();
					}
				} else {
					synchronized (this) {
						System.out.println("==List Full==");
						System.out.println("--Add method is waiting--");
						this.wait();
						System.out.println("--Continue Add method --");
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Remove Elements from List
	 */
	public void remove() {
		try {

			while (true) {
				Thread.sleep(2000);
				synchronized (this) {
					if (values.size() == 0) {

						System.out.println("===List Empty===");
						System.out.println("---Remove method is waiting--");
						this.wait();
						System.out.println("--Continue Remove method --");
					}

					System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
					System.out.println("Removed Element: " + values.remove(values.size() - 1));
					System.out.println("List: " + values);
					System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

					this.notify();
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}
