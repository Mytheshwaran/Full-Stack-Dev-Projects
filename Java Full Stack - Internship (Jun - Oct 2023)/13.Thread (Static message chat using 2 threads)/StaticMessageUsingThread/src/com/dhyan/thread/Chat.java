package com.dhyan.thread;

public class Chat {
	public static void main(String[] args) {
		String[] person1Msg = new String[] { "Hi", "How are you?","End" };
		String[] person2Msg = new String[] { "Hi", "I am fine", "End" };
		try {
			Chat chatObj = new Chat();
			Thread person1=new Thread(new Messager1(person1Msg, chatObj));
			Thread person2=new Thread(new Messager2(person2Msg, chatObj));
			person1.start();
			person2.start();
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
