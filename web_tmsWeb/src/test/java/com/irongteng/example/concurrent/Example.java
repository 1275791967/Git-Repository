package com.irongteng.example.concurrent;

class Example extends Thread {
    
    volatile boolean stop = false;
    
    public static void main(String args[]) throws Exception {
        Example thread = new Example();
        System.out.println("Starting thread...");
        thread.start();
        Thread.sleep(3000);
        System.out.println("Asking thread to stop...");
        thread.stop = true;
        Thread.sleep(3000);
        System.out.println("Stopping application...");
        // System.exit( 0 );
    }
    
    @Override
    public void run() {
        while (!stop) {
            System.out.println("Thread is running...");
            long time = System.currentTimeMillis();
            while ((System.currentTimeMillis() - time < 1000) && (!stop)) {
                
            }
        }
        System.out.println("Thread exiting under request...");
    }
}