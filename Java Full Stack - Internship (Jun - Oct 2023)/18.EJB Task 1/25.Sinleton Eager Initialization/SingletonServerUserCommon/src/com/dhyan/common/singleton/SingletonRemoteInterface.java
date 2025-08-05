package com.dhyan.common.singleton;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface SingletonRemoteInterface
{
    void addUser(UserDetails user);
    void removeUser(UserDetails user);
    List<UserDetails> getUserList();
}
