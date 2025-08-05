package com.dhyan.ejbserver;

import java.util.ArrayList;
import java.util.List;
import com.dhyan.common.UserDetails;
import com.dhyan.common.RegistrationLocal;

import javax.ejb.Stateless;

/**
 * Session Bean implementation class Registration
 */
@Stateless
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

