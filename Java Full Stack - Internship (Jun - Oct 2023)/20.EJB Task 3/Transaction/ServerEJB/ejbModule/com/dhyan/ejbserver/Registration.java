package com.dhyan.ejbserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dhyan.common.RegistrationRemote;
import com.dhyan.server.jpa.UserDetails;

/**
 * Session Bean implementation class Registration
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class Registration implements RegistrationRemote {

	@PersistenceContext(unitName = "ServerJPA")
	EntityManager entityManager;
	RegistrationRemote registerObj=null;

	@Override
	public void addUser(String name) {
		Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        properties.put(Context.PROVIDER_URL, "remote+http://127.0.0.1:8080");
      
		try {
			Context context = new InitialContext(properties);
            registerObj = (RegistrationRemote) context.lookup("ejb:RemoteEJB/ServerEJB/Registration!com.dhyan.common.RegistrationRemote");
            
            System.out.println("--Adder--");
            
            newTransactionAdder(name);
            
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
			registerObj.requiredTransactionAdder(name);
		}
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public List<String> getUserList() {
		System.out.println("------Retrive------");
		List<UserDetails> userListObj=entityManager.createNamedQuery("user.getAll").getResultList();
		List<String> userList=new ArrayList<>();
		for(UserDetails user:userListObj)
		{
			userList.add(user.getName());
		}
		return userList;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override
	public void newTransactionAdder(String name)
	{
		System.out.println("---New1 Transaction---");
		try {
			UserDetails user=new UserDetails();
			user.setName("new1 "+name);
			System.out.println("==="+user);
			entityManager.persist(user);
			throw new Exception("Rollback condition triggered");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void requiredTransactionAdder(String name)
	{
		System.out.println("---New2 Transaction---");
		try {
			UserDetails user=new UserDetails();
			user.setName("new2 "+name);
			System.out.println("==="+user);
			entityManager.persist(user);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
}
