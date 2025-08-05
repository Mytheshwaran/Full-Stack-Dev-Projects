package com.dhyan.common.singleton;

import javax.ejb.Remote;

@Remote
public interface RemoteInterface
{
    void increment();
    int getCounter();
    
}
