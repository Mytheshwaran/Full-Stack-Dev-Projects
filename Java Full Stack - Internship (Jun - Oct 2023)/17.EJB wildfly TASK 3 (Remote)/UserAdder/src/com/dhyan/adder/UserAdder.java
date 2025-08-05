package com.dhyan.adder;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;

import com.dhyan.common.RegistrationRemote;
import com.dhyan.common.UserDetails;


public class UserAdder
{
    private static RegistrationRemote registerObj;

    public static void main(String[] args)
    {
        Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        properties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        try
        {
            Context context = new InitialContext(properties);
            registerObj = (RegistrationRemote) context.lookup("ejb:RemoteEJB/ServerEJB/Registration!com.dhyan.common.RegistrationRemote");

            while (true)
            {
                addUsersToList();
                System.out.print("Display Users (0) or Add Users (1) : ");
                int opt = IOUtility.getInteger();
                if (opt == 0)
                {
                    displayUsers();
                    break;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    public static void addUsersToList()
    {
        System.out.print("Enter the name: ");
        String name = IOUtility.getString();

        if (!name.isEmpty())
        {
            UserDetails user = new UserDetails();
            user.setName(name);

            boolean isUserExist = checkUsers(name);
            if (!isUserExist)
            {
                registerObj.addUser(user);
                System.out.println("User added Successfull!!!");
            }
            else
            {
                System.out.println("User Already Exist");
            }
        }
    }

    public static boolean checkUsers(String name)
    {
        boolean isUserExist = false;
        for (UserDetails users : registerObj.getUserList())
        {
            if (users.getName().equalsIgnoreCase(name))
            {
                isUserExist = true;
            }
        }
        return isUserExist;
    }

    public static void displayUsers()
    {
        for (UserDetails users : registerObj.getUserList())
        {
            System.out.println(users.getName());
        }
    }
}
