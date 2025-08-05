package com.dhyan.ejbserver;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

import com.dhyan.common.singleton.RemoteInterface;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class SingletonImplementaion implements RemoteInterface
{
    private int counter;
    
    @Lock(LockType.WRITE)
    @Override
    public void increment()
    {
        System.out.println(Thread.currentThread().getName()+" - Increment the counter");
        try
        {
            Thread.sleep(1000);
            counter++;
        }
        catch (InterruptedException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    @Lock(LockType.READ)
    @Override
    public int getCounter()
    {
        return counter;
    }
}
