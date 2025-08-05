package com.dhyan.common;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface RegistrationRemote {
	void addUser(String user);
	List<String> getUserList();
	public void newTransactionAdder(String name);
	public void requiredTransactionAdder(String name);
}
