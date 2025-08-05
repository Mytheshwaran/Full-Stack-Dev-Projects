package com.dhyan.thread;

public class Messager2 implements Runnable {
	private String[] messages;
	private Chat chatObj = null;
	/**
	 * Constructor set messages and chatObj
	 * @param message
	 * @param chatObj
	 */
	public Messager2(final String[] message, Chat chatObj) {
		this.messages = message;
		this.chatObj = chatObj;
	}

	@Override
	public void run() {
		synchronized (chatObj) {
			for (String message : messages) {
				System.out.println("Messager 2: " + message);
				try {
					chatObj.notify();
					chatObj.wait();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}

		}
	}
}
