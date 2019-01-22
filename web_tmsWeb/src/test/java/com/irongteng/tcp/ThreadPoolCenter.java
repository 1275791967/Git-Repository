package com.irongteng.tcp;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolCenter extends ThreadPoolExecutor{
    
    private static BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(); 
    
    private static final ThreadPoolCenter executor = new ThreadPoolCenter();
    
    private ThreadPoolCenter() {
        super(30, 600, 1, TimeUnit.DAYS, queue);
    }
    
    public static ThreadPoolCenter getInstance() {
        return executor;
    }
    
    public void stop() {
        this.shutdown();
    }
    
    @Override
    protected void afterExecute(Runnable r, Throwable t) { 
        System.out.println("Task finished."); 
    }
    
    public void add(Runnable runnable) {
        this.execute(runnable);
    }
}

