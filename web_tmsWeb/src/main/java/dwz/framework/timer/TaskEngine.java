package dwz.framework.timer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;

public class TaskEngine {
    
    private static PriorityQueue<PriorityTask> taskQueue = null;
    
    private static Thread[] workers = null;
    
    private static Map<String, Timer> taskTimers = null;
    
    private static Object lock = new Object();
    
    private static List<TaskEngineWorker> taskWorks = new ArrayList<>();
    
    static {
        taskTimers = new HashMap<>();
        taskQueue = new PriorityQueue<>();
        
        workers = new Thread[7];
        for(int i = 0; i < workers.length; i++) {
            TaskEngineWorker worker = new TaskEngineWorker();
            
            taskWorks.add(worker);
            
            Thread thread = new Thread(worker);
            workers[i] = thread;
            workers[i].setDaemon(true);
            workers[i].start();
        }
        
    }
    
    public TaskEngine() {
        
    }
    
    public static void addTask(Runnable task, int priority) {
        synchronized(lock) {
            PriorityTask pTask = new PriorityTask(task, priority);
            taskQueue.add(pTask);
            lock.notifyAll();
        }
    }
    
    public static void addTask(Runnable task) {
        addTask(task, Thread.NORM_PRIORITY);
    }
    
    public static TimerTask scheduleTask(Runnable task, Date time) {
        ScheduledTask timerTask = new ScheduledTask(task);
        Timer taskTimer = new Timer(true);
        taskTimer.schedule(timerTask, time);
        taskTimers.put("Timer-" + taskTimers.size(), taskTimer);
        return timerTask;
    }
    
    public static TimerTask scheduleTask(Runnable task, Date time, int priority) {
        ScheduledTask timerTask = new ScheduledTask(task, priority);
        Timer taskTimer = new Timer(true);
        taskTimer.schedule(timerTask, time);
        taskTimers.put("Timer-" + taskTimers.size(), taskTimer);
        return timerTask;
    }
    
    public static TimerTask scheduleTask(Runnable task, Date time, long period) {
        ScheduledTask timerTask = new ScheduledTask(task);
        Timer taskTimer = new Timer(true);
        taskTimer.scheduleAtFixedRate(timerTask, time, period);
        taskTimers.put("Timer-" + taskTimers.size(), taskTimer);
        return timerTask;
    }
    
    public static TimerTask scheduleTask(Runnable task, Date time, long period, int priority) {
        ScheduledTask timerTask = new ScheduledTask(task, priority);
        Timer taskTimer = new Timer(true);
        taskTimer.scheduleAtFixedRate(timerTask, time, period);
        taskTimers.put("Timer-" + taskTimers.size(), taskTimer);
        return timerTask;
    }

    public static TimerTask scheduleTask(Runnable task, long delay, long period) {
        ScheduledTask timerTask = new ScheduledTask(task);
        Timer taskTimer = new Timer(true);
        taskTimer.scheduleAtFixedRate(timerTask, delay, period);
        taskTimers.put("Timer-" + taskTimers.size(), taskTimer);
        return timerTask;
    }
    
    public static TimerTask scheduleTask(Runnable task, long delay, long period, int priority) {
        ScheduledTask timerTask = new ScheduledTask(task, priority);
        Timer taskTimer = new Timer(true);
        
        taskTimer.scheduleAtFixedRate(timerTask, delay, period);
        taskTimers.put("Timer-" + taskTimers.size(), taskTimer);
        
        return timerTask;
    }

    public static void cancelAllTasks() {
        
        synchronized(lock) {
            
            Collection<Timer> times = taskTimers.values();
            Iterator<Timer> it = times.iterator();
            
            while(it.hasNext()) {
                Timer time = it.next();
                time.cancel();
                time = null;
            }
            
            for(Thread thread: workers) {
                thread.setDaemon(false);
                thread.interrupt();
                thread = null;
            }
            
            taskQueue.clear();
            for (TaskEngineWorker taskwork: taskWorks) {
                taskwork.setDone(true);
            }
            
            taskWorks.clear();
        }
    }
    
    public static Runnable nextTask() {
        synchronized(lock) {
            while(taskQueue.isEmpty()) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return taskQueue.poll();
        }
    }
    
    private static class TaskEngineWorker implements Runnable {
        
        private boolean done = false;

        public void setDone(boolean done) {
            this.done = done;
        }
        
        @Override
        public void run() {
            while(!done) {
                nextTask().run();
            }
        }
    }

    private static class ScheduledTask extends TimerTask {
        
        private Runnable task;
        private int priority;
        
        public ScheduledTask(Runnable task, int priority) {
            this.task = task;
            this.priority = priority;
        }
        
        public ScheduledTask(Runnable task) {
            this(task, Thread.NORM_PRIORITY);
        }

        @Override
        public void run() {
            addTask(task, priority);
        }
        
    }
    
    private static class PriorityTask implements Runnable, Comparable<PriorityTask> {
        
        private Runnable task = null;
        
        private int priority = Thread.NORM_PRIORITY;
        
        public PriorityTask(Runnable task, int priority) {
            this.task = task;
            this.priority = priority;
        }

        @Override
        public void run() {
            this.task.run();
        }
        
        @Override
        public int compareTo(PriorityTask other) {
            if (other==null) return 0;
            
            if(other.priority > this.priority) {
                return 1;
            } else if(other.priority < this.priority){
                return -1;
            }
            return 0;
        }
        
    }
    
}