package com.dhyan.ejbserver;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Singleton;

import com.dhyan.common.singleton.SingletonRemoteInterface;
import com.dhyan.common.singleton.UserDetails;
/**
 * Session Bean implementation class Singleton
 */

@Singleton
public class SingletonImplementaion implements SingletonRemoteInterface{

    private List<UserDetails> userList=new ArrayList<UserDetails>();
    /**
     * Default constructor. 
     */
    public SingletonImplementaion() {
        System.out.println("Singleton");
    }

    @Override
    public void addUser(UserDetails user)
    {
        userList.add(user);
    }
    
    @Override
    public void removeUser(UserDetails user)
    {
        for(UserDetails listUser:userList)
        {
            if((listUser.getName()).equalsIgnoreCase(user.getName()))
            {
                userList.remove(listUser);       
                break;
            }
        }
    }
    @Override
    public List<UserDetails> getUserList()
    {
        return userList;
    }
}
