package com.dhyan.user.adder;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.dhyan.common.singleton.SingletonRemoteInterface;
import com.dhyan.common.singleton.UserDetails;

public class Client
{
    static SingletonRemoteInterface remoteObj;
    public static void main(String[] args)
    {
        Properties properties=new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        properties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        try
        {
            Context context=new InitialContext(properties);
            remoteObj = (SingletonRemoteInterface) context.lookup("ejb:SimpleSingleton/SingletonNormalEJB/SingletonImplementaion!com.dhyan.common.singleton.SingletonRemoteInterface");
            while (true)
            {
                System.out.print("1.Add Users\n"
                        + "2.Remove Users\n"
                        + "3.Display Users\n"
                        + "4.exit \n");
                int opt = IOUtility.getInteger();
                if (opt == 1)
                {
                    addUsersToList();
                }
                else if(opt==2)
                {
                    removeUsersFromList();
                }
                else if(opt==3)
                {
                    displayUsers();
                }
                else if(opt==4)
                {
                    break;
                }
                else
                {
                    System.out.println("Invalid choice");
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
            boolean isUserExist = checkUsers(name);
            if (!isUserExist)
            {
                UserDetails user=new UserDetails();
                user.setName(name);
                remoteObj.addUser(user);
                System.out.println("User added Successfully!!!");
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
        for (UserDetails user : remoteObj.getUserList())
        {
            if (user.getName().equalsIgnoreCase(name))
            {
                isUserExist = true;
            }
        }
        return isUserExist;
    }
    
    public static void removeUsersFromList()
    {
        System.out.print("Enter name: ");
        String name=IOUtility.getString();
        UserDetails user=new UserDetails();
        user.setName(name);
        remoteObj.removeUser(user);                
        System.out.println("User Removed Sucessfully");
    }

    public static void displayUsers()
    {
        for (UserDetails users : remoteObj.getUserList())
        {
            System.out.println(users.getName());
        }
    }
}
