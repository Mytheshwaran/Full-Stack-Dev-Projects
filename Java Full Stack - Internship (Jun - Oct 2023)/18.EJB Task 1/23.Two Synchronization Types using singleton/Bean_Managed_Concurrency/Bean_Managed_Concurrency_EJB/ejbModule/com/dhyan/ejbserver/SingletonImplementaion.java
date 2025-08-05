package com.dhyan.ejbserver;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;

import com.dhyan.common.singleton.RemoteInterface;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class SingletonImplementaion implements RemoteInterface
{
    private int counter;
    
    @Override
    public synchronized void increment()
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
    
    @Override
    public int getCounter()
    {
        return counter;
    }
}
