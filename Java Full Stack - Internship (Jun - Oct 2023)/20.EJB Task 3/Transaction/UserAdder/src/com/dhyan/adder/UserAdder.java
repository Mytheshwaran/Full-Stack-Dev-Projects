package com.dhyan.adder;

import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.dhyan.common.RegistrationRemote;


public class UserAdder
{
    private static RegistrationRemote registerObj;

    public static void main(String[] args)
    {
        Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        properties.put(Context.PROVIDER_URL, "remote+http://127.0.0.1:8080");
        try
        {
            Context context = new InitialContext(properties);
            registerObj = (RegistrationRemote) context.lookup("ejb:RemoteEJB/ServerEJB/Registration!com.dhyan.common.RegistrationRemote");
            userAdder();
            displayUser();
            
        }
        catch (Exception e)
        {
        	e.printStackTrace();
            System.err.println(e.getMessage());
        }

    }
    public static void userAdder()
    {
    	System.out.print("Enter Name:");
    	Scanner scan=new Scanner(System.in);
    	String name=scan.next();
    	registerObj.addUser(name);
    	scan.close();
    }
    public static void displayUser()
    {
    	List<String> userList=registerObj.getUserList();
    	for(String user:userList)
    	{
    		System.out.println(user);
    	}
    }
}
