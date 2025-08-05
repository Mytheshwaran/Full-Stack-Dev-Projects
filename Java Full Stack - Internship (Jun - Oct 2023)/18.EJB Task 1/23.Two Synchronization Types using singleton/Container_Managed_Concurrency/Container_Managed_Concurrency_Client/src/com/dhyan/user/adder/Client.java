package com.dhyan.user.adder;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.dhyan.common.singleton.RemoteInterface;

public class Client
{
    private static RemoteInterface remoteObj;
    
    public static void main(String[] args)
    {
        Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        properties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        try
        {
            Context context = new InitialContext(properties);
            remoteObj = (RemoteInterface) context
                    .lookup("ejb:Container_Menaged_Concurrency/Container_Managed_Concurrency_EJB/SingletonImplementaion!com.dhyan.common.singleton.RemoteInterface");
            
            for (int i = 0; i < 2; i++)
            {
                System.out.println("--------------------------------");
                Thread t1 = new Thread(new Runnable()
                {
                    public void run()
                    {
                        System.out.println(Thread.currentThread().getName() + " - " + Thread.currentThread().getState());
                        System.out.println(Thread.currentThread().getName() + " - " +remoteObj.getCounter());
                        remoteObj.increment();
                        System.out.println(Thread.currentThread().getName()+" - Completed  the Increment");
                        System.out.println("<<<<<<<< waiting to complete the lock type write, If any>>>>>>>");
                        System.out.println("=="+Thread.currentThread().getName() + " - " +remoteObj.getCounter());
                    }
                },"t1");

                Thread t2 = new Thread(new Runnable()
                {
                    public void run()
                    {
                        System.out.println(Thread.currentThread().getName() + " - " + Thread.currentThread().getState());
                        System.out.println(Thread.currentThread().getName() + " - " +remoteObj.getCounter());
                        remoteObj.increment();
                        System.out.println(Thread.currentThread().getName()+" - Completed  the Increment");
                        System.out.println("<<<<<<<< waiting to complete the lock type write, If any >>>>>>>");
                        System.out.println("=="+Thread.currentThread().getName() + " - " +remoteObj.getCounter());
                    }
                },"t2");

                t1.start();
                t2.start();
                Thread.sleep(1000);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
