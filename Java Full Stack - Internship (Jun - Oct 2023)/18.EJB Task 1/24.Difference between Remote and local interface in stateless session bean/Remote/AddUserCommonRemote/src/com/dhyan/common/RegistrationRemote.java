package com.dhyan.common;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface RegistrationRemote {
	void addUser(UserDetails user);
	List<UserDetails> getUserList();
}
