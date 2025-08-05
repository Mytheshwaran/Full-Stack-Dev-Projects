package com.dhyan.common;

import java.util.List;

import javax.ejb.Local;

@Local
public interface RegistrationLocal {
	void addUser(UserDetails user);
	List<UserDetails> getUserList();
}
