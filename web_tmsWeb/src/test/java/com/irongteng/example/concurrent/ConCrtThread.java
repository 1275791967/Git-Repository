package com.irongteng.example.concurrent;

class ConCrtThread extends Thread {
    
    volatile boolean stop = false;
    
    @Override
    public void run() {
        long time = System.currentTimeMillis();
        while (!stop || System.currentTimeMillis() - time < 3000) {
            System.out.println("Thread is running...");
            //long time = System.currentTimeMillis();
            while(true) {
                try {
                    this.join(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("are run..." + System.currentTimeMillis());
            }
        }
        System.out.println("Thread exiting under request...");
    }
    
    public void setStopped(boolean stop) {
        this.stop = stop;
    }
    
    public static void main(String args[]) throws Exception {
        ConCrtThread thread = new ConCrtThread();
        System.out.println("Starting thread...");
        thread.start();
        thread.join(3000);
        thread.setStopped(true);
        //thread.interrupt();
        
        System.out.println("Stopping application...");
        // System.exit( 0 );
    }
    
}