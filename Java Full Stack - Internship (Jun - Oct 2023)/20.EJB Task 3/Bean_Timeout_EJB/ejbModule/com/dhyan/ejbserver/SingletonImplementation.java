package com.dhyan.ejbserver;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
/**
 * Session Bean implementation class Singleton
 */

@Singleton
@Startup
public class SingletonImplementation{

	@Resource
    TimerService timerService;
    
	@PostConstruct
    public void serviceStarter() {
    	schedular();
    	timerService.createTimer(2000, "Timeout");
    }
    
    @Timeout
    public void timout(Timer timer)
    {
    	System.out.println(timer.getInfo()+"-->"+new Date());
    }
    
    @Schedule(hour = "*",minute = "*",second = "*/5")
    public void schedular()
    {
    	System.out.println("Schedule-->"+new Date());
    }
}
