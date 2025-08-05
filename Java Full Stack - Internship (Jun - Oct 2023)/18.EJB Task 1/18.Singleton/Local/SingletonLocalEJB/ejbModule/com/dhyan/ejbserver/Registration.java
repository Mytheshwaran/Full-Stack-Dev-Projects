package com.dhyan.ejbserver;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Singleton;

import com.dhyan.common.RegistrationLocal;
import com.dhyan.common.UserDetails;

/**
 * Session Bean implementation class Registration
 */
@Singleton
public class Registration implements RegistrationLocal {
	private List<UserDetails> userList=new ArrayList<UserDetails>();

	@Override
	public void addUser(UserDetails user)
	{
		userList.add(user);
	}
	
	@Override
	public List<UserDetails> getUserList()
	{
		return userList;
	}
}

