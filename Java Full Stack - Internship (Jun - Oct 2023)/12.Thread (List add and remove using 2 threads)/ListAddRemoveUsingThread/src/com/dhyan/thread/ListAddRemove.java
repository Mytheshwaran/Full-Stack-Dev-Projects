package com.dhyan.thread;


public class ListAddRemove {
	public static void main(String[] args) throws InterruptedException {
		
		AddRemove addRemoveObj = new AddRemove();
		Thread add = new Thread(addRemoveObj, "Adding Thread");
		Thread remove = new Thread(addRemoveObj, "Removing Thread");
		remove.start();
	
		add.start();
	}
}
