package com.dhyan.thread;

public class Messager1 implements Runnable {
	private String[] messages;
	private Chat chatObj = null;
	
	/**
	 * Constructor set messages and chatObj
	 * @param message
	 * @param chatObj
	 */
	public Messager1(final String[] message, Chat chatObj) {
		this.messages = message;
		this.chatObj = chatObj;
	}

	@Override
	public void run() {
		synchronized (chatObj) {

			for (String message : messages) {
				System.out.println("Messager 1 : " + message);
				try {
					chatObj.wait();
					chatObj.notify();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}

	}
}
